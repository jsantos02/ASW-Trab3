package web.s4v.main;

import web.s4v.shared.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Message broadcasting from activities and tasks.
 * This class is a participant in the Observable design pattern.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 */

public class MessageBroadcaster {
    private List<MessageObserver> observers;

    /**
     * Constructor of MessageBroadcaster
     */
    public MessageBroadcaster() {
        observers = new ArrayList<MessageObserver>();
    }

    /**
     * Add an messageObserver to enable future notification of broadcast messages
     * @param messageObserver to add
     */

    void addObserver(MessageObserver messageObserver) {
        observers.add(messageObserver);
    }

    /**
     * Remove messageObserver to disable further notifications
     * @param messageObserver to remove
     */

    void removeObserver(MessageObserver messageObserver) {
        observers.remove(messageObserver);
    }

    /**
     * Broadcast message to all the currently registered messageObservers
     * @param message to broadcast
     */

    void broadcast(Message message) {
        for (MessageObserver observer : observers) {
            observer.notify(message);
        }
    }
}
