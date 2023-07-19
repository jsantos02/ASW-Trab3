package web.s4v.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import web.s4v.client.widgets.CreateActivitiesAndTasks;
import web.s4v.client.widgets.ReadAndSendMessages;
import web.s4v.client.widgets.RegisterVolunteers;
import web.s4v.client.widgets.ViewActivitiesAndTasks;
import web.s4v.main.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */
public class S4v implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Label label = new Label();

        TabLayoutPanel tabPanel = new TabLayoutPanel(3, Style.Unit.EM);

        S4vServiceAsync s4vService = S4vService.App.getInstance();

        final String SERVER_ERROR = "An error occurred while "
                + "attempting to contact the server. Please check your network " + "connection and try again.";

        final CreateActivitiesAndTasks createActivitiesAndTasks = new CreateActivitiesAndTasks(s4vService);
        final ReadAndSendMessages readAndSendMessages = new ReadAndSendMessages(s4vService);
        final RegisterVolunteers registerVolunteers = new RegisterVolunteers(s4vService);
        final ViewActivitiesAndTasks viewActivitiesAndTasks = new ViewActivitiesAndTasks(s4vService);


        tabPanel.add(registerVolunteers,"Register a Volunteer  ");
        tabPanel.add(createActivitiesAndTasks, "Create Volunteering Activity  ");
        tabPanel.add(readAndSendMessages,"Read Messages");
        tabPanel.add(viewActivitiesAndTasks,"View Activities");

        // Set the first tab to be selected
        tabPanel.selectTab(0);

        // Add it to the root panel.
        RootLayoutPanel.get().add(tabPanel);

        RootPanel.get("slot1").add(label);
    }


    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
