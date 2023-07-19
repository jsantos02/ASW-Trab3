package web.s4v.quad;

import web.s4v.shared.HasPoint;
/**
 * The Visitor interface, part of the abstract layer of the design pattern with the same name.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 *
 * @extends HasPoint
 */
public interface Visitor<T extends HasPoint> {
	/**
	 * Do a visit to a leaf in the composite structure
	 * @param leaf to be visited
	 */
	void visit(LeafTrie<T> leaf);

	/**
	 * Do a visit to a node in the composite structure
	 * @param node to be visited
	 */

	void visit(NodeTrie<T> node);

}
