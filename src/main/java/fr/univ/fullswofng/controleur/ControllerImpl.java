package fr.univ.fullswofng.controleur;
import fr.univ.fullswofng.controleur.abstractControler.AbstractController;
import fr.univ.fullswofng.controleur.order.OrderType;
import fr.univ.fullswofng.views.ViewManager;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class ControllerImpl extends AbstractController{

    // La façade du modele sera connue ici dans le futur



    public ControllerImpl(ViewManager viewManager, ControllerSetUp controllerSetUp) {
        super(viewManager);
        controllerSetUp.setUp(this, getViewManager());
        
    }

    public void goToGeneralParameters()
    {
        this.fireOrder(OrderType.SHOW_MENU);
    }
    public void goToEntryConcentration()
    {
        this.fireOrder(OrderType.SHOW_CONCENTRATION);
    }
    public void goToInitialConditions()
    {
        this.fireOrder(OrderType.SHOW_INITIAL_CONDITIONS);
    }
    public void goToMiConcentrationsCoeficient(){this.fireOrder(OrderType.SHOW_MI_CONCENTRATION_COEFICIENT);}
    public void goToRelaxationParameters() {this.fireOrder(OrderType.SHOW_RELAXATION_PARAMETERS);}
    public void goToBalancingFunction(){this.fireOrder(OrderType.SHOW_BALANCING_FUNCTION);}
    public void goToSpatialInputForFlowConcentration(){this.fireOrder(OrderType.SHOW_SPATIAL_INPUT_FOR_FLOW_CONCENTRATION);}
    public void goToRainFallInputForFlowConcentration(){this.fireOrder(OrderType.SHOW_RAINFALL_INPUT_FOR_FLOW_CONCENTRATION);}
    public void goToFlowInputForFlowConcentration(){this.fireOrder(OrderType.SHOW_FLOW_INPUT_FOR_FLOW_CONCENTRATION);}
    public void goToChemicalInputForExchangeLayer(){this.fireOrder(OrderType.SHOW_CHEMICAL_INPUT_FOR_EXCHANGE_LAYER);}
    public void goToOutputFileName(){this.fireOrder(OrderType.SHOW_OUTPUT_FILE_NAME);}


    public void run() {
        this.fireOrder(OrderType.SHOW_MENU);
    }


}