package web.s4v.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Information that characterizes a task.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @implements Serializable
 */

public class TaskInfo implements Serializable {
    private String activityId;
    private Date end;
    private String id;
    private double latitude;
    private double longitude;
    private String name;
    private Date start;

    /**
     * Create an empty instance.
     */
    public TaskInfo() {}

    /**
     * Create an instance with relevant properties.
     * This constructor is typically invoked on the client side,
     * and the ID is latter assigned by the server,
     * when an instance of Task is created.
     * @param activityId of this task
     * @param name of place where this tasks takes place
     * @param latitude of place of task
     * @param longitude of place of task
     * @param start date/time
     * @param end date/time
     * @throws SpotsForVolunteeringException if end date precedes start date
     */
    public TaskInfo(String activityId, String name, double latitude, double longitude, Date start, Date end)
            throws SpotsForVolunteeringException {
        if (end.before(start)) {
            throw new SpotsForVolunteeringException("End date cannot be before start date");
        }
        this.activityId = activityId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.start = start;
        this.end = end;
    }

    /**
     * The id of this task. When created the id is null and is only set when a Task is created.
     * @return id
     */

    public String getId() {
        return id;
    }

    /**
     * Sets an id for this task if none was set yet.
     * Ids cannot be changed after assigned.
     * This method is called only by Task.
     * @param id to set
     */

    public void setId(String id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    /**
     * The activity id of this task.
     * @return id of activity
     */

    public String getActivityId() {
        return activityId;
    }

    /**
     * Sets or changes the activity id of this task.
     * @param activityId to set
     */

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    /**
     * The name of this task.
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * Sets or changes the name for this task.
     * @param name to set
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The latitude where this task takes place
     * @return latitude
     */

    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets or changes the latitude where this task takes place
     * @param latitude to set
     */

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * The longitude where this task takes place
     * @return longitude
     */

    public double getLongitude() {
        return longitude;
    }

    /**
     * Set or change the longitude where this task takes place
     * @param longitude to set
     */

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * The start date of this task.
     * @return start date
     */

    public Date getStart() {
        return start;
    }

    /**
     * Sets or changes the start date for this task.
     * @param start date to set
     */

    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * The end date of this task.
     * @return end date
     */

    public Date getEnd() {
        return end;
    }

    /**
     * Sets or changes the end date for this task.
     * @param end date to set
     */

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskInfo taskInfo = (TaskInfo) o;
        return Double.compare(taskInfo.latitude, latitude) == 0 && Double.compare(taskInfo.longitude, longitude) == 0 && Objects.equals(activityId, taskInfo.activityId) && Objects.equals(end, taskInfo.end) && Objects.equals(id, taskInfo.id) && Objects.equals(name, taskInfo.name) && Objects.equals(start, taskInfo.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId, end, id, latitude, longitude, name, start);
    }
}
