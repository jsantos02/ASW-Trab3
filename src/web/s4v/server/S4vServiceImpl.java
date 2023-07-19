package web.s4v.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import web.s4v.client.S4vService;
import web.s4v.shared.*;
import web.s4v.main.*;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Set;

/**
 * S4v Service Implementation
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */
public class S4vServiceImpl extends RemoteServiceServlet implements S4vService {
    Manager manager;
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            manager = Manager.getInstance();
        } catch (SpotsForVolunteeringException e) {
            throw new ServletException(e);
        }
      // ...
    }

    @Override
    public AuthenticatedVolunteerInfo registerVolunteer(VolunteerInfo volunteerInfo) throws SpotsForVolunteeringException {
        return manager.registerVolunteer(volunteerInfo);
    }

    @Override
    public VolunteerInfo getVolunteerInfo(String id) throws SpotsForVolunteeringException {
        return manager.getVolunteerInfo(id);
    }

    @Override
    public void changeVolunteer(AuthenticatedVolunteerInfo volunteerInfo) throws SpotsForVolunteeringException {
        manager.changeVolunteer(volunteerInfo);
    }

    @Override
    public void deregisterVolunteer(AuthenticatedVolunteerInfo volunteerInfo) throws SpotsForVolunteeringException {
        manager.deregisterVolunteer(volunteerInfo);
    }

    @Override
    public ActivityInfo createActivity(String authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        return manager.createActivity(authenticatedVolunteerInfo, activityInfo);
    }

    @Override
    public ActivityInfo getActivityInfo(String id) throws SpotsForVolunteeringException {
        return manager.getActivityInfo(id);
    }

    @Override
    public void changeActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        manager.changeActivity(authenticatedVolunteerInfo,activityInfo);
    }

    @Override
    public TaskInfo createTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        return manager.createTask(authenticatedVolunteerInfo, taskInfo);
    }

    @Override
    public TaskInfo getTaskInfo(String activityId, String taskId) throws SpotsForVolunteeringException {
        return manager.getTaskInfo(activityId, taskId);
    }

    @Override
    public void removeTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        manager.removeTask(authenticatedVolunteerInfo, taskInfo);
    }

    @Override
    public Set<ActivityInfo> getActivities() {
        return manager.getActivities();
    }

    @Override
    public Set<ActivityInfo> getOngoingActivities() {
        return manager.getOngoingActivities();
    }

    @Override
    public Set<ActivityInfo> getUpcomingActivities() {
        return manager.getUpcomingActivities();
    }

    @Override
    public void broadcastToActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo, Message message) throws SpotsForVolunteeringException {
        manager.broadcastToActivity(authenticatedVolunteerInfo, activityInfo, message);
    }

    @Override
    public void followActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        manager.followActivity(authenticatedVolunteerInfo, activityInfo);
    }

    @Override
    public void unfollowActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        manager.unfollowActivity(authenticatedVolunteerInfo, activityInfo);
    }

    @Override
    public Set<TaskInfo> getAllTasks(ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        return manager.getAllTasks(activityInfo);
    }

    @Override
    public void broadcastToTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, Message message) throws SpotsForVolunteeringException {
        manager.broadcastToTask(authenticatedVolunteerInfo, taskInfo, message);
    }

    @Override
    public void followTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        manager.followTask(authenticatedVolunteerInfo, taskInfo);
    }

    @Override
    public void unfollowTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        manager.unfollowTask(authenticatedVolunteerInfo, taskInfo);
    }

    @Override
    public List<Message> getMessages(AuthenticatedVolunteerInfo authenticatedVolunteerInfo) throws SpotsForVolunteeringException {
        return manager.getMessages(authenticatedVolunteerInfo);
    }

    @Override
    public Set<TaskInfo> getNearbyTasks(double latitude, double longitude, double radius) {
        return manager.getNearbyTasks(latitude, longitude, radius);
    }

    @Override
    public void enrollInTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        manager.enrollInTask(authenticatedVolunteerInfo, taskInfo);
    }

    @Override
    public Set<VolunteerInfo> getEnrolledVolunteers(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        return manager.getEnrolledVolunteers(authenticatedVolunteerInfo, taskInfo);
    }
}