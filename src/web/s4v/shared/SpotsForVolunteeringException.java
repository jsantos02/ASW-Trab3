package web.s4v.shared;

import java.io.Serializable;

/**
 * An exception thrown by spots for volunteering.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @extends Exception
 * @implements Serializable
 */

public class SpotsForVolunteeringException extends Exception implements Serializable {

    public SpotsForVolunteeringException(String message) {
        super(message);
    }

    public SpotsForVolunteeringException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SpotsForVolunteeringException() {
    }
}
