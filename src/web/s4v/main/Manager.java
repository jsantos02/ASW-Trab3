package web.s4v.main;

import web.s4v.shared.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An instance of this class is responsible for managing volunteers, activities and tasks.
 * The methods of this class are those needed by web clients, thus it follows the Facade design pattern.
 * It also follows the Singleton design pattern to provide a single instance of this class to the application.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since April 2023
 */

public class Manager implements web.s4v.client.BaseService {
 //   static Manager instance;
    public static Manager manager;
    Set<Volunteer> volunteers = new HashSet<>();
    Set<Activity> activities = new HashSet<>();
    Set<Task> tasks = new HashSet<>();

    static VolunteerPool volunteerPool;

    static ActivityPool activityPool;

    static TaskQuad taskQuad;


    private Manager() {
    }



    /**
     * Method to determine if a given volunteer is registered or not
     * @param volunteerInfo
     * @return if a volunteer is registered or not
     */
    boolean isRegistered(VolunteerInfo volunteerInfo) {
        for(Volunteer volunteer : volunteers)
            if(volunteer.getId().equals(volunteerInfo.getId()))
                return true;
        return false;
    }

    /**
     * Reset manager to its initial state. Use it only for unit testing.
     */
    void reset() {
        manager = null;
        volunteers.clear();
        activities.clear();
        tasks.clear();
        volunteerPool = null;
        activityPool = null;
        taskQuad = null;
    }

    /**
     *
     * @return
     * @throws SpotsForVolunteeringException
     */
    public static Manager getInstance() throws SpotsForVolunteeringException{
        if(manager == null){
            manager = new Manager();
            return manager;
        }
        return manager;
    }

    /**
     * Register a new volunteer with data provided by the argument
     * @param volunteerInfo  data to initialize volunteer
     * @return authenticated volunteer info
     * @throws SpotsForVolunteeringException  if the parameters is null, or volunteer is not registered, or authentication is invalid.
     */

    @Override
    public AuthenticatedVolunteerInfo registerVolunteer(VolunteerInfo volunteerInfo) throws SpotsForVolunteeringException {
        if(volunteerInfo == null) throw new SpotsForVolunteeringException("parameters null");
        Volunteer volunteer = new Volunteer(volunteerInfo);
        volunteers.add(volunteer);
        if(!volunteer.authenticate(volunteer.getAuthenticatedVolunteerInfo().getPrivateKey())) throw new SpotsForVolunteeringException("authentication failed");
        return volunteer.getAuthenticatedVolunteerInfo();
    }

    /**
     * Retrieve info on volunteer with given ID
     * @param id of volunteer
     * @return info on volunteer
     * @throws SpotsForVolunteeringException if no currently registered volunteer has the given ID
     */

    @Override
    public VolunteerInfo getVolunteerInfo(String id) throws SpotsForVolunteeringException {
        for(Volunteer volunteer : volunteers) {
            if(id.equals(volunteer.getId()))
                return volunteer.getVolunteerInfo();
        }
        throw new SpotsForVolunteeringException("no volunteer with that ID");
    }

    /**
     * Change information on a volunteer.
     * The information on the volunteer with the id in the parameter is replaced with the given parameter.
     * Notice that the private key must be equals to the initially generated and thus cannot be changed.
     * @param volunteerInfo data to replace existing data
     * @throws SpotsForVolunteeringException if any of the parameters is null, or volunteer is not registered, or authentication is invalid.
     */
    @Override
    public void changeVolunteer(AuthenticatedVolunteerInfo volunteerInfo) throws SpotsForVolunteeringException {
        if(volunteerInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(volunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        for(Volunteer volunteer : volunteers) {
            if(volunteer.getId().equals(volunteerInfo.getId())) {
                if(volunteer.authenticate(volunteerInfo.getPrivateKey()))
                    volunteer.setVolunteerInfo(volunteerInfo);
                else throw new SpotsForVolunteeringException("authentication failed");
            }
        }
    }

    /**
     * Deregister a previously registered volunteer. All information about this volunteer will be lost.
     * This method return quietly even if the parameter is null or no volunteer is found with given id.
     * @param volunteerInfo to remove
     * @throws SpotsForVolunteeringException if the parameters is null, or volunteer is not registered, or authentication is invalid.
     */

    @Override
    public void deregisterVolunteer(AuthenticatedVolunteerInfo volunteerInfo) throws SpotsForVolunteeringException {
        if(volunteerInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(volunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        for(Volunteer volunteer : volunteers) {
            if(volunteer.getId().equals(volunteer.getId())) {
                if(volunteer.authenticate(volunteerInfo.getPrivateKey()))
                    volunteers.remove(volunteer);
                else throw new SpotsForVolunteeringException("authentication failed");
            }
        }
    }

    /**
     * Create a new activity assigned to given authenticated volunteer
     * @param authenticatedVolunteerInfo of activity owner
     * @param activityInfo to create activity
     * @return activityInfo with an assigned ID
     * @throws SpotsForVolunteeringException if any of the parameters is null, or volunteer is not registered, or authentication is invalid, or the authenticated volunteer is different fom the activity owner.
     */

    @Override
    public ActivityInfo createActivity(String authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || activityInfo == null) throw new SpotsForVolunteeringException("null");
        if(!(activityInfo.getOwnerId().equals(authenticatedVolunteerInfo))) throw new SpotsForVolunteeringException("Owner ID and VolunteerID don't match");
        // if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo);
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
       // if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
       //     throw new SpotsForVolunteeringException("Authentication failed");
        activityInfo.setOwnerId(authenticatedVolunteerInfo);
        Activity activity = new Activity(activityInfo);
        activities.add(activity);
        return activity.getActivityInfo();
    }

    /**
     * Retrieve info on activity with given ID
     * @param id of activity
     * @return info on activity
     * @throws SpotsForVolunteeringException if no activity has the given ID
     */

    @Override
    public ActivityInfo getActivityInfo(String id) throws SpotsForVolunteeringException {
        for(Activity activity : activities)
            if(activity.getId().equals(id))
                return activity.getActivityInfo();
        throw new SpotsForVolunteeringException("no activity found");
    }

    /**
     * Changes activity of given authenticated user
     * @param authenticatedVolunteerInfo that created this activity
     * @param activityInfo to change
     * @throws SpotsForVolunteeringException if any of the parameters is null, or volunteer is not registered, or authentication is invalid, or the authenticated volunteer is different fom the activity owner.
     */

    @Override
    public void changeActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || activityInfo == null) throw new SpotsForVolunteeringException("null");
        if(!(activityInfo.getOwnerId().equals(authenticatedVolunteerInfo.getId()))) throw new SpotsForVolunteeringException("Owner ID and VolunteerID don't match");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");
        activityPool.changeActivity(activityInfo);
    }

    /**
     * Method that creates a class
     * @param authenticatedVolunteerInfo
     * @param taskInfo
     * @return the information of the created task
     * @throws SpotsForVolunteeringException any of the parameters is null, or if they entities they refer are not registered.
     */

    @Override
    public TaskInfo createTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || taskInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");
        Task task = new Task(taskInfo);
        tasks.add(task);
        return task.getTaskInfo();
    }

    /**
     * Retrieve info on task from activity given their IDs
     * @param activityId of task
     * @param taskId of task
     * @return task info
     * @throws SpotsForVolunteeringException any of the parameters is null, or if they entities they refer are not registered.
     */

    @Override
    public TaskInfo getTaskInfo(String activityId, String taskId) throws SpotsForVolunteeringException {
        if(activityId == null || taskId == null) throw new SpotsForVolunteeringException("null");
        for(Activity activity : activities) {
            if(activityId.equals(activity.getId())) {
                return activity.getTask(taskId).getTaskInfo();
            }
        }
        throw new SpotsForVolunteeringException("Task or Activity is non existent");
    }

    /**
     * Removes task from its activity. Task is also removed from quad tree.
     * @param authenticatedVolunteerInfo task creator
     * @param taskInfo to remove
     * @throws SpotsForVolunteeringException if any of the parameters is null, or volunteer is not registered, or authentication is invalid, or tasks doesn't have an ID, or an activity ID.
     */

    @Override
    public void removeTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || taskInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");
        Activity activity = activityPool.getActivity(taskInfo.getActivityId());
        activity.removeTask(taskInfo.getId());
    }

    /**
     * Information on all registered activities
     * @return list all activities
     */

    @Override
    public Set<ActivityInfo> getActivities() {
        return activityPool.getAllActivityInfos();
    }

    /**
     * Information on activities that have started but haven't ended yed
     * @return set of current activities
     */

    @Override
    public Set<ActivityInfo> getOngoingActivities() {
        return activityPool.getOngoingActivities();
    }

    /**
     * Information on activities that haven't started yed
     * @return set of upcoming activities
     */

    @Override
    public Set<ActivityInfo> getUpcomingActivities() {
        return activityPool.getUpcomingActivities();
    }

    /**
     * Broadcast message to activity,
     * @param authenticatedVolunteerInfo message sender
     * @param activityInfo of activity where message is broadcast
     * @param message to broadcast
     * @throws SpotsForVolunteeringException if any of the parameters is null, or volunteer is not registered, or authentication is invalid, or volunteer is not the owner of the activity, or message is not from volunteer.
     */


    @Override
    public void broadcastToActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo, Message message) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || activityInfo == null || message == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        MessageBroadcaster messageBroadcaster = new MessageBroadcaster();
        messageBroadcaster.broadcast(message);
    }

    /**
     * Volunteer starts receiving messages sent to given activity
     * @param authenticatedVolunteerInfo who will receive messages
     * @param activityInfo of activity broadcasting messages
     * @throws SpotsForVolunteeringException if any of the arguments is null, volunteer, task or activity are unknown, volunteer authentication is invalid.
     */

    @Override
    public void followActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || activityInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        MessageBroadcaster messageBroadcaster = new MessageBroadcaster();
        messageBroadcaster.addObserver(volunteer);
    }

    /**
     * Volunteer stops receiving messages sent to given task
     * @param authenticatedVolunteerInfo who will receive messages
     * @param activityInfo of activity broadcasting messages
     * @throws SpotsForVolunteeringException if any of the arguments is null, volunteer, task or activity are unknown, volunteer authentication is invalid.
     */

    @Override
    public void unfollowActivity(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || activityInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        MessageBroadcaster messageBroadcaster = new MessageBroadcaster();
        messageBroadcaster.removeObserver(volunteer);
    }

    /**
     * Get tasks from given activity
     * @param activityInfo of interest
     * @return set of tasks infos
     * @throws SpotsForVolunteeringException if the arguments is null, or activity is unknown.
     */

    @Override
    public Set<TaskInfo> getAllTasks(ActivityInfo activityInfo) throws SpotsForVolunteeringException {
        if (activityInfo == null) throw new SpotsForVolunteeringException("null");
        Activity activity = activityPool.getActivity(activityInfo.getId());
        return activity.getTaskInfos();
    }

    /**
     * Broadcast message from user in task. Authenticated user must be the message sender and be enrolled in task.
     * @param authenticatedVolunteerInfo sending the message
     * @param taskInfo of task where to broadcast
     * @param message to broadcast
     * @throws SpotsForVolunteeringException if any of the arguments is null, the authentication failed, the volunteer is not the sender, or is not enrolled in task.
     */

    @Override
    public void broadcastToTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo, Message message) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || taskInfo == null || message == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        MessageBroadcaster messageBroadcaster = new MessageBroadcaster();
        messageBroadcaster.broadcast(message);
    }

    /**
     * Volunteer starts receiving messages sent to given task
     * @param authenticatedVolunteerInfo who will receive messages
     * @param taskInfo of task broadcasting messages
     * @throws SpotsForVolunteeringException if any of the arguments is null, volunteer, task or activity are unknown, volunteer authentication is invalid.
     */

    @Override
    public void followTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || taskInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        MessageBroadcaster messageBroadcaster = new MessageBroadcaster();
        messageBroadcaster.addObserver(volunteer);
    }

    /**
     * Volunteer stops receiving messages sent to given task
     * @param authenticatedVolunteerInfo who will stop receiving messages
     * @param taskInfo of task broadcasting messages
     * @throws SpotsForVolunteeringException if any of the arguments is null, volunteer, task or activity are unknown, or volunteer authentication is invalid.
     */

    @Override
    public void unfollowTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || taskInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        MessageBroadcaster messageBroadcaster = new MessageBroadcaster();
        messageBroadcaster.removeObserver(volunteer);
    }

    /**
     * Get a list with the latest messages for the given authenticated volunteer. The returned messages are cleared afterwards.
     * @param authenticatedVolunteerInfo receiving messages
     * @return list of messages
     * @throws SpotsForVolunteeringException if any of the arguments is null, the authentication failed, the volunteer is not the sender, or is not enrolled in task.
     */

    @Override
    public List<Message> getMessages(AuthenticatedVolunteerInfo authenticatedVolunteerInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        return volunteer.getMessages();
    }

    /**
     * Get tasks from all activities within a circle with center at the given latitude and longitude, and with given radius
     * @param latitude of the circle's center
     * @param longitude of the circle's center
     * @param radius to the circle
     * @return set of tasks
     */

    @Override
    public Set<TaskInfo> getNearbyTasks(double latitude, double longitude, double radius) {
        return taskQuad.getNearbyTasks(latitude,longitude,radius);
    }

    /**
     * Enroll a volunteer in a task
     * @param authenticatedVolunteerInfo volunteer to get enrolled
     * @param taskInfo info of the task to enroll
     * @throws SpotsForVolunteeringException if any of the arguments is null, the authentication failed, the volunteer is not the sender, or is not enrolled in task.
     */

    @Override
    public void enrollInTask(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || taskInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        Task task = new Task(taskInfo);
        task.enroll(volunteer);
    }

    /**
     * Set of volunteer information of enrolled volunteers in a task
     * @param authenticatedVolunteerInfo volunteer enrolled
     * @param taskInfo information of the task
     * @return set of Enrolled Volunteers
     * @throws SpotsForVolunteeringException if any of the arguments is null, the authentication failed, the volunteer is not the sender, or is not enrolled in task.
     */

    @Override
    public Set<VolunteerInfo> getEnrolledVolunteers(AuthenticatedVolunteerInfo authenticatedVolunteerInfo, TaskInfo taskInfo) throws SpotsForVolunteeringException {
        if(authenticatedVolunteerInfo == null || taskInfo == null) throw new SpotsForVolunteeringException("null");
        if(!isRegistered(authenticatedVolunteerInfo)) throw new SpotsForVolunteeringException("not registered");
        VolunteerInfo volunteerInfo = getVolunteerInfo(authenticatedVolunteerInfo.getId());
        Volunteer volunteer = volunteerPool.getVolunteer(volunteerInfo.getId());
        if (!volunteer.authenticate(authenticatedVolunteerInfo.getPrivateKey()))
            throw new SpotsForVolunteeringException("Authentication failed");

        Task task = new Task(taskInfo);
        return task.getEnrolledInfo();
    }
}
