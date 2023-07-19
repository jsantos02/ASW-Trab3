package web.s4v.quad;

import web.s4v.shared.HasPoint;

import java.util.Set;
/**
 * Abstract class common to all classes implementing the trie structure.
 * Defines methods required by those classes and provides general methods for checking overlaps and computing distances.
 * This class corresponds to the Component in the Composite design pattern.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 *
 * @extends HasPoint
 * @implements Element<T>
 *
 * @param <T> type that extends interface HasPoint
 */
public abstract class Trie<T extends HasPoint> implements Element<T> {

	protected double bottomRightX;
	protected double bottomRightY;
	protected double topLeftX;
	protected double topLeftY;
	static int capacity;

	/**
	 * Quadrants of NodeTries. Names are from the compass NE = North East, etc.
	 */
	static enum Quadrant {
		SW, NW, SE, NE
	}

	/**
	 * Initializes trie
	 * @param topLeftX x coordinate of top left corner
	 * @param topLeftY y coordinate of top left corner
	 * @param bottomRightX x coordinate of bottom right corner
	 * @param bottomRightY y coordinate of bottom right corner
	 */
	protected Trie(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		this.bottomRightX = bottomRightX;
		this.bottomRightY = bottomRightY;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;

	}

	/**
	 * Collect all points in this node and its descendants in given set
	 * @param points set of HasPoint for collecting points
	 */
	abstract void collectAll(Set<T> points);

	/**
	 * Collect points at a distance smaller or equal to radius from (x,y) and place them in given list
	 * @param x coordinate of point
	 * @param y coordinate of point
	 * @param radius from given point
	 * @param points set for collecting points
	 */
	abstract void collectNear(double x, double y, double radius, Set<T> points);

	/**
	 * Delete given point
	 * @param point to delete
	 */
	abstract void delete(T point);

	/**
	 * Find a recorded point with the same coordinates of given point
	 * @param point with requested coordinates
	 * @return recorded point, if found; null otherwise
	 */
	abstract T find(T point);

	/**
	 * Insert given point
	 * @param point to be inserted
	 * @return changed parent node
	 */
	abstract Trie<T> insert(T point);

	/**
	 * Insert given point, replacing existing points in same location
	 * @param point to be inserted
	 * @return changed parent node
	 */
	abstract Trie<T> insertReplace(T point);

	/**
	 * Get capacity of a bucket
	 * @return capacity
	 */
	static int getCapacity() {
		return Trie.capacity;
	}

	/**
	 * Set capacity of a bucket
	 * @param capacity of bucket
	 */
	static void setCapacity(int capacity) {
		Trie.capacity = capacity;
	}

	/**
	 * Euclidean distance between two pair of coordinates of two points
	 * @param x1 x coordinate of first point
	 * @param y1 y coordinate of first point
	 * @param x2 x coordinate of second point
	 * @param y2 y coordinate of second point
	 * @return distance between given points
	 */
	static double getDistance(double x1, double y1, double x2, double y2) {
		double distX = Math.pow((x2-x1), 2);
		double distY = Math.pow((y2-y1), 2);
		return Math.sqrt(distY+distX);
	}

	/**
	 * Check if overlaps with given circle
	 * @param x coordinate of circle
	 * @param y coordinate of circle
	 * @param radius of circle
	 * @return true if overlaps and false otherwise
	 */

	boolean overlaps(double x, double y, double radius) {
		
		if((x >= this.topLeftX && x <= this.bottomRightX) 
				&& (y <= this.topLeftY && y >= this.bottomRightY))
			return true;

		
		double x1 = Math.max(this.topLeftX, 
				Math.min(x, this.bottomRightX));
		
		double y1 = Math.max(this.bottomRightY, 
				Math.min(y, this.topLeftY));
		
		double distance = getDistance(x1, y1, x, y);
		
		return distance <= radius;
	}

	@Override
	public String toString() {
		return null;
	}
}
