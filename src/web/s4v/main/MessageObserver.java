package web.s4v.main;
import web.s4v.shared.*;


/**
 * Observer of messages broadcast by activities and tasks.
 * This class is a participant in the Observable design pattern.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 */
public interface MessageObserver {
    /**
     * receive a message notification
     * @param message received
     */
    void notify(Message message);
}
