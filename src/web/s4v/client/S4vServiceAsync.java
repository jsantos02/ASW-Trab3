package web.s4v.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import web.s4v.shared.*;
import web.s4v.main.*;

import java.util.List;
import java.util.Set;
/**
 * S4v Service Asynchronicity
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */

public interface S4vServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);



    void getEnrolledVolunteers(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, AsyncCallback<Set<VolunteerInfo>> async);

    void registerVolunteer(VolunteerInfo volunteerInfo, AsyncCallback<AuthenticatedVolunteerInfo> async);

    void getVolunteerInfo(String id, AsyncCallback<VolunteerInfo> async);

    void changeVolunteer(AuthenticatedVolunteerInfo volunteerInfo, AsyncCallback<Void> async);

    void deregisterVolunteer(AuthenticatedVolunteerInfo volunteerInfo, AsyncCallback<Void> async);

    void createActivity(String authenticatedVolunteerInfo, ActivityInfo activityInfo, AsyncCallback<ActivityInfo> async);

    void getActivityInfo(String id, AsyncCallback<ActivityInfo> async);

    void changeActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo, AsyncCallback<Void> async);

    void createTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, AsyncCallback<TaskInfo> async);

    void getTaskInfo(String activityId, String taskId, AsyncCallback<TaskInfo> async);

    void removeTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, AsyncCallback<Void> async);

    void getActivities(AsyncCallback<Set<ActivityInfo>> async);

    void getOngoingActivities(AsyncCallback<Set<ActivityInfo>> async);

    void getUpcomingActivities(AsyncCallback<Set<ActivityInfo>> async);

    void broadcastToActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo, Message message, AsyncCallback<Void> async);

    void followActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo, AsyncCallback<Void> async);

    void unfollowActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo, AsyncCallback<Void> async);

    void getAllTasks(ActivityInfo activityInfo, AsyncCallback<Set<TaskInfo>> async);

    void broadcastToTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, Message message, AsyncCallback<Void> async);

    void followTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, AsyncCallback<Void> async);

    void unfollowTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, AsyncCallback<Void> async);

    void getMessages(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, AsyncCallback<List<Message>> async);

    void getNearbyTasks(double latitude, double longitude, double radius, AsyncCallback<Set<TaskInfo>> async);

    void enrollInTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, AsyncCallback<Void> async);
}
