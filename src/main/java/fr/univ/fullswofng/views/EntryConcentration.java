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

public class EntryConcentration extends AbstractRefactoringInteractiveViews{


    @FXML
    private RadioButton noEntry,identicalCoeficient,file;
    @FXML
    private TextField cheminFichier;
    @FXML
    private Button choixFichier;
    @FXML
    private TextField Coeff_Cin;
    @FXML
    private TextField instant_Initial;
    @FXML
    private TextField Instant_Final;


    public static EntryConcentration createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(EntryConcentration.class.getResource("/entryConcentration.fxml"));
        try {
            fxmlLoader.load();
            EntryConcentration view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Petit souci de chargement du fichier entryConcentration.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case DISABLE_COEFF_CIN:{
                activation_De_Coef();
            }
            case ERREUR_INSTANT_INITIALE: {
                handleKeyPress();
                break;
            }
            case ERREUR_NB_INSTANT_SAUV: {
                handleKeyPress();
                break;
            }
            case ERREUR_INSTANT_FINAL:{
                handleKeyPress();
                break;
            }
        }

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

    @FXML
    private void handleKeyPress() {
        validateTextField(Coeff_Cin);
        validateTextField(instant_Initial);
        validateTextField(Instant_Final);

    }



/**
Cette méthode permet de choisir le fichier exclusivement au format texte
 */

    private void choixFichier() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt", "*.text"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        Stage stage = (Stage) choixFichier.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            cheminFichier.setText(selectedFile.getAbsolutePath());
        }
    }

    @Override
    public void setSubscription(LauncherOrder e) {
        e.subscription(this, OrderType.SHOW_CONCENTRATION,
                OrderType.ERREUR_COEF_CIN,
                OrderType.ERREUR_INSTANT_FINAL,
                OrderType.ERREUR_INSTANT_INITIALE,
                OrderType.DISABLE_COEFF_CIN);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle = new ToggleGroup();
        noEntry.setToggleGroup(toggle);
        identicalCoeficient.setToggleGroup(toggle);
        file.setToggleGroup(toggle);

    }
    @FXML
    private void activation_De_Coef() {

        if (identicalCoeficient.isSelected()) {
            cheminFichier.setDisable(true);
            choixFichier.setDisable(true);
            Coeff_Cin.setDisable(false);

    }
    }

    @FXML
    private void activation_Choix_Fichier() {
            if (file.isSelected()) {
                cheminFichier.setDisable(false);
                choixFichier.setDisable(false);
                Coeff_Cin.setDisable(true);
            }
            choixFichier();
        }
    @FXML
    private void pas_Entrer() {
        if (noEntry.isSelected()) {
            cheminFichier.setDisable(true);
            choixFichier.setDisable(true);
            Coeff_Cin.setDisable(true);
        }
    }


}




