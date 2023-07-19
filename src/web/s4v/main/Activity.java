package web.s4v.main;

import web.s4v.shared.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Class representing an Activity.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @extends MessageBroadcaster
 * @implements Serializable
 *
 */
public class Activity extends MessageBroadcaster implements Serializable {
    ActivityInfo activityInfo;
    Set<Task> tasks = new HashSet<>();

    /**
     * Create an activity with given information. An ID is assigned and set on its info.
     * @param activityInfo to initialize activity
     */
    Activity(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
        String name = activityInfo.getName();
        byte[] inputBytes = name.getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(inputBytes);
        activityInfo.setId(uuid.toString());
        this.activityInfo = activityInfo;
    }

    /**
     * Get information on this activity
     * @return info on the activity
     */
    ActivityInfo getActivityInfo() {
        return activityInfo;
    }

    /**
     * Chance information on this activity. The id and ownerId cannot differ from existing ones
     * @param activityInfo, to set
     * @throws SpotsForVolunteeringException, if id or ownerId differ
     */
    void setActivityInfo(ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        if(!(this.activityInfo.getOwnerId().equals(activityInfo.getOwnerId())) || !(this.activityInfo.getId().equals(activityInfo.getId()))) throw new SpotsForVolunteeringException("different ownerID or ID");
        this.activityInfo.setName(activityInfo.getName());
        this.activityInfo.setStart(activityInfo.getStart());
        this.activityInfo.setEnd(activityInfo.getEnd());
    }

    /**
     * Convenience method to obtain this activity's id from its info.
     * @return the id of the activity
     */
    String getId() {
        return activityInfo.getId();
    }

    /**
     * Convenience method to obtain this activity's owner's id from its info.
     * @return the owner id of this activity
     */
    String getOwnerId() { return activityInfo.getOwnerId(); }

    /**
     * Add a task to this activity based on given information.
     * @param taskInfo on task to create
     * @return id generated for this task
     * @throws SpotsForVolunteeringException if task already has an ID
     */
    Task addTask(TaskInfo taskInfo) throws SpotsForVolunteeringException {
        if(taskInfo.getId() != null) throw new SpotsForVolunteeringException("already has an id");
        String name = taskInfo.getName();
        byte[] inputBytes = name.getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(inputBytes);
        taskInfo.setId(uuid.toString());
        Task task = new Task(taskInfo);
        tasks.add(task);
        return task;
    }

    /**
     * Forget existing task and remove it as message observer
     * @param id of the task
     * @return the removed task or null if task is non-existent
     */
    Task removeTask(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                tasks.remove(task);
                removeObserver(task);
                return task;
            }
        }
        return null;
    }

    /**
     * Task with given id
     * @param id of the task
     * @return task or null if task is non-existent
     */

    Task getTask(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    /**
     * A set with information on the tasks of this activity.
     * @return set of tasks
     */
     Set<TaskInfo> getTaskInfos() {
        Set<TaskInfo> infoTask = new HashSet<>();
         for (Task task : tasks) {
             infoTask.add(task.getTaskInfo());
         }
        return infoTask;
    }

    /**
     * Check if volunteer is this activity's owner.
     * @param volunteer to check
     * @return true is is the owner; false otherwise.
     */
    boolean isOwner(Volunteer volunteer) {
        return ((volunteer.getId().equals(activityInfo.getOwnerId())));
    }
}
