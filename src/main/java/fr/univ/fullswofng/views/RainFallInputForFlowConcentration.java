package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.order.LauncherOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RainFallInputForFlowConcentration extends AbstractRefactoringInteractiveViews {

    @FXML
    public RadioButton noInputRain;
    @FXML
    public RadioButton inputRain;
    @FXML
    private TextField instant_final;
    @FXML
    private TextField detachabilite_sol;
    @FXML
    private TextField masse_Saturation;
    @FXML
    private TextField seuil_saturation;
    @FXML
    private RadioButton identique;
    @FXML
    private RadioButton fichiers;
    @FXML
    private Button choix_fichier;
    @FXML
    private TextField chemin_fichiers;
    @FXML
    private RadioButton coeff_constant;
    @FXML
    private RadioButton forme_exponentielle;
    @FXML
    private TextField  coeff_B ;
    @FXML
    private TextField Coeff_pi;

    public static RainFallInputForFlowConcentration createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(RainFallInputForFlowConcentration.class.getResource("/rainfallInputForFlowConcentration.fxml"));
        try {
            fxmlLoader.load();
            RainFallInputForFlowConcentration view = fxmlLoader.getController();
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
            case ERREUR_DETACHABILITE, ERREUR_INSTANT_FINAL_APPORT_PLUIE, ERRUR_MASSE_SATURATION, ERREUR_SEUIL,
                    ERREUR_COEF_PI, ERREUR_COEFF_B -> {
                handleKeyPress();
            }
        }
    }

    @Override
    public void setSubscription(LauncherOrder e) {
        e.subscription(this, OrderType.SHOW_RAINFALL_INPUT_FOR_FLOW_CONCENTRATION,
                OrderType.ERREUR_DETACHABILITE,
                OrderType.ERREUR_INSTANT_FINAL_APPORT_PLUIE,
                OrderType.ERRUR_MASSE_SATURATION,
                OrderType.ERREUR_SEUIL,
                OrderType.ERREUR_COEF_PI
                ,OrderType.ERREUR_COEFF_B);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);

        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle = new ToggleGroup();
        noInputRain.setToggleGroup(toggle);
        inputRain.setToggleGroup(toggle);
        ToggleGroup toggle1 = new ToggleGroup();
        identique.setToggleGroup(toggle1);
        fichiers.setToggleGroup(toggle1);
        ToggleGroup toggle2 = new ToggleGroup();
        coeff_constant.setToggleGroup(toggle2);
        forme_exponentielle.setToggleGroup(toggle2);
    }
    private void handleIdentiqueClick() {
        Coeff_pi.setDisable(false);
        Coeff_pi.setEditable(true);
        choix_fichier.setDisable(true);
        chemin_fichiers.setDisable(true);
    }

    private void handleFichiersClick() {
        Coeff_pi.setDisable(true);
        choix_fichier.setDisable(false);
        chemin_fichiers.setDisable(false);
    }



    @FXML
    private void desactivation_Activation_composant() {
        if (noInputRain.isSelected()) {
            instant_final.setDisable(true);
            detachabilite_sol.setDisable(true);
            seuil_saturation.setDisable(true);
            masse_Saturation.setDisable(true);
            identique.setDisable(true);
            fichiers.setDisable(true);
            coeff_constant.setDisable(true);
            choix_fichier.setDisable(true);
            chemin_fichiers.setDisable(true);
            forme_exponentielle.setDisable(true);
            coeff_B.setDisable(true);
            Coeff_pi.setDisable(true);
        }
       if (inputRain.isSelected()) {
            instant_final.setDisable(false);
            detachabilite_sol.setDisable(false);
            seuil_saturation.setDisable(false);
            masse_Saturation.setDisable(false);
            identique.setDisable(false);
            fichiers.setDisable(false);
           if (identique.isSelected()) {
               handleIdentiqueClick();
           }

           if (fichiers.isSelected()) {
               handleFichiersClick();
               choixFichier();
           }
            coeff_constant.setDisable(false);
            forme_exponentielle.setDisable(false);
            coeff_B.setDisable(false);
            coeff_B.setDisable(false);
        }
    }

    @FXML
    private void handleKeyPress() {
        validateTextField(instant_final);
        validateTextField(detachabilite_sol);
        validateTextField(masse_Saturation);
        validateTextField(seuil_saturation);
        validateTextField(coeff_B);
        validateTextField(Coeff_pi);
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
