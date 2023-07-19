package web.s4v.main;

import web.s4v.shared.*;

import java.io.Serializable;
import java.util.*;

/**
 * A volunteer is characterized by personal information (VolunteerInfo),
 * a set of Activity (s)he created and is responsible for,
 * a set of Tasks in which (s)he enrolled, and received Messages related to the above.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @implements MessageObserver, Serializable
 */

public class Volunteer implements MessageObserver, Serializable {
    static int PRIVATE_KEY_SIZE = 16;
    private VolunteerInfo volunteerInfo;
    private AuthenticatedVolunteerInfo authVolunteerInfo;
    private List<Message> messages = new ArrayList<>();
    Set<Activity> activities = new HashSet<>();
    Set<Task> tasks = new HashSet<>();

    /**
     * Create a volunteer with given info. An ID and a private key are generated to this volunteer.
     * The ID is stored in the VolunteerInfo instance.
     * @param volunteerInfo to instance volunteer
     */
    public Volunteer(VolunteerInfo volunteerInfo) {
        this.volunteerInfo = volunteerInfo;
        String name = volunteerInfo.getName();
        byte[] inputBytes = name.getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(inputBytes);
        volunteerInfo.setId(uuid.toString());
        this.volunteerInfo = volunteerInfo;
        authVolunteerInfo = new AuthenticatedVolunteerInfo(volunteerInfo, generatePrivateKey());
    }

    /**
     * A random string of printable characters.
     * @return key of random printable characters
     */

    String generatePrivateKey() {
        StringBuilder sb = new StringBuilder(PRIVATE_KEY_SIZE);
        for (int i = 0; i < PRIVATE_KEY_SIZE; i++) {
            int randChar = (int) (Math.random() * (127 - 33 + 1)) + 33;
            sb.append((char) randChar);
        }
        return sb.toString();
    }

    /**
     * Authenticates given key against the stored private key.
     * @param key to check
     * @return true is keys match; otherwise false
     */
    boolean authenticate(String key) {
       return (key.equals(authVolunteerInfo.getPrivateKey())) ;
    }

    /**
     * Convenience method to obtain this volunteer's id from its info.
     * @return id of activity
     */

    public String getId() {
        return volunteerInfo.getId();
    }

    /**
     * Information describing this volunteer
     * @return info
     */

    VolunteerInfo getVolunteerInfo() {
        return volunteerInfo;
    }

    /**
     * Complete Information describing this volunteer, including Private key assigned to this volunteer on instantiation.
     * This key cannot be changed and is used for authentication.
     * @return authenticated info
     */

    AuthenticatedVolunteerInfo getAuthenticatedVolunteerInfo() {
        return authVolunteerInfo;
    }

    /**
     * Change the VolunteerInfo describing this volunteer.
     * The new info cannot null, and it only replaces the existing info if
     * it had null id
     * it has the same ID.
     * @param volunteerInfo to change
     */

    public void setVolunteerInfo(VolunteerInfo volunteerInfo) {
        if(volunteerInfo.getName() == null) {
            if (volunteerInfo.getId() == null || volunteerInfo.getId().equals(this.volunteerInfo.getId())) {
                this.volunteerInfo.setId(volunteerInfo.getId());
                this.volunteerInfo.setName(volunteerInfo.getName());
            }
        }
    }

    /**
     * List of the latest messages received by Object.notify(). The returned messages are cleared afterward.
     * @return messages
     */

    List<Message> getMessages() {
        return messages;
    }

    /**
     * Add an activity this volunteer created
     * @param activity to add
     */

    void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Remove activities with end date before given limit which this volunteer created
     * @param limit to keep activity
     */

    void removeActivitiesOlderThan(Date limit) {
        for(Activity activity : activities) {
            if(activity.getActivityInfo().getEnd().after(limit))
                activities.remove(activity);
        }
    }

    /**
     * Get all activities this volunteer created
     * @return set of activities
     */

    Set<ActivityInfo> getAllActivities() {
        Set<ActivityInfo> infoActivity = new HashSet<>();
        for (Activity activity : activities) {
                if(activity.getOwnerId().equals(volunteerInfo.getId()))
                    infoActivity.add(activity.getActivityInfo());
        }
        return infoActivity;
    }

    /**
     * Get upcoming activities (not stared yet) created by this volunteer
     * @return set of activities
     */

    Set<ActivityInfo> getUpcomingActivities() {
        Set<ActivityInfo> upcomingActivities = new HashSet<>();
        Date current = new Date();
        for (Activity activity : activities) {
            if (activity.getActivityInfo().getStart().after(current) && volunteerInfo.getId().equals(activity.getOwnerId()))
                upcomingActivities.add(activity.getActivityInfo());
        }
        return upcomingActivities;
    }

    /**
     * Get ongoing (already started but not yet ended) activities created by this volunteer
     * @return set of activities
     */

    Set<ActivityInfo> getOngoingActivities() {
        Set<ActivityInfo> ongoingActivities = new HashSet<>();
        Date current = new Date();
        for(Activity activity : activities) {
            if ((activity.getActivityInfo().getStart().before(current) && activity.getActivityInfo().getEnd().after(current)) && volunteerInfo.getId().equals(activity.getOwnerId()))
                ongoingActivities.add(activity.getActivityInfo());
        }
        return ongoingActivities;
    }

    /**
     * Add a task in which this volunteer is enrolled
     * @param task to add
     */

    void addTask(Task task) {
        Volunteer volunteer = new Volunteer(volunteerInfo);
        if(task.isEnrolled(volunteer))
            tasks.add(task);
    }

    /**
     * Remove tasks with end date before given limit in which this volunteer is enrolled
     * @param limit to keep tasks
     */

    public void removeTasksOlderThan(Date limit) {
        for(Task task : tasks) {
            if(task.getTaskInfo().getEnd().after(limit))
                tasks.remove(task);
        }
    }

    /**
     * Get all tasks in which this volunteer is enrolled
     * @return set of tasks
     */

    Set<TaskInfo> getAllTasks() {
        Set<TaskInfo> tasksInfo = new HashSet<>();
        Volunteer volunteer = new Volunteer(volunteerInfo);
        for(Task task : tasks) {
            if(task.isEnrolled(volunteer))
                tasksInfo.add(task.getTaskInfo());
        }
        return tasksInfo;
    }

    /**
     * Get upcoming tasks (not stared yet) in which this volunteer is enrolled
     * @return set of tasks
     */

    Set<TaskInfo> getUpcomingTasks() {
        Set<TaskInfo> upcomingTasks = new HashSet<>();
        Volunteer volunteer = new Volunteer(volunteerInfo);
        Date current = new Date();
        for (Task task : tasks) {
            if (task.getTaskInfo().getStart().after(current) && task.isEnrolled(volunteer))
                upcomingTasks.add(task.getTaskInfo());
        }
        return upcomingTasks;
    }

    /**
     * Get ongoing (already started but not yet ended) in which this volunteer is enrolled
     * @return set of tasks
     */

    Set<TaskInfo> getOngoingTasks() {
        Set<TaskInfo> ongoingTasks = new HashSet<>();
        Volunteer volunteer = new Volunteer(volunteerInfo);
        Date current = new Date();
        for (Task task : tasks) {
            if ((task.getTaskInfo().getStart().before(current) && task.getTaskInfo().getStart().after(current))  && task.isEnrolled(volunteer))
                ongoingTasks.add(task.getTaskInfo());
        }
        return ongoingTasks;
    }

    /**
     * Receive and store message
     * @param message received
     */

    @Override
    public void notify(Message message) {
        messages.add(message);
    }


}
