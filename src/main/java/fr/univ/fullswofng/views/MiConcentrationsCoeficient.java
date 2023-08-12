package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.order.LauncherOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MiConcentrationsCoeficient extends AbstractRefactoringInteractiveViews  {
    @FXML
    private TextField Coeff_Concentration_Mi;

    public static MiConcentrationsCoeficient createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(MiConcentrationsCoeficient.class.getResource("/miConcentrationCoeficient.fxml"));
        try {
            fxmlLoader.load();
            MiConcentrationsCoeficient view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Petit souci de chargement du fichier miConcentrationCoeficient.fxml");
        }
    }
    @FXML
    private void handleKeyPress() {
        validateTextField(Coeff_Concentration_Mi);

    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case ERREUR_COEF_CONCENTRATION_MI: {
                handleKeyPress();
                break;
            }
        }
    }

    @Override
    public void setSubscription(LauncherOrder e) {
        e.subscription(this, OrderType.SHOW_MI_CONCENTRATION_COEFICIENT,
                OrderType.ERREUR_COEF_CONCENTRATION_MI);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }
/*
*Cette méthode permet de donner une alerte et interdit lorsque l'utilisateur entre un format
* différent des nombres ou inférieur à 0.
*
 */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void validateTextField(TextField textField) {
        String input = textField.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        try {
            int number = Integer.parseInt(input);
            if (number <= 0) {
                showAlert("Erreur", "Le nombre doit être supérieur à zéro.");
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez saisir un entier.");
        }
    }



}
