package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.order.LauncherOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RelaxationParameters extends AbstractRefactoringInteractiveViews {

    @FXML
    public RadioButton identicalCoeficientRelaxation, relaxationFile, tsiEqualsRpi, tsiEqualsHByRpi, tsiEqualsChemicalTime;
    @FXML
    private TextField coefficient_RPI;
    @FXML
    private TextField intensite_Pluie;
    @FXML
    private TextField chemin_Fichier;
    @FXML
    private Button choix_Fichier;




    public static RelaxationParameters createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(RelaxationParameters.class.getResource("/relaxationParameters.fxml"));
        try {
            fxmlLoader.load();
            RelaxationParameters view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Petit souci de chargement du fichier relaxationParameters.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case ERREUR_INTENSITE_PLUIE: {
                handleKeyPress();
                break;
            }
            case ERREUR_COEF_RPI: {
                handleKeyPress();
                break;
            }
        }

    }
        @FXML
        private void handleKeyPress() {
            validateTextField(coefficient_RPI);
            validateTextField(intensite_Pluie);

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
        e.subscription(this, OrderType.SHOW_RELAXATION_PARAMETERS,OrderType.ERREUR_INTENSITE_PLUIE,OrderType.ERREUR_COEF_RPI);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle = new ToggleGroup();
        identicalCoeficientRelaxation.setToggleGroup(toggle);
        relaxationFile.setToggleGroup(toggle);

        ToggleGroup togglePrim = new ToggleGroup();
        tsiEqualsRpi.setToggleGroup(togglePrim);
        tsiEqualsHByRpi.setToggleGroup(togglePrim);
        tsiEqualsChemicalTime.setToggleGroup(togglePrim);
    }
    @FXML
    private void desactivation_composant() {

        if (identicalCoeficientRelaxation.isSelected()) {
            coefficient_RPI.setDisable(false);
            chemin_Fichier.setDisable(true);
            choix_Fichier.setDisable(true);

        }

    }
    @FXML
    private void choixFichier() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt", "*.text"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        Stage stage = (Stage) choix_Fichier.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            chemin_Fichier.setText(selectedFile.getAbsolutePath());
        }
    }
    @FXML
    private void activation_Choix_Fichier() {

        if (relaxationFile.isSelected()) {
            coefficient_RPI.setDisable(true);
            chemin_Fichier.setDisable(false);
            choix_Fichier.setDisable(false);

        }
        choixFichier();
    }
    @FXML
    private void activation_Temps_Chimique() {

        if (tsiEqualsChemicalTime.isSelected()) {
            intensite_Pluie.setDisable(false);

        } else if (tsiEqualsHByRpi.isSelected()) {
            intensite_Pluie.setDisable(true);
        }
        else if (tsiEqualsRpi.isSelected()){
            intensite_Pluie.setDisable(true);
        }
    }


}
