package fr.univ.fullswofng;

import fr.univ.fullswofng.controleur.ControllerImpl;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import fr.univ.fullswofng.views.ViewManager;
import fr.univ.fullswofng.views.ViewManagerImpl;

import java.io.IOException;

public class HelloApplication extends Application {

    // Ce service est utilisé pour le bouton aide
    public static HostServices hostServices;

    @Override
    public void start(Stage stage) throws IOException {
        ViewManager viewManager = new ViewManagerImpl(stage);

        ControllerImpl controller = new ControllerImpl(viewManager,
                (ctrlr, vM) -> {
                    // Propagation du contrôleur pour toutes les vues
                    vM.getInteractiveViews().forEach(interactiveView ->
                            interactiveView.setController(ctrlr));
                    // Inscription des ecouteurs d'ordres
                    vM.getOrderListeners().forEach(ecouteurOrdre ->
                            ecouteurOrdre.setSubscription(ctrlr));
                });
        hostServices=getHostServices();
        controller.run();
    }

    public static void main(String[] args) {
        launch();
    }
}