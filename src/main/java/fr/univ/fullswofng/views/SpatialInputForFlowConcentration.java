package fr.univ.fullswofng.views;

import com.jfoenix.controls.JFXButton;
import fr.univ.fullswofng.controleur.order.LauncherOrder;
import fr.univ.fullswofng.controleur.order.ListenerOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SpatialInputForFlowConcentration extends AbstractRefactoringInteractiveViews {


    public JFXButton apportSpatial;
    public RadioButton noInput;
    public RadioButton identicalAndConstantCoeficient;
    public RadioButton spatialFlowFile;
    @FXML
    private TextField instant_final;
    @FXML
    private TextField instant_initial;
    @FXML
    private TextField coefficient_constant;
   @FXML
   private Button choix_fichier;
    @FXML
    private TextField chemin_fichier;



    public static SpatialInputForFlowConcentration createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(SpatialInputForFlowConcentration.class.getResource("/spatialInputForFlowConcentration.fxml"));
        try {
            fxmlLoader.load();
            SpatialInputForFlowConcentration view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Petit souci de chargement du fichier spatialInputForFlowConcentration.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case ERREUR_INITIAL_APPORT_SPATIAL: {
                handleKeyPress();
                break;
            }
            case ERREUR_FINAL_SPATIAL: {
                handleKeyPress();
                break;
            }
            case ERREUR_COEF_CONSTANT_APPORT: {
                handleKeyPress();
                break;
            }
        }
    }
    @FXML
    private void handleKeyPress() {
        validateTextField(coefficient_constant);
        validateTextField(instant_initial);
        validateTextField(instant_final);

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void choixFichier() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt", "*.text"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        Stage stage = (Stage) choix_fichier.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            choix_fichier.setText(selectedFile.getAbsolutePath());
        }
    }
    @FXML
    private void desactivation_Activation_composant() {
        if (noInput.isSelected()) {
            instant_final.setDisable(true);
            instant_initial.setDisable(true);
            coefficient_constant.setDisable(true);
            choix_fichier.setDisable(true);
            chemin_fichier.setDisable(true);
        }
        else if (identicalAndConstantCoeficient.isSelected()) {
            instant_final.setDisable(false);
            instant_initial.setDisable(false);
            coefficient_constant.setDisable(false);
            choix_fichier.setDisable(true);
            chemin_fichier.setDisable(true);

        }
    }

    @Override
    public void setSubscription(LauncherOrder e) {
        e.subscription(this, OrderType.SHOW_RELAXATION_PARAMETERS,OrderType.ERREUR_FINAL_SPATIAL,OrderType.ERREUR_COEF_CONSTANT_APPORT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle = new ToggleGroup();
        noInput.setToggleGroup(toggle);
        identicalAndConstantCoeficient.setToggleGroup(toggle);
        spatialFlowFile.setToggleGroup(toggle);
    }
    @FXML
    private void activation_Choix_Fichier() {
        if (spatialFlowFile.isSelected()) {
            instant_final.setDisable(false);
            instant_initial.setDisable(false);
            coefficient_constant.setDisable(true);
            choix_fichier.setDisable(false);
            chemin_fichier.setDisable(false);

        }
        choixFichier();
    }
}
