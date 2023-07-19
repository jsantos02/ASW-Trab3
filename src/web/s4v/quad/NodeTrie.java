package web.s4v.quad;


import web.s4v.shared.HasPoint;

import java.util.*;

/**
 * Trie with 4 sub tries with equal dimensions covering all its area.
 * This class corresponds to the Container in the Composite design pattern.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 *
 * @extends HasPoint, Trie
 * @implements Element
 *
 * @param <T> type that extends HasPoint
 */

public class NodeTrie<T extends HasPoint> extends Trie<T> implements Element<T> {

	Map<Trie.Quadrant, Trie<T>> tries;
	boolean visited;

	/**
	 * Initialize NodeTrie
	 * @param topLeftX to set left X
	 * @param topLeftY to set left Y
	 * @param bottomRightX to set right X
	 * @param bottomRightY to set right Y
	 */

	NodeTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
		
		Trie<T> SW = new LeafTrie<T>(topLeftX, (topLeftY+bottomRightY)/2, 
				(bottomRightX+topLeftX)/2, bottomRightY);
		
		Trie<T> NW = new LeafTrie<T>(topLeftX, topLeftY, 
				(bottomRightX+topLeftX)/2, (bottomRightY+topLeftY)/2);
		
		Trie<T> SE = new LeafTrie<T>((bottomRightX+topLeftX)/2, (topLeftY+bottomRightY)/2, 
				bottomRightX, bottomRightY);
		
		Trie<T> NE = new LeafTrie<T>((topLeftX+bottomRightX)/2, topLeftY, 
				bottomRightX, (bottomRightY+topLeftY)/2);
		
		this.tries = initializeQuadrants(SW, NW, SE, NE);
		visited = false;
	}

	/**
	 * Initialize the quadrants of the compass
	 * @param SW compass
	 * @param NW compass
	 * @param SE compass
	 * @param NE compass
	 * @return a new node according to compass coordinates
	 */

	Map<Trie.Quadrant, Trie<T>> initializeQuadrants(Trie<T> SW, Trie<T> NW, Trie<T> SE, Trie<T> NE) {
		
		Map<Trie.Quadrant, Trie<T>> newNode = new HashMap<Trie.Quadrant, Trie<T>>();
			
		newNode.put(Quadrant.SE, SE);
		newNode.put(Quadrant.NE, NE);
		newNode.put(Quadrant.SW, SW);
		newNode.put(Quadrant.NW, NW);
		
		return newNode;
	}

	/**
	 * Accept a visitor to operate on a node of the composite structure
	 * @param visitor to the node
	 */

	public void accept(Visitor<T> visitor) {
		visitor.visit(this);
	}

	/**
	 * Collect all points in this node and its descendants in given set
	 * @param set set of HasPoint for collecting points
	 */

	void collectAll(Set<T> set) {
		for(Quadrant q : Quadrant.values()) {
				tries.get(q).collectAll(set);
		}
	}

	/**
	 * Collect points at a distance smaller or equal to radius from (x,y) and place them in given list
	 * @param x coordinate of point
	 * @param y coordinate of point
	 * @param radius from given point
	 * @param nodes set for collecting points
	 */

	void collectNear(double x, double y, double radius, Set<T> nodes) {
		for(Quadrant q : Quadrant.values()) {
			if(tries.get(q).overlaps(x, y, radius)) 
				this.tries.get(q).collectNear(x, y, radius, nodes);
		}
	}

	/**
	 * Delete given point
	 * @param point to delete
	 */

	void delete(T point) {
		this.tries.get(quadrantOf(point)).delete(point);
	}

	/**
	 * Find a recorded point with the same coordinates of given point
	 * @param point with requested coordinates
	 * @return recorded point, if found; null otherwise
	 */

	T find(T point) {
		return this.tries.get(quadrantOf(point)).find(point);
	}

	/**
	 * A collection of tries that descend from this one
	 * @return collection tries
	 */

	Collection<Trie<T>> getTries() {
		Collection<Trie<T>> retVal = new ArrayList<Trie<T>>();
		
		for(Quadrant q : Quadrant.values()) {
			retVal.add(tries.get(q));
		}
		
		return retVal;
	}

	/**
	 * Insert given point
	 * @param point to be inserted
	 * @return changed parent node
	 */

	Trie<T> insert(T point) {
		Quadrant quad = quadrantOf(point);
		Trie<T> trie = tries.get(quad).insert(point);
		
		tries.put(quad, trie);
		return this;
	}

	/**
	 * Insert given point, replacing existing points in same location
	 * @param point point to be inserted
	 * @return changed parent node
	 */

	Trie<T> insertReplace(T point) {
		Quadrant quad = quadrantOf(point);
		Trie<T> trie = tries.get(quad).insertReplace(point);
		
		tries.put(quad, trie);
		return this;
	}

	/**
	 * Determine quadrants
	 * @param point point to determine
	 * @return quadrant
	 */

	Quadrant quadrantOf(T point) {
		if (point.getX() < ((topLeftX+bottomRightX)/2)) {
			if (point.getY() < ((topLeftY+bottomRightY)/2))
				return Quadrant.SW;
			else
				return Quadrant.NW;
		} 
		else {
			if (point.getY() < ((topLeftY+bottomRightY)/2))
				return Quadrant.SE;
			else
				return Quadrant.NE;
		}
	}

	@Override
	public String toString() {
		return "NodeTrie [tries=" + tries + "]";
	}

}