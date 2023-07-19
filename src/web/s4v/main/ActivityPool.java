package web.s4v.main;

import web.s4v.shared.ActivityInfo;
import web.s4v.shared.SpotsForVolunteeringException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a pool of activities managed by their IDs.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @extends PersistentObject
 * @implements Serializable
 */

public class ActivityPool extends PersistentObject implements Serializable {

    private static File backupFile;
    private static ActivityPool activityPool = null;
    private Set<Activity> activities = new HashSet<>();

    /**
     * Reset pool to its initial state.
     */
    public void reset() {
        activityPool = null;
        activities.clear();
        try (FileWriter writer = new FileWriter(backupFile)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("File reset error");
        }
    }

    /**
     * Get the current file used for backup. It is an absolute pathname
     * @return backupFile
     */
    public static File getBackupFile() {
        return backupFile;
    }

    /**
     * Change the file used for backups. This file is persisted as an absolute pathname, even if given as a relative pathname
     * @param backupFile to save the serialization.
     */
    public static void setBackupFile(File backupFile) {
        if(!backupFile.isAbsolute())
            backupFile = backupFile.getAbsoluteFile();
        ActivityPool.backupFile = backupFile;
    }

    /**
     * Convenience method to set the backup file as a string
     * @param name of backup file
     */
    public static void setBackupFile(String name) {
        backupFile = new File(name);
    }

    /**
     * Back up activity pool single instance to the backup file
     * @throws SpotsForVolunteeringException PersistentObject.backup(S, java.io.File) exceptions
     */
    static void backup() throws SpotsForVolunteeringException {
        PersistentObject.backup(activityPool,backupFile);
    }

    /**
     *
     * @return ActivityPool
     * @throws SpotsForVolunteeringException PersistentObject.backup(S, java.io.File) exceptions
     */
    static ActivityPool restore() throws SpotsForVolunteeringException {
        return (ActivityPool) PersistentObject.restore(backupFile);
    }

    /**
     * Obtain single instance of this class. If a serialized version of this instance is available on the file system, it is recovered; otherwise, a new instance is created
     * @return activityPool singleton instance.
     * @throws SpotsForVolunteeringException if an IOException or ClassNotFoundException occur during deserialization.
     */

    public static ActivityPool getInstance() throws SpotsForVolunteeringException {
        if (activityPool == null) {
            if (backupFile.exists()) {
                try {
                    activityPool = (ActivityPool) PersistentObject.restore(backupFile);
                } catch (SpotsForVolunteeringException e) {
                    throw new SpotsForVolunteeringException("Error while recovering activity pool from file", e);
                }
            } else {
                activityPool = new ActivityPool();
            }
        }
        return activityPool;
    }

    /**
     * Creates a new activity with given ID and stores it to be retrieved later using getActivity(String).
     * @param activityInfo to instance activity
     * @return activity added
     * @throws SpotsForVolunteeringException if activity already has an ID
     */

    Activity addActivity(ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        for (Activity activity : activities) {
            if(activity.getId().equals(activityInfo.getId()))
                throw new SpotsForVolunteeringException("Activity with that ID already exists");
        }
        Activity activity = new Activity(activityInfo);
        activities.add(activity);
        return activity;
    }

    /**
     * Retrieve activity by ID
     * @param id of activity
     * @return activity or null, if no activity exists with given ID
     */

    Activity getActivity(String id) {
        for (Activity activity : activities)
            if(activity.getId().equals(id))
                return activity;
        return null;
    }

    /**
     * Change activity info if it currently available (already added and not yet removed)
     * @param activityInfo to change
     * @throws SpotsForVolunteeringException if activity was added (has a non-null ID) and not remove (ID exists).
     */

    void changeActivity(ActivityInfo activityInfo) throws SpotsForVolunteeringException {
            if(activityInfo.getId() == null)
                throw new SpotsForVolunteeringException("Activity with that ID already exists");
        for (Activity activity : activities) {
            if(activity.getId().equals(activityInfo.getId()))
                activity.setActivityInfo(activityInfo);
        }
    }

    /**
     * Remove activity given its ID
     * @param id of activity to remove
     * @throws SpotsForVolunteeringException if ID is not currently associated to and activity
     */

    void removeActivity(String id) throws SpotsForVolunteeringException{
        boolean flag = false;
        for(Activity activity : activities) {
            if(activity.getId().equals(id)) {
                flag = true;
                activities.remove(activity);
            }
        }
        if(!flag)
            throw new SpotsForVolunteeringException("No activity with that ID exists");
    }

    /**
     * Removes old activities with a given limit
     * @param limit to remove all activities prior to that date
     */
    void removeActivitiesOlderThan(Date limit) {
        for(Activity activity : activities) {
            if(activity.getActivityInfo().getEnd().after(limit))
                activities.remove(activity);
        }
    }

    /**
     * Information on all registered activities
     * @return list all activities
     */

    Set<ActivityInfo> getAllActivityInfos() {
        Set<ActivityInfo> infoActivity = new HashSet<>();
        for (Activity activity : activities) {
                infoActivity.add(activity.getActivityInfo());
        }
        return infoActivity;
    }

    /**
     * Information on activities that have started but haven't ended yed
     * @return set of current activities
     */

    Set<ActivityInfo> getUpcomingActivities() {
        Set<ActivityInfo> upcomingActivities = new HashSet<>();
        Date current = new Date();
        for (Activity activity : activities) {
            if (activity.getActivityInfo().getStart().after(current))
                upcomingActivities.add(activity.getActivityInfo());
        }
        return upcomingActivities;
    }

    /**
     * Information on activities that haven't started yed
     * @return set of future activities
     */

    Set<ActivityInfo> getOngoingActivities() {
        Set<ActivityInfo> ongoingActivities = new HashSet<>();
        Date current = new Date();
        for(Activity activity : activities) {
            if (activity.getActivityInfo().getStart().before(current) && activity.getActivityInfo().getEnd().after(current))
                ongoingActivities.add(activity.getActivityInfo());
        }
        return ongoingActivities;
    }
}
