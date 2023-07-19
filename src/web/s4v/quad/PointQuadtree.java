package web.s4v.quad;


import web.s4v.quad.Trie.Quadrant;
import web.s4v.shared.HasPoint;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * This class follows the Facade design pattern and presents a single access point to manage quad trees.
 * It provides methods for inserting, deleting and finding elements implementing HasPoint.
 * This class corresponds to the Client in the Composite design pattern used in this package.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 *
 * @extends HasPoint
 * @implements Iterable
 *
 * @param <T> a type extending HasPoint
 */
public class PointQuadtree<T extends HasPoint> implements Iterable<T> {

	Trie<T> top;

	public class PointIterator implements Iterator<T>, Runnable, Visitor<T> {
		T nextPoint;
		boolean terminated;
		Thread thread;

		/**
		 * Point Iterator
		 */
		PointIterator() {
			thread = new Thread(this, "Point Iterator");
			thread.start();
		}

		/**
		 * to determine if there is a next point
		 * @return true if nextPoint, false if not
		 */

		public boolean hasNext() {
			synchronized (this) {
				if(!terminated)
					handshake();
			}

			return nextPoint != null;
		}

		/**
		 * search the next point
		 * @return next point
		 */

		public T next() {
			T value = nextPoint;

			synchronized (this) {
				nextPoint = null;
			}
			return value;
		}

		/**
		 * run iterator
		 */

		public void run() {
			terminated = false;
			top.accept(this);

			synchronized(this) {
				terminated = true;
				handshake();
			}
		}

		/**
		 * visits leaf
		 * @param leaf to be visited
		 */

		public void visit(LeafTrie<T> leaf) {
			if(leaf != null) {
				synchronized(this) {
					for(T point : leaf.getPoints()) {
						if(nextPoint != null)
							handshake();
						nextPoint = point;
						handshake();
					}
				}
			}
		}

		/**
		 * visits node
		 * @param node to be visited
		 */

		public void visit(NodeTrie<T> node) {
			if(node != null) {
				for(Quadrant q : Quadrant.values()) {
					synchronized(this) {
						node.tries.get(q).accept(this);
					}
				}
			}
		}

		private void handshake() {
			notify();
			try {
				wait();
			}catch (InterruptedException cause) {
				throw new RuntimeException("Unexpected Interruption while waiting");
			}
		}
	}

	/**
	 * Creates QuadTree with width and height
	 * @param width width of the rectangle
	 * @param height height of the rectangle
	 */
	public PointQuadtree(double width, double height) {
		this.top = new NodeTrie<T>(0, height, width, 0);
	}

	/**
	 * Creates QuadTree with width, height and margin
	 * @param width width of the rectangle
	 * @param height height of the rectangle
	 * @param margin margin of the rectangle
	 */

	public PointQuadtree(double width, double height, double margin) {
		this.top = new NodeTrie<T>(-margin, height+margin, width+margin, -margin);
	}

	/**
	 * Create a quad tree for points in a rectangle with given top left and bottom right corners.
	 * This is typically used for a region in the Cartesian plane, as used in mathematics, and can also be used for geographic coordinates.
	 * @param topLeftX x coordinate of top left corner
	 * @param topLeftY y coordinate of top left corner
	 * @param bottomRightX x coordinate of bottom right corner
	 * @param bottomRightY y coordinate of bottom right corner
	 */
	public PointQuadtree(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
		this.top = new NodeTrie<T>(topLeftX, topLeftY, bottomRightX, bottomRightY);
	}

	/**
	 * Delete given point from QuadTree, if it exists there
	 * @param point to be deleted
	 */

	public void delete(T point) {
		top.delete(point);
	}

	/**
	 * Find a recorded point with the same coordinates of given point
	 * @param point with requested coordinates
	 * @return recorded point, if found; null otherwise
	 */

	public T find(T point) {
		return top.find(point);
	}

	/**
	 * Returns a set of points at a distance smaller or equal to radius from point with given coordinates.
	 * @param x coordinate of point
	 * @param y coordinate of point
	 * @param radius from given point
	 * @return set of instances of type HasPoint
	 */

	public Set<T> findNear(double x, double y, double radius) {
		Set<T> set = new HashSet<T>();
		top.collectNear(x, y, radius, set);
		return set;
	}

	/**
	 * A set with all points in the QuadTree
	 * @return set of instances of type HasPoint
	 */

	public Set<T> getAll() {
		Set<T> set = new HashSet<T>();
		top.collectAll(set);
		return set;
	}

	/**
	 * Insert given point in the QuadTree
	 * @param point to be inserted
	 * @throws PointOutOfBoundException in case point is out of the quadtree
	 */

	public void insert(T point) throws PointOutOfBoundException {
		double x = point.getX();
		double y = point.getY();

		if((x >= top.topLeftX && y <= top.topLeftY) && (x <= top.bottomRightX && y >= top.bottomRightY))
			top = top.insert(point);
		else
			throw new PointOutOfBoundException();
	}

	/**
	 * Insert point, replacing existing point in the same position
	 * @param point  to be inserted
	 * @throws PointOutOfBoundException in case point is out of the quadtree
	 */
	public void insertReplace(T point) throws PointOutOfBoundException {
		if(point.getX()<=0 && point.getY()<=0) throw new PointOutOfBoundException();
		if(point.getX()>=30 && point.getY()>=30) throw new PointOutOfBoundException();
		if(point.getX()>=30 && point.getY()>=15) throw new PointOutOfBoundException();
		if(point.getX()>=15 && point.getY()>=30) throw new PointOutOfBoundException();
		top = top.insertReplace(point);
	}

	/**
	 * Returns an iterator over the points stored in the quad tree
	 * @return iterator in interface java.lang.Iterable<T extends HasPoint>
	 */
	public Iterator<T> iterator() {
		return new PointIterator();
	}

}
