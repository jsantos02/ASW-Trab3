package web.s4v.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * Class representing a message from a volunteer to broadcast to other volunteers
 * either in the same task or in the same activity
 * Has getters and setters to set and get information about a certain activity.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @implements Serializable
 */

public class Message implements Serializable {
    private final Date date;
    private String from;
    private String text;
    private String to;

    /**
     * Empty message. Data of creation is automatically recorded.
     */
    public Message() {
        this.date = new Date();
        this.from = null;
        this.text = null;
    }

    /**
     * Create a message from given volunteer id and with given text.
     * Data of creation is automatically recorded.
     * @param from id of volunteer who sent the message
     * @param text of message
     */
    @Deprecated
    public Message(String from, String text) {
        this.date = new Date();
        this.from = from;
        this.text = text;
    }

    /**
     * Create a message from given volunteer id and who receives it with given text.
     * Data of creation is automatically recorded.
     * @param from id of volunteer who sent the message
     * @param to id of volunteer who receives message
     * @param text of message
     */
    public Message(String from, String to, String text) {
        this.date = new Date();
        this.from = from;
        this.to = to;
        this.text = text;
    }
    /**
     * ID of the volunteer who sent the message
     * @return ID of the volunteer
     */

    public String getFrom() {
        return from;
    }

    /**
     * ID of the volunteer received sent the message
     * @return ID of the volunteer
     */
    public String getTo() { return to; }

    /**
     * Text of message
     * @return text
     */

    public String getText() {
        return text;
    }

    /**
     * Moment when message was created
     * @return date
     */

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(date, message.date) && Objects.equals(from, message.from) && Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, text, date);
    }
}