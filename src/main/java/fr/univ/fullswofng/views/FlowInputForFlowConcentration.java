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

public class FlowInputForFlowConcentration extends AbstractRefactoringInteractiveViews {

    public RadioButton noInputFlow;
    public RadioButton inputFlow;
    @FXML
    private TextField seuil_debit;
    @FXML
    private TextField coeff_flux;
    @FXML
    private RadioButton identique ;
    @FXML
    private RadioButton fichiers;
    @FXML
    private  TextField coeff_pi;
    @FXML
    private TextField saturation_mass;
    @FXML
    private Button choix_fichier;
    @FXML
    private TextField chemin_fichiers;
    @FXML
    private TextField energie_entrainement;
    @FXML
    private  TextField instant_final;


    public static FlowInputForFlowConcentration createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(FlowInputForFlowConcentration.class.getResource("/flowInputForFlowConcentration.fxml"));
        try {
            fxmlLoader.load();
            FlowInputForFlowConcentration view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Petit souci de chargement du fichier flowInputForFlowConcentration.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case ERREUR_SEUIL_DEBIT: {
                handleKeyPress();
                break;
            }
            case ERREUR_COEF_FLUX: {
                handleKeyPress();
                break;
            }
            case ERREUR_COEFF_PI: {
                handleKeyPress();
                break;
            }
            case ERREUR_ENERGIE: {
                handleKeyPress();
                break;
            }
            case ERREUR_INSTANT_FINALE: {
                handleKeyPress();
                break;
            }

        }
    }
    @FXML
    private void desactivation_Activation_composant() {
        if (noInputFlow.isSelected()) {
            instant_final.setDisable(true);
            seuil_debit.setDisable(true);
            coeff_flux.setDisable(true);
            coeff_pi.setDisable(true);
            identique.setDisable(true);
            fichiers.setDisable(true);
            energie_entrainement.setDisable(true);
            choix_fichier.setDisable(true);
            chemin_fichiers.setDisable(true);
            saturation_mass.setDisable(true);

        }
        else if (inputFlow.isSelected()) {
            instant_final.setDisable(false);
            seuil_debit.setDisable(false);
            coeff_flux.setDisable(false);
            coeff_pi.setDisable(false);
            identique.setDisable(false);
            fichiers.setDisable(false);
            saturation_mass.setDisable(false);
            energie_entrainement.setDisable(false);
        }
    }
    @FXML void desactivation_Activation_composant2(){
        if (identique.isSelected()){
            coeff_pi.setDisable(false);
            choix_fichier.setDisable(true);
            chemin_fichiers.setDisable(true);
        }
        if(fichiers.isSelected()){
            coeff_pi.setDisable(true);
            choix_fichier.setDisable(false);
            chemin_fichiers.setDisable(false);
            choixFichier();
        }

    }

    @FXML
    private void handleKeyPress() {
        validateTextField(instant_final);
        validateTextField(seuil_debit);
        validateTextField(coeff_flux);
        validateTextField(coeff_pi);
        validateTextField(energie_entrainement);
        validateTextField(saturation_mass);

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
        e.subscription(this, OrderType.SHOW_FLOW_INPUT_FOR_FLOW_CONCENTRATION,
                OrderType.ERREUR_SEUIL_DEBIT,
                OrderType.ERREUR_COEF_FLUX,
                OrderType.ERREUR_COEFF_PI,
                OrderType.ERREUR_ENERGIE,
                OrderType.ERREUR_INSTANT_FINALE);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle = new ToggleGroup();
        noInputFlow.setToggleGroup(toggle);
        inputFlow.setToggleGroup(toggle);
        ToggleGroup toggle1 = new ToggleGroup();
        identique.setToggleGroup(toggle1);
        fichiers.setToggleGroup(toggle1);
    }
    private void choixFichier() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt", "*.text"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        Stage stage = (Stage) choix_fichier.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            chemin_fichiers.setText(selectedFile.getAbsolutePath());
        }
    }
}
