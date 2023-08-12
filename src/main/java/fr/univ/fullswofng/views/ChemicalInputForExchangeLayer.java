package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.order.LauncherOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChemicalInputForExchangeLayer extends AbstractRefactoringInteractiveViews{


    public RadioButton noInputChemical;
    public RadioButton inputChemical;
    @FXML
    private TextField taux_diffusion;
    @FXML
    private TextField profondeur_couche;
    @FXML
    private TextField instant_final;


    public static ChemicalInputForExchangeLayer createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(ChemicalInputForExchangeLayer.class.getResource("/chemicalInputForExchangeLayer.fxml"));
        try {
            fxmlLoader.load();
            ChemicalInputForExchangeLayer view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Petit souci de chargement du fichier chemicalInputForExchangeLayer.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case ERREUR_TAUX_DIFFUSION: {
                handleKeyPress();
                break;
            }
            case ERREUR_PROFONDEUR: {
                handleKeyPress();
                break;
            }
            case ERREUR_INSTANT_FINALE_APPORT: {
                handleKeyPress();
                break;
            }
        }
    }
    @FXML
    private void desactivation_Activation_composant() {
        if (noInputChemical.isSelected()) {
            instant_final.setDisable(true);
            taux_diffusion.setDisable(true);
            profondeur_couche.setDisable(true);

        }
        else if (inputChemical.isSelected()) {
            instant_final.setDisable(false);
            taux_diffusion.setDisable(false);
            profondeur_couche.setDisable(false);
        }
    }

    @FXML
    private void handleKeyPress() {
        validateTextField(instant_final);
        validateTextField(profondeur_couche);
        validateTextField(taux_diffusion);


    }
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

    @Override
    public void setSubscription(LauncherOrder e) {
        e.subscription(this, OrderType.SHOW_CHEMICAL_INPUT_FOR_EXCHANGE_LAYER,
                OrderType.ERREUR_TAUX_DIFFUSION,
                OrderType.ERREUR_PROFONDEUR,
                OrderType.ERREUR_INSTANT_FINALE_APPORT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle = new ToggleGroup();
        noInputChemical.setToggleGroup(toggle);
        inputChemical.setToggleGroup(toggle);
    }
}
