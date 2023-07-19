package web.s4v.quad;

import web.s4v.shared.HasPoint;
/**
 * The Element interface, part of the abstract layer of the Visitor design pattern.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 *
 * @extends HasPoint
 */
public interface Element<T extends HasPoint> {
	/**
	 * Accept a visitor to operate on a node of the composite structure
	 * @param visitor to the node
	 */
	void accept(Visitor<T> visitor);
}