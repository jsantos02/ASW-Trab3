package web.s4v.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import web.s4v.client.S4vServiceAsync;

/**
 *    This class is used to read and send messages
 * @author José Santos (up202007059)
 * @author Miguel Gomes (up201905102)
 * @since May 2023
 */
public class ReadAndSendMessages extends Composite {
    private VerticalPanel mainPanel;
    private Label messageLabel;
    private TextArea messageTextArea;
    private S4vServiceAsync s4vService;


    public ReadAndSendMessages(S4vServiceAsync s4vService) {
        this.s4vService = s4vService;
        initWidget(createMainPanel());
    }
    /**
     * @return mainPanel
     */
    private VerticalPanel createMainPanel() {
        mainPanel = new VerticalPanel();

        messageLabel = new Label("Messages:");
        mainPanel.add(messageLabel);

        messageTextArea = new TextArea();
        mainPanel.add(messageTextArea);

        // Add your additional UI components and logic for ReadSendMessagesPanel

        return mainPanel;
    }
}
