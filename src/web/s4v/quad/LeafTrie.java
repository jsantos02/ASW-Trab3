package web.s4v.quad;

import web.s4v.shared.HasPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * A Trie that has no descendants. This class corresponds to the Leaf in the Composite design pattern.
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

public class LeafTrie<T extends HasPoint> extends Trie<T> implements Element<T> {
	
	private ArrayList<T> points;
	boolean visited;

	/**
	 * Initialize LeafTrie
	 * @param topLeftX to set left X
	 * @param topLeftY to set left Y
	 * @param bottomRightX to set right X
	 * @param bottomRightY to set right Y
	 */

	LeafTrie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		super(topLeftX, topLeftY, bottomRightX, bottomRightY);
		points = new ArrayList<T>();
		visited = false;
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
	 * @param nodes set of HasPoint for collecting points
	 */

	void collectAll(Set<T> nodes) {
        nodes.addAll(this.points);
	}

	/**
	 * Collect points at a distance smaller or equal to radius from (x,y) and place them in given list
	 * @param x coordinate of point
	 * @param y coordinate of point
	 * @param radius from given point
	 * @param nodes set for collecting points
	 */
	
	void collectNear(double x, double y, double radius, Set<T> nodes) {
		for(T point : this.points) {
			if(isNear(x, y, radius, point)) {
				nodes.add(point);
			}
		}
	}

	/**
	 * To determine if a given point is near the circle
	 * @param x coordinate of point
	 * @param y coordinate of point
	 * @param radius from given point
	 * @param point a point
	 * @return true if near, false if far
	 */
	
	private boolean isNear(double x, double y, double radius, T point) {
		double dist = super.getDistance(x, y, point.getX(), point.getY());
		return dist <= radius;
	}

	/**
	 * Delete given point
	 * @param point to delete
	 */

	void delete(T point) {
		for(int i = 0; i<this.points.size(); i++) {
			if(point.getX() == this.points.get(i).getX() 
				&& point.getY() == this.points.get(i).getY()) {
				this.points.remove(i);
				return;
			}
		}
	}

	/**
	 * Find a recorded point with the same coordinates of given point
	 * @param point with requested coordinates
	 * @return recorded point, if found; null otherwise
	 */

	T find(T point) {
		for(T pointInList : this.points) {
			if(point.getX() == pointInList.getX() && point.getY() == pointInList.getY())
				return pointInList;
		}	
		return null;
	}

	/**
	 * A collection of points currently in this leaf
	 * @return collection of points
	 */

	Collection<T> getPoints() {
		Collection<T> coll = new ArrayList<T>(super.getCapacity());
		coll.addAll(this.points);
		return coll;
	}

	/**
	 * Insert given point
	 * @param point to be inserted
	 * @return changed parent node
	 */

	Trie<T> insert(T point) {
		if(this.points.size() + 1 > Trie.getCapacity()) {
			Trie<T> trie = new NodeTrie<T>
				(topLeftX, topLeftY, bottomRightX, bottomRightY);
			
			trie.insert(point);
			for(T p : this.points) 
				trie.insert(p);

			this.points.clear();
			return trie;
		}
		
		this.points.add(point);
		return this;
	}

	/**
	 * Insert given point, replacing existing points in same location
	 * @param point point to be inserted
	 * @return changed parent node
	 */

	Trie<T> insertReplace(T point) {
		this.points.removeAll(points);
		this.points.add(point);
		return this;
	}

	@Override
	public String toString() {
		return "LeafTrie []";
	}

}