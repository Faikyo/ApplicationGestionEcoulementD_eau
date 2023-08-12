package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.order.LauncherOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OutputFileName extends AbstractRefactoringInteractiveViews{

    public static OutputFileName createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(OutputFileName.class.getResource("/outputFileName.fxml"));
        try {
            fxmlLoader.load();
            OutputFileName view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Petit souci de chargement du fichier outputFileName.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {

    }

    @Override
    public void setSubscription(LauncherOrder e) {
        e.subscription(this, OrderType.SHOW_OUTPUT_FILE_NAME);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }
}
