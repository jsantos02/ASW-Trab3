package web.s4v.main;

import web.s4v.quad.PointQuadtree;
import web.s4v.shared.TaskInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing an Activity.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 *
 * @extends PointQuadtree<Task>
 */

public class TaskQuad extends PointQuadtree<Task> {

    static final double NORTHERNMOST_LATITUDE = 42.154058;
    static final double SOUTHERNMOST_LATITUDE = 36.960158;
    static final double EASTERNMOST_LONGITUDE = -6.190217;
    static final double WESTERNMOST_LONGITUDE = -9.500552;

    static double northernmostLatitude = NORTHERNMOST_LATITUDE;
    static double southernmostLatitude = SOUTHERNMOST_LATITUDE;
    static double westernmostLongitude = WESTERNMOST_LONGITUDE;
    static double easternmostLongitude = EASTERNMOST_LONGITUDE;

    private static TaskQuad taskQuad = new TaskQuad();

    /**
     * Reset quad to its initial state. Use it only for unit testing.
     */
    void reset() {
        taskQuad = new TaskQuad();
    }

    private TaskQuad() {
        super(westernmostLongitude, northernmostLatitude, easternmostLongitude, southernmostLatitude);
    }

    /**
     * Singleton
     * @return taskQuad
     */
    static TaskQuad getInstance() {
        if (taskQuad == null)
            taskQuad = new TaskQuad();
        return taskQuad;
    }

    /**
     * The northernmost latitude used in quad trees.
     * @return northernmost latitude
     */
    public static double getNorthernmostLatitude() {
        return northernmostLatitude;
    }

    /**
     * Change the northernmost latitude used in quad trees.
     * @param northernmostLatitude to set
     */

    public static void setNorthernmostLatitude(double northernmostLatitude) {
        TaskQuad.northernmostLatitude = northernmostLatitude;
    }

    /**
     * The southernmost latitude used in quad trees.
     * @return southernmost latitude
     */

    public static double getSouthernmostLatitude() {
        return southernmostLatitude;
    }

    /**
     * Change the southernmost latitude used in quad trees.
     * @param southernmostLatitude to set
     */

    public static void setSouthernLatitude(double southernmostLatitude) {
        TaskQuad.southernmostLatitude = southernmostLatitude;
    }

    /**
     * The westernmost longitude used in quad trees.
     * @return westernmostLongitude
     */

    public static double getWesternmostLongitude() {
        return westernmostLongitude;
    }

    /**
     * Change the westernmost longitude used in quad trees.
     * @param westernmostLongitude to set
     */

    public static void setWesternmostLongitude(double westernmostLongitude) {
        TaskQuad.westernmostLongitude = westernmostLongitude;
    }

    /**
     * The easternmost longitude used in quad trees.
     * @return easternmost longitude
     */

    public static double getEasternmostLongitude() {
        return easternmostLongitude;
    }

    /**
     * Change the easternmost longitude used in quad trees.
     * @param easternmostLongitude to set
     */

    public static void setEasternmostLongitude(double easternmostLongitude) {
        TaskQuad.easternmostLongitude = easternmostLongitude;
    }

    /**
     * A set of infos on tasks located within a circle centered at the given latitude and longitude, and with given radius.
     * @param latitude of the circle's center
     * @param longitude of the circle's center
     * @param radius to the circle
     * @return set of tasks
     */

    Set<TaskInfo> getNearbyTasks(double latitude, double longitude, double radius) {
        Set<TaskInfo> taskInfoSet = new HashSet<>();
        for (Task t : findNear(longitude, latitude, radius))
            taskInfoSet.add(t.getTaskInfo());
        return taskInfoSet;
    }


}
