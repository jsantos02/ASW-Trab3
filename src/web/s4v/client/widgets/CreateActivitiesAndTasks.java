package web.s4v.client.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.DateBox;
import web.s4v.client.S4vServiceAsync;
import web.s4v.shared.*;

import java.util.Date;

import static web.s4v.client.widgets.RegisterVolunteers.authvInfo;
import static web.s4v.client.widgets.RegisterVolunteers.vInfo;

/**
 * This class represents a panel for creating activities and tasks.
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */

public class CreateActivitiesAndTasks extends Composite {
    private VerticalPanel mainPanel;
    private TextBox activityTextBox;
    private TextBox nameTextBox;
    private TextBox activityIdTextBox;
    private TextBox taskNameTextBox;
    private TextBox latitudeTextBox;
    private TextBox longitudeTextBox;
    private Button createActivityButton;
    private Button createTaskButton;
    private S4vServiceAsync s4vService;
    private DateBox activityStartDateBox;
    private DateBox activityEndDateBox;
    private DateBox taskStartDateBox;
    private DateBox taskEndDateBox;
    public static ActivityInfo activityInfo;
    public static TaskInfo taskInfo;


    public CreateActivitiesAndTasks(S4vServiceAsync s4vService) {
        this.s4vService = s4vService;
        initWidget(createMainPanel());
    }

    /**
     * @return the created main panel
     */
    private VerticalPanel createMainPanel() {
        mainPanel = new VerticalPanel();

        Label activityLabel = new Label("ACTIVITY:");
        mainPanel.add(activityLabel);

        nameTextBox = new TextBox();
        nameTextBox.getElement().setPropertyString("placeholder", "Volunteer Creating");
        mainPanel.add(nameTextBox);

        activityTextBox = new TextBox();
        activityTextBox.getElement().setPropertyString("placeholder", "Activity");
        mainPanel.add(activityTextBox);

        activityStartDateBox = new DateBox();
        activityStartDateBox.getElement().setPropertyString("placeholder", "Start Date");
        mainPanel.add(activityStartDateBox);

        activityEndDateBox = new DateBox();
        activityEndDateBox.getElement().setPropertyString("placeholder", "End Date");
        mainPanel.add(activityEndDateBox);

        createActivityButton = new Button("Create Activity");
        mainPanel.add(createActivityButton);

        Label taskLabel = new Label("TASK:");
        mainPanel.add(taskLabel);

        activityIdTextBox = new TextBox();
        activityIdTextBox.getElement().setPropertyString("placeholder", "Activity Name");
        mainPanel.add(activityIdTextBox);

        taskNameTextBox = new TextBox();
        taskNameTextBox.getElement().setPropertyString("placeholder", "Name");
        mainPanel.add(taskNameTextBox);

        latitudeTextBox = new TextBox();
        latitudeTextBox.getElement().setPropertyString("placeholder", "Latitude");
        mainPanel.add(latitudeTextBox);

        longitudeTextBox = new TextBox();
        longitudeTextBox.getElement().setPropertyString("placeholder", "Longitude");
        mainPanel.add(longitudeTextBox);

        taskStartDateBox = new DateBox();
        taskStartDateBox.getElement().setPropertyString("placeholder", "Start Date");
        mainPanel.add(taskStartDateBox);

        taskEndDateBox = new DateBox();
        taskEndDateBox.getElement().setPropertyString("placeholder", "End Date");
        mainPanel.add(taskEndDateBox);

        createTaskButton = new Button("Create Task");
        mainPanel.add(createTaskButton);

        createActivityButton.addClickHandler(event -> {
            String activityText = activityTextBox.getText();
            Date startDate = activityStartDateBox.getValue();
            Date endDate = activityEndDateBox.getValue();
     try {
                activityInfo = new ActivityInfo(authvInfo.getId(nameTextBox.getName()), activityText, startDate, endDate);
                s4vService.createActivity(authvInfo.getId(nameTextBox.getName()), activityInfo, new AsyncCallback<ActivityInfo>() {
                    @Override
                    public void onSuccess(ActivityInfo result) {
                        // Handle success
                        // Display a success message or perform any other necessary actions
                        Window.alert("Activity created successfully!");
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        // Handle failure
                        // Display an error message or perform any other necessary actions
                        Window.alert("Failed to create activity: " + caught.getMessage());
                    }
                });
            } catch (SpotsForVolunteeringException e) {
                // Handle exception
                // Display an error message or perform any other necessary actions
                Window.alert("Failed to create activity: " + e.getMessage());
            }
        });

        createTaskButton.addClickHandler(event -> {
             String activityIdText = activityInfo.getId();
            String taskNameText = taskNameTextBox.getText();
            double latitude = Double.parseDouble(latitudeTextBox.getText());
            double longitude = Double.parseDouble(longitudeTextBox.getText());
            Date startDate = taskStartDateBox.getValue();
            Date endDate = taskEndDateBox.getValue();
 try {
                taskInfo = new TaskInfo(activityIdText, taskNameText, latitude, longitude, startDate, endDate);

                s4vService.createTask(authvInfo, taskInfo, new AsyncCallback<TaskInfo>() {
                    @Override
                    public void onSuccess(TaskInfo result) {
                        Window.alert("Task created successfully!");
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        // Handle failure
                        // Display an error message or perform any other necessary actions
                        Window.alert("Failed to create task: " + caught.getMessage());
                    }
                });
            } catch (SpotsForVolunteeringException e) {
                throw new RuntimeException(e);
            }
        });

        return mainPanel;
    }
}
