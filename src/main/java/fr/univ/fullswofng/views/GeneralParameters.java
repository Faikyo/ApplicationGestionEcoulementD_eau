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

public class GeneralParameters extends AbstractRefactoringInteractiveViews  {

    @FXML
    public TextField numberOfClasses, numberOfSnapshots;
    public TextField savedConcentrations;
    @FXML
    public RadioButton one, two, yes, no;
    @FXML
    public ToggleGroup toggleGroupOrdre;
    public ToggleGroup toggleGroup1;

    public static GeneralParameters createView(ViewManagerImpl viewManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(GeneralParameters.class.getResource("/mainWindow.fxml"));
        try {
            fxmlLoader.load();
            GeneralParameters view = fxmlLoader.getController();
            view.initialization();
            viewManager.addIteractiveView(view);
            viewManager.addOrderListener(view);
            return view;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Petit souci de chargement du fichier mainView.fxml");
        }
    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case DISABLE -> {
                handleRadioButtonAction();
            }
            case ERREUR_NB_CLASSE, ERREUR_NB_INSTANT_SAUV, ERREUR_ORDRE_ESPACE -> {
                handleKeyPress();
            }
        }
    }

    @FXML
    private void handleRadioButtonAction() {
        if (yes.isSelected()) {
            savedConcentrations.setDisable(false);
        }
        // Sélectionnez uniquement un seul radiobutton
        initialize();
    }
    @FXML
    private void fieldDisabling(){
        if (no.isSelected()){
            savedConcentrations.setDisable(true);
        }
    }

    //gére les erreurs en appelant la méthode Alert de la classe Alert

    @FXML
    private void handleKeyPress() {
        inputValidation(numberOfClasses);
        inputValidation(numberOfSnapshots);
        inputValidation(savedConcentrations);

    }
/*
Cette méthode block la saisie de l'utilisateur en lui disant que sa saisie n'est pas bonne
 */
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



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void initialize() {
        toggleGroupOrdre = new ToggleGroup();
        one.setToggleGroup(toggleGroupOrdre);
        two.setToggleGroup(toggleGroupOrdre);
        toggleGroup1 = new ToggleGroup();
        yes.setToggleGroup(toggleGroup1);
        no.setToggleGroup(toggleGroup1);

    }
    @Override
    public void setSubscription(LauncherOrder e) {
        e.subscription(this, OrderType.SHOW_MENU,
                OrderType.DISABLE,
                OrderType.ERREUR_NB_CLASSE,
                OrderType.ERREUR_NB_INSTANT_SAUV );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        //RdioButtons : Pour que seulement un RadioButton soit selectionné à la fois
        ToggleGroup toggle1 = new ToggleGroup();
        one.setToggleGroup(toggle1);
        two.setToggleGroup(toggle1);

        ToggleGroup toggle2 = new ToggleGroup();
        yes.setToggleGroup(toggle2);
        no.setToggleGroup(toggle2);
    }


}

