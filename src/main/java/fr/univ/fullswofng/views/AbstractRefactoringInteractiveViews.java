package fr.univ.fullswofng.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import fr.univ.fullswofng.controleur.ControllerImpl;
import fr.univ.fullswofng.controleur.order.ListenerOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import fr.univ.fullswofng.views.upperNavBar.Procedures;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractRefactoringInteractiveViews extends AbstractView implements InteractiveView, ListenerOrder, Initializable {

    // Cette classe à été renommée de AbstractInteractiveView à AbstractRefactoringInteractiveViews pour intégrer la refacto du code ->
    // environ 1200 lignes de code économisées.
    private ControllerImpl controller;

    @Override
    public ControllerImpl getController() {
        return controller;
    }

    @Override
    public void setController(ControllerImpl controller) {
        this.controller = controller;
    }

    @FXML
    protected JFXPopup popupFile;
    @FXML
    protected JFXPopup popupPreferences;
    @FXML
    protected JFXPopup popupHelp;
    @FXML
    protected JFXButton fichier;
    @FXML
    protected JFXButton preferences;
    @FXML
    protected JFXButton aide;
    @FXML
    protected AnchorPane anchorPane, pane2;
    @FXML
    protected Pane pane3;
    @FXML
    protected ImageView menu;

    public void goToGeneralParameters(ActionEvent actionEvent)
    {
        this.getController().goToGeneralParameters();
    }
    public void goToEntryConcentration(ActionEvent actionEvent)
    {
        this.getController().goToEntryConcentration();
    }
    public void goToInitialConditions(ActionEvent actionEvent)
    {
        this.getController().goToInitialConditions();
    }
    public void goToMiConcentrationsCoeficient(ActionEvent actionEvent) {this.getController().goToMiConcentrationsCoeficient();}
    public void goToRelaxationParameters(ActionEvent actionEvent)
    {
        this.getController().goToRelaxationParameters();
    }
    public void goToBalancingFunction(ActionEvent actionEvent)
    {
        this.getController().goToBalancingFunction();
    }
    public void goToSpatialInputFOrFlowConcentration(ActionEvent actionEvent) {this.getController().goToSpatialInputForFlowConcentration();}
    public void goToRainFallInputForFlowConcentration(ActionEvent actionEvent)
    {
        this.getController().goToRainFallInputForFlowConcentration();
    }
    public void goToFlowInputForFlowConcentration(ActionEvent actionEvent)
    {
        this.getController().goToFlowInputForFlowConcentration();
    }
    public void goToChemicalInputForExchangeLayer(ActionEvent actionEvent) {this.getController().goToChemicalInputForExchangeLayer();}
    public void goToOutputFileName(ActionEvent actionEvent) {this.getController().goToOutputFileName();}


    @Override
    public Parent getTopParent() {
        return anchorPane;
    }

    @FXML
    protected void showPopupFile() {
        popupFile.show(fichier, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }
    @FXML
    protected void showPopupPreferences() {popupPreferences.show(preferences, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);}
    @FXML
    protected void showPopupHelp() {
        popupHelp.show(aide, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Bouttons du popup fichier
        JFXButton newProject = new JFXButton("Nouveau projet");
        JFXButton openProject = new JFXButton("Ouvrir projet");
        JFXButton importParameterFile= new JFXButton("Importer un fichier de paramètres");
        JFXButton saveProject = new JFXButton("Enregistrer projet");
        JFXButton saveProjectInLocation = new JFXButton("Enregistrer projet sous...");
        JFXButton launchSimulation = new JFXButton("Lancer la simulation");
        JFXButton openResultsFile = new JFXButton("Ouvrir un fichier de résultats");
        JFXButton exit = new JFXButton("Fermer");


        // Bouttons du popup préférences
        JFXButton language = new JFXButton("Langue");

        // Bouttons du popup Aide
        JFXButton about = new JFXButton("À propos");
        JFXButton help = new JFXButton("aide");

        // Vbox du popup fichier
        VBox buttonBox = new VBox(newProject, openProject, importParameterFile,
                saveProject, saveProjectInLocation, launchSimulation, openResultsFile,exit);
        buttonBox.setSpacing(5);

        // Vbox du popup préférences
        VBox buttonBox2 = new VBox(language);
        buttonBox2.setSpacing(5);

        // Vbox du popup Aide
        VBox buttonBox3 = new VBox(about, help);
        buttonBox2.setSpacing(5);

        // Ici on met de contenu souhaité dans chaque popup
        popupFile = new JFXPopup();
        popupFile.setPopupContent(buttonBox);

        popupPreferences = new JFXPopup();
        popupPreferences.setPopupContent(buttonBox2);

        popupHelp = new JFXPopup();
        popupHelp.setPopupContent(buttonBox3);

        // Ici on définit les evenements au clic de chaque bouton de la navbar du haut
        fichier.setOnAction(event -> showPopupFile());
        preferences.setOnAction(event -> showPopupPreferences());
        aide.setOnAction(event -> showPopupHelp());

        // J'utilise ici la classe Procedure pour gérer les actions plus facilement
        newProject.setOnAction(event -> Procedures.newProjectAfterConfirmation());
        openProject.setOnAction(event -> Procedures.openProject());
        importParameterFile.setOnAction(event -> Procedures.importParameters());
        saveProject.setOnAction(event -> Procedures.saveProject());
        saveProjectInLocation.setOnAction(event -> Procedures.saveProjectAs());
        launchSimulation.setOnAction(event -> Procedures.runProject());
        openResultsFile.setOnAction(event -> Procedures.openVisualisation());
        exit.setOnAction(event -> Procedures.close());

        language.setOnAction(event -> Procedures.setPreferences());

        about.setOnAction(event -> Procedures.about());
        help.setOnAction(event -> Procedures.help());

        // Ici on gère la fonction "Slide"
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setToX(0);
        translateTransition.play();

        TranslateTransition translateTransitionPrim = new TranslateTransition(Duration.seconds(0.5), pane3);
        translateTransitionPrim.setToX(0);
        translateTransitionPrim.play();


        menu.setOnMouseClicked(event -> {

            if (pane2.getTranslateX() != 0) {
                // Si pane2 est déjà déplacé vers la gauche, on le ramène à sa position d'origine
                translateTransition.setToX(0);
                translateTransitionPrim.setToX(0);
            } else {
                // Sinon, on le déplace vers la gauche
                translateTransition.setToX(-280);
                translateTransitionPrim.setToX(-280);
            }

            translateTransition.play();
            translateTransitionPrim.play();
        });
    }

}
