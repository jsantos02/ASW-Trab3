package web.s4v.shared;

import java.io.Serializable;
import java.util.Date;

import static web.s4v.shared.IDGenerator.generateID;

/**
 * Information that characterizes a task.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @implements Serializable
 */
public class ActivityInfo implements Serializable{
    private String id;
    private String ownerId;
    private String name;
    private Date start;
    private Date end;

    /**
     * Construct an empty activity.
     */
    public ActivityInfo() {
        this.id = generateID();
        this.name = null;
        this.start = null;
        this.end = null;
        this.ownerId = null;

    };

    /**
     * Create an instance with relevant properties initialized.
     * This constructor is typically invoked on the client side,
     * and the ID is latter assigned by the server,
     * when an instance of Volunteer is created.
     * @param ownerId of the volunteer responsible for this activity
     * @param name of activity
     * @param start date/time
     * @param end date/time
     * @throws SpotsForVolunteeringException if end date precedes start date
     */
    public ActivityInfo(String ownerId, String name, Date start, Date end) throws SpotsForVolunteeringException {
        if (end.before(start)) throw new SpotsForVolunteeringException("end date can't be written first");
      //  if (ownerId == null || name == null || start == null) throw new SpotsForVolunteeringException("null values");
        this.id = generateID();
        this.ownerId = ownerId;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    /**
     * The id of this activity. When created the id is null and is only set when a Activity is created.
     * @return id
     */
    public String getId() {return id;}

    /**
     * The owner's id of this activity. This property cannot be changed.
     * @return owner ID
     */
    public String getOwnerId() {return ownerId;}

    /**
     * The name of this activity.
     * @return name of the activity
     */
    public String getName() {return name;}

    /**
     * The start date of this activity.
     * @return start date
     */
    public Date getStart() {return start;}

    /**
     * The end date of this activity.
     * @return end date
     */
    public Date getEnd() {return end;}

    /**
     * Sets an id for this activity if none was set yet.
     * Ids cannot be changed after assigned.
     * This method is called only by Activity.
     * @param id to set
     */
    public void setId(String id) { if(this.id == null) this.id = id; }

    /**
     * set the owner ID
     * @param ownerId to set
     */
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    /**
     * Sets a name for this activity.
     * @param name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets a start date for this activity.
     * @param start to set
     */
    public void setStart(Date start) { this.start = start; }

    /**
     * Sets a end date for this activity.
     * @param end to set
     */
    public void setEnd(Date end) { this.end = end; }

}
