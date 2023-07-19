package web.s4v.main;

import web.s4v.shared.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A task in an activity. Apart from its basic info it manages a ser of enrolled volunteers.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @extends MessageBroadcaster
 * @implements HasPoint, MessageObserver, Serializable
 */

public class Task extends MessageBroadcaster implements HasPoint, MessageObserver, Serializable{
    private TaskInfo taskInfo;
    private Set<Volunteer> enrolledVolunteers = new HashSet<>();

    /**
     * Create a task with given information. An ID is assigned and set on its info.
     * @param taskInfo to initialize the task
     */
    Task(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
        String name = taskInfo.getName();
        byte[] inputBytes = name.getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(inputBytes);
        taskInfo.setId(uuid.toString());
        this.taskInfo = taskInfo;
    }

    /**
     * Enroll volunteer in this task
     * @param volunteer to enroll
     */

    void enroll(Volunteer volunteer) {
        enrolledVolunteers.add(volunteer);
    }

    /**
     * Volunteers enrolled in this task
     * @return set of volunteers
     */

    Set<Volunteer> getEnrolled() {
        return enrolledVolunteers;
    }

    /**
     * Info on the volunteers enrolled in this task
     * @return set of info on volunteers
     */

    Set<VolunteerInfo> getEnrolledInfo() {
        Set<VolunteerInfo> enrolledInfo = new HashSet<>();
        for (Volunteer volunteer : enrolledVolunteers) {
            enrolledInfo.add(volunteer.getVolunteerInfo());
        }
        return enrolledInfo;
    }

    /**
     * Convenience method to obtain this task's id from its info.
     * @return id of activity
     */

    String getId() {
        return taskInfo.getId();
    }

    /**
     * The TaskInfo instance used for defining this task
     * @return taskInfo
     */

    TaskInfo getTaskInfo() {
        return taskInfo;
    }

    /**
     * Checks if volunteer with given is enrolled in this task
     * @param volunteer to check
     * @return true if enrolled; false otherwise.
     */

    public boolean isEnrolled(Volunteer volunteer) {
        return enrolledVolunteers.contains(volunteer);
    }

    /**
     * Change TaskInfo describing this volunteer. It is only changed if it had null id, or it has the same ID.
     * @param taskInfo to change
     */

    void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo.setActivityId(taskInfo.getActivityId());
        this.taskInfo.setId(taskInfo.getId());
        this.taskInfo.setName(taskInfo.getName());
        this.taskInfo.setLongitude(taskInfo.getLongitude());
        this.taskInfo.setStart(taskInfo.getStart());
        this.taskInfo.setEnd(taskInfo.getEnd());
    }

    /**
     * Receive messages from activities and propagates them yo the task observers
     * @param message received, to propagate
     */
    @Override
    public void notify(Message message) {
        broadcast(message);
    }

    /**
     * The longitude where this task takes place, as defined by TaskInfo. Longitude is the "horizontal" (x) coordinate.
     * @return latitude of task
     */

    @Override
    public double getX() {
        return taskInfo.getLatitude();
    }

    /**
     * The latitude where this task takes place, as defined by TaskInfo. Latitude is the "vertical" (y) coordinate,
     * @return longitude of task
     */

    @Override
    public double getY() {
        return taskInfo.getLongitude();
    }
}
