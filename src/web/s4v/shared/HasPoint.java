package web.s4v.shared;
/**
 * Interface for representing an object with x and y coordinates.
 * Has only getters.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 */

public interface HasPoint {
    /**
     * Gets x
     * @return x;
     */
    double getX();

    /**
     * Gets y
     * @return y;
     */
    double getY();
}