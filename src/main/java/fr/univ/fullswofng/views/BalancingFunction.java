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

public class BalancingFunction extends AbstractRefactoringInteractiveViews {
    @FXML
    RadioButton  button_Linear;
    @FXML
    RadioButton button_No_Linear;
    @FXML
    RadioButton button_No_Linear_Less;
    @FXML
    RadioButton constant_exchange;
    @FXML
    RadioButton button_Exchange_rain;
    @FXML
    RadioButton button_exchange_flow;
    @FXML
    RadioButton button_Rain_and_Run_Of_Exchange;
    @FXML
    RadioButton button_Chemical_Exchange;
    @FXML
    RadioButton button_Identical_Coefficient;
    @FXML
    RadioButton button_File1;
    @FXML
    RadioButton button_Identical_Coefficient2;
    @FXML
    RadioButton button_File2;
    @FXML
    Button choice_file1;
    @FXML
    Button choice_file2;
    @FXML
    TextField characteristique_concentration_TextField;
    @FXML
    TextField rain_intensity1_TextField;
    @FXML
    TextField water_flow_threshold_FieldText;
    @FXML
    TextField coefficient_Ki_TextField;
    @FXML
    TextField coefficient_Ki2_TextField;
    @FXML
    TextField file_Path1_TextField;
    @FXML
    TextField file_Path2_TextField;
    @FXML
    TextField rain_For_transfer_TextField;





    public static BalancingFunction createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(BalancingFunction.class.getResource("/balancingFunction.fxml"));
        try {
            fxmlLoader.load();
            BalancingFunction view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Petit souci de chargement du fichier balancingFunction.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {

    }

    @Override
    public void setSubscription(LauncherOrder e) {
        e.subscription(this, OrderType.SHOW_RELAXATION_PARAMETERS);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        super.initialize(url, resourceBundle);

        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle1 = new ToggleGroup();
        button_Linear.setToggleGroup(toggle1);
        button_No_Linear.setToggleGroup(toggle1);
        button_No_Linear_Less.setToggleGroup(toggle1);

        ToggleGroup toggle2 = new ToggleGroup();
        constant_exchange.setToggleGroup(toggle2);
        button_Exchange_rain.setToggleGroup(toggle2);
        button_exchange_flow.setToggleGroup(toggle2);
        button_Rain_and_Run_Of_Exchange.setToggleGroup(toggle2);
        button_Chemical_Exchange.setToggleGroup(toggle2);

        ToggleGroup toggleGroup3 = new ToggleGroup();
        button_Identical_Coefficient.setToggleGroup(toggleGroup3);
        button_File1.setToggleGroup(toggleGroup3);

        ToggleGroup toggleGroup4 = new ToggleGroup();
        button_Identical_Coefficient2.setToggleGroup(toggleGroup4);
        button_File2.setToggleGroup(toggleGroup4);


    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleKeyPress() {
        inputValidation(characteristique_concentration_TextField);
        inputValidation(rain_intensity1_TextField);
        inputValidation(water_flow_threshold_FieldText);
        inputValidation(coefficient_Ki2_TextField);
        inputValidation(rain_For_transfer_TextField);
        inputValidation(coefficient_Ki_TextField);



    }
    private void inputValidation(TextField textField) {
        String input = textField.getText().trim();
        if (input.isEmpty()) {
            return;
        }
        try {
            int number = Integer.parseInt(input);
            if (number <= 0) {
                showAlert("ErrorLower", "Le nombre doit être supérieur à zéro.");
            }
        } catch (NumberFormatException e) {
            showAlert("ErrorInteger", "Veuillez saisir un entier");
        }
    }
    @FXML
    private void desactivation_Activation_composant() {
        if (button_Linear.isSelected()) {
            characteristique_concentration_TextField.setDisable(true);
            rain_intensity1_TextField.setDisable(true);
            water_flow_threshold_FieldText.setDisable(true);
            rain_For_transfer_TextField.setDisable(true);
            coefficient_Ki2_TextField.setDisable(true);
            button_Identical_Coefficient.setDisable(true);
            button_File1.setDisable(true);
            choice_file1.setDisable(true);
            file_Path1_TextField.setDisable(true);
            coefficient_Ki_TextField.setDisable(false);
            choice_file2.setDisable(true);
            file_Path2_TextField.setDisable(true);


        }
        if (button_No_Linear.isSelected()) {
            characteristique_concentration_TextField.setDisable(false);
            rain_intensity1_TextField.setDisable(true);
            water_flow_threshold_FieldText.setDisable(true);
            rain_For_transfer_TextField.setDisable(true);
            coefficient_Ki2_TextField.setDisable(true);
            button_Identical_Coefficient.setDisable(true);
            button_File1.setDisable(true);
            choice_file1.setDisable(true);
            file_Path1_TextField.setDisable(true);
            coefficient_Ki_TextField.setDisable(false);
            choice_file2.setDisable(true);
            file_Path2_TextField.setDisable(true);

        }
        if (button_No_Linear_Less.isSelected()) {
            characteristique_concentration_TextField.setDisable(false);
            rain_intensity1_TextField.setDisable(true);
            water_flow_threshold_FieldText.setDisable(true);
            rain_For_transfer_TextField.setDisable(true);
            coefficient_Ki2_TextField.setDisable(true);
            button_Identical_Coefficient.setDisable(true);
            button_File1.setDisable(true);
            choice_file1.setDisable(true);
            file_Path1_TextField.setDisable(true);
            coefficient_Ki_TextField.setDisable(false);
            choice_file2.setDisable(true);
            file_Path2_TextField.setDisable(true);
        }

    }
    @FXML
    private void desactivation_Activation_composant2(){
        if (constant_exchange.isSelected()){
            rain_intensity1_TextField.setDisable(true);
            water_flow_threshold_FieldText.setDisable(true);
            rain_For_transfer_TextField.setDisable(true);
            button_Identical_Coefficient.setDisable(true);
            button_File1.setDisable(true);
        }
        if (button_Exchange_rain.isSelected()){
            rain_intensity1_TextField.setDisable(false);
            water_flow_threshold_FieldText.setDisable(true);
            rain_For_transfer_TextField.setDisable(true);
            button_Identical_Coefficient.setDisable(true);
            button_File1.setDisable(true);
        }
        if (button_exchange_flow.isSelected()){
            rain_intensity1_TextField.setDisable(true);
            water_flow_threshold_FieldText.setDisable(false);
            rain_For_transfer_TextField.setDisable(true);
            button_Identical_Coefficient.setDisable(true);
            button_File1.setDisable(true);
        }
        if (button_Chemical_Exchange.isSelected()){
            rain_intensity1_TextField.setDisable(true);
            water_flow_threshold_FieldText.setDisable(true);
            rain_For_transfer_TextField.setDisable(false);
            button_Identical_Coefficient.setDisable(true);
            button_File1.setDisable(true);
            coefficient_Ki2_TextField.setDisable(true);
        }
        if (button_Rain_and_Run_Of_Exchange.isSelected()){
            coefficient_Ki2_TextField.setDisable(false);
            rain_intensity1_TextField.setDisable(false);
            water_flow_threshold_FieldText.setDisable(false);
            button_Identical_Coefficient.setDisable(false);
            rain_For_transfer_TextField.setDisable(false);
           // button_Identical_Coefficient.setDisable(true);
            button_File1.setDisable(false);
        }

    }
    @FXML
    private void desactivation_Activation_composant3(){
        if (button_Identical_Coefficient.isSelected()){
            coefficient_Ki2_TextField.setDisable(false);
            choice_file1.setDisable(true);
            file_Path1_TextField.setDisable(true);
        }

        if (button_File1.isSelected()){
            choice_file1.setDisable(false);
            file_Path1_TextField.setDisable(false);
            coefficient_Ki2_TextField.setDisable(true);
            choixFichier();
        }

    }
    @FXML
    private void desactivation_Activation_composant4(){
        if (button_Identical_Coefficient2.isSelected()){
            choice_file2.setDisable(true);
            file_Path2_TextField.setDisable(true);
        }
        if (button_File2.isSelected()){
            choice_file2.setDisable(false);
            file_Path2_TextField.setDisable(false);
            choixFichier2();

        }

    }


    private void choixFichier() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt", "*.text"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        Stage stage = (Stage) choice_file1.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            file_Path1_TextField.setText(selectedFile.getAbsolutePath());
        }
    }
    private void choixFichier2() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt", "*.text"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        Stage stage = (Stage) choice_file2.getScene().getWindow();
        java.io.File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            file_Path2_TextField.setText(selectedFile.getAbsolutePath());
        }
    }
}
