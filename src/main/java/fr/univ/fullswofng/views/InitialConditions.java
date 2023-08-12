package fr.univ.fullswofng.views;

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

public class InitialConditions extends AbstractRefactoringInteractiveViews implements ListenerOrder, Initializable {

    @FXML
    private RadioButton zero,ciMiidenticalCoeficient,file;
    @FXML
    private TextField coefficient_Ci;
    @FXML
    private TextField coefficient_Mi;
    @FXML
    private TextField chemin_Fichier;
    @FXML
    private Button choix_Fichier;

    public static InitialConditions createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(InitialConditions.class.getResource("/initialConditions.fxml"));
        try {
            fxmlLoader.load();
            InitialConditions view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
        throw new RuntimeException("Petit souci de chargement du fichier initialConditions.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case ERREUR_COEF_CI, ERREUR_COEF_CONCENTRATION_MI -> {
                handleKeyPress();
            }
        }
    }

    @FXML
    private void handleKeyPress() {
        validateTextField(coefficient_Ci);
        validateTextField(coefficient_Mi);

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
        e.subscription(this, OrderType.SHOW_INITIAL_CONDITIONS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle = new ToggleGroup();
        zero.setToggleGroup(toggle);
        ciMiidenticalCoeficient.setToggleGroup(toggle);
        file.setToggleGroup(toggle);
    }
    @FXML
    private void desactivation_composant() {

        if (zero.isSelected()) {
            coefficient_Ci.setDisable(true);
            coefficient_Mi.setDisable(true);
            chemin_Fichier.setDisable(true);
            choix_Fichier.setDisable(true);

        }

    }


    @FXML
    private void sans_Fichier() {

        if (ciMiidenticalCoeficient.isSelected()) {
            coefficient_Ci.setDisable(false);
            coefficient_Mi.setDisable(false);
            chemin_Fichier.setDisable(true);
            choix_Fichier.setDisable(true);

        }

    }
    @FXML
    private void activation_Choix_Fichier() {

        if (file.isSelected()) {
            coefficient_Ci.setDisable(true);
            coefficient_Mi.setDisable(true);
            chemin_Fichier.setDisable(false);
            choix_Fichier.setDisable(false);

        }
        choixFichier();
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

}
