package web.s4v.client;

import web.s4v.shared.*;
import web.s4v.main.*;

import java.util.List;
import java.util.Set;


/**
 * Manager Interface
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */

public interface BaseService {

    AuthenticatedVolunteerInfo registerVolunteer(VolunteerInfo volunteerInfo) throws SpotsForVolunteeringException;

    VolunteerInfo getVolunteerInfo(String id) throws SpotsForVolunteeringException;

    void changeVolunteer(AuthenticatedVolunteerInfo volunteerInfo) throws SpotsForVolunteeringException;

    void deregisterVolunteer(AuthenticatedVolunteerInfo volunteerInfo) throws SpotsForVolunteeringException;

    ActivityInfo createActivity(String authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException;

    ActivityInfo getActivityInfo(String id) throws SpotsForVolunteeringException;

    void changeActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException;

    TaskInfo createTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException;

    TaskInfo getTaskInfo(String activityId, String taskId) throws SpotsForVolunteeringException;

    void removeTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException;

    Set<ActivityInfo> getActivities();

    Set<ActivityInfo> getOngoingActivities();

    Set<ActivityInfo> getUpcomingActivities();


    void broadcastToActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo, Message message) throws SpotsForVolunteeringException;

    void followActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException;

    void unfollowActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException;

    Set<TaskInfo> getAllTasks(ActivityInfo activityInfo) throws SpotsForVolunteeringException;

    void broadcastToTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, Message message) throws SpotsForVolunteeringException;

    void followTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException;

    void unfollowTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException;

    List<Message> getMessages(AuthenticatedVolunteerInfo authenticatedVolunteerInfo) throws SpotsForVolunteeringException;

    Set<TaskInfo> getNearbyTasks(double latitude, double longitude, double radius);

    void enrollInTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException;

    Set<VolunteerInfo> getEnrolledVolunteers(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException;
}
