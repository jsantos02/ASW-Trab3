package web.s4v.client.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import web.s4v.client.S4vServiceAsync;
import web.s4v.shared.ActivityInfo;

import java.util.Set;

/**
 * This class shows activities and tasks
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */
public class ViewActivitiesAndTasks extends Composite {
    private VerticalPanel mainPanel;
    private Label activitiesLabel;
    private Button showActivity;
    private S4vServiceAsync s4vService;


    public ViewActivitiesAndTasks(S4vServiceAsync s4vService) {
        this.s4vService = s4vService;
        initWidget(createMainPanel());
    }
    /**
     * @return mainPanel
     */
    private VerticalPanel createMainPanel() {
        mainPanel = new VerticalPanel();
        activitiesLabel = new Label("Activities and Tasks:");
        mainPanel.add(activitiesLabel);

        showActivity = new Button("Show");
        mainPanel.add(showActivity);

        showActivity.addClickHandler(event -> {
            s4vService.getActivities(new AsyncCallback<Set<ActivityInfo>>() {
                @Override
                public void onSuccess(Set<ActivityInfo> result) {
                    // Handle success
                    // Display each activity in the panel
                    for (ActivityInfo activity : result) {
                        Label activityLabel = new Label("Activity ID: " + activity.getId() +
                                ", Name: " + activity.getName() +
                                ", Start Date: " + activity.getStart() +
                                ", End Date: " + activity.getEnd()
                        );
                        mainPanel.add(activityLabel);
                    }
                }

                @Override
                public void onFailure(Throwable caught) {
                    // Handle failure
                    // Display an error message
                    Window.alert("Failed to get activities: " + caught.getMessage());
                }
            });
        });
            return mainPanel;
    }
}
