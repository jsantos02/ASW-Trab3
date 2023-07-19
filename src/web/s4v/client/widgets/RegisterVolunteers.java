package web.s4v.client.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import web.s4v.client.S4vServiceAsync;
import web.s4v.shared.*;

import static web.s4v.shared.IDGenerator.generateID;

/**
 * The purpose of this class is to Register Volunteers
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */
public class RegisterVolunteers extends Composite {
    private VerticalPanel mainPanel;
    private TextBox nameTextBox;
    private TextBox emailTextBox;
    private TextBox phoneTextBox;
    private TextBox otherTextBox;
    private TextBox passwordTextBox;
    private Button registerButton;
    private S4vServiceAsync s4vService;
    public static VolunteerInfo vInfo;
    public static AuthenticatedVolunteerInfo authvInfo;


    public RegisterVolunteers(S4vServiceAsync s4vService) {
        this.s4vService = s4vService;
        initWidget(createMainPanel());
    }

    /**
     * @return the created main panel
     */
    private VerticalPanel createMainPanel() {
        mainPanel = new VerticalPanel();

        Label nameLabel = new Label("Name:");
        mainPanel.add(nameLabel);

        nameTextBox = new TextBox();
        nameTextBox.getElement().setPropertyString("placeholder", "Name");
        mainPanel.add(nameTextBox);

        Label contactLabel = new Label("Contacts:");
        mainPanel.add(contactLabel);

        emailTextBox = new TextBox();
        emailTextBox.getElement().setPropertyString("placeholder", "Email");
        mainPanel.add(emailTextBox);

        phoneTextBox = new TextBox();
        phoneTextBox.getElement().setPropertyString("placeholder", "Phone");
        mainPanel.add(phoneTextBox);

        otherTextBox = new TextBox();
        otherTextBox.getElement().setPropertyString("placeholder", "Other");
        mainPanel.add(otherTextBox);
        Label passLabel = new Label("Password:");
        mainPanel.add(passLabel);
        passwordTextBox = new TextBox();
        passwordTextBox.getElement().setPropertyString("placeholder", "Password");
        mainPanel.add(passwordTextBox);

        registerButton = new Button("Register");
        mainPanel.add(registerButton);

        registerButton.addClickHandler(event -> {
            String name  = nameTextBox.getText();
            String email = emailTextBox.getText();
            String phone = phoneTextBox.getText();
            String other = otherTextBox.getText();
            String password = passwordTextBox.getText();

            vInfo = new VolunteerInfo(name);

            if(email != null) {
                Contact emailContact = new Contact(email, ContactType.EMAIL);
                vInfo.addContact(emailContact);
            }
            if(phone != null){
                Contact phoneContact = new Contact(phone, ContactType.PHONE);
                vInfo.addContact(phoneContact);
            }else if(other != null){
                Contact otherContact = new Contact(other, ContactType.OTHER);
                vInfo.addContact(otherContact);
            }

            authvInfo = new AuthenticatedVolunteerInfo(vInfo, password);
            authvInfo.setId(generateID());
            s4vService.registerVolunteer(vInfo, new AsyncCallback<AuthenticatedVolunteerInfo>() {
                @Override
                public void onSuccess(AuthenticatedVolunteerInfo result) {
                    Window.alert("Volunteer registered successfully!" + authvInfo.getId());
                }

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Failed to register volunteer: " + caught.getMessage());
                }
            });
        });

        return mainPanel;
    }
}
