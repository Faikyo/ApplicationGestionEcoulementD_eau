package fr.univ.fullswofng.views.upperNavBar;

import javafx.application.HostServices;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class AboutHtml extends Stage {
    /**
     * The resource bundle
     */
    private final ResourceBundle messages;
    private final HostServices hostServices;

    /**
     * @details Constructs a window displaying an about.
     */
    public AboutHtml(ResourceBundle rb, HostServices hostServices) {
        super();
        messages = rb != null ? rb : ResourceBundle.getBundle("language.TransferParameters_default", Locale.getDefault());

        this.hostServices = hostServices;

        setTitle(messages.getString("about.title"));
        initModality(Modality.APPLICATION_MODAL);

        VBox aboutPane = createAboutPane();
        Scene scene = new Scene(aboutPane, 450, 250);
        setScene(scene);
        setResizable(false);
        centerOnScreen();
    }

    /**
     * Returns a VBox to display the content of an about.
     *
     * @return a VBox to display the content of an about
     */
    private VBox createAboutPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 35));
        vbox.setAlignment(Pos.CENTER_LEFT);

        if (messages != null) {
            Text label1 = new Text(messages.getString("about.text").replace("<br/>", "\n"));
            label1.setWrappingWidth(400);
            vbox.getChildren().add(label1);

            Text website = new Text(messages.getString("website"));
            website.setFill(Color.BLUE);
            website.setUnderline(true);
            website.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hostServices.showDocument(messages.getString("website"));
                }
            });

            vbox.getChildren().add(website);
        } else {
            Text errorText = new Text("ResourceBundle is missing or null!");
            vbox.getChildren().add(errorText);
        }

        return vbox;
    }
}
