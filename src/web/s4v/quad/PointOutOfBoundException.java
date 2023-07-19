package web.s4v.quad;

/**
 * Exception raised when the quad tree is used with a point outside its boundaries.
 * Programmers can easily avoid these exceptions by checking points before attempting to insert them in a quad tree.
 * Since it extends RuntimeException, it is not mandatory to handle this kind of exception.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 *
 * @extends RuntimeException
 */
public class PointOutOfBoundException extends RuntimeException{
	public PointOutOfBoundException() {
	}
}
