package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.order.LauncherOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewManagerImpl extends AbstractViewManager {

    private GeneralParameters generalParameters;
    private EntryConcentration entryConcentration;
    private InitialConditions initialConditions;
    private MiConcentrationsCoeficient miConcentrationsCoeficient;
    private RelaxationParameters relaxationParameters;
    private BalancingFunction balancingFunction;
    private SpatialInputForFlowConcentration spatialInputForFlowConcentration;
    private RainFallInputForFlowConcentration rainFallInputForFlowConcentration;
    private FlowInputForFlowConcentration flowInputForFlowConcentration;
    private ChemicalInputForExchangeLayer chemicalInputForExchangeLayer;
    private OutputFileName outputFileName;

    public ViewManagerImpl(Stage stage) {
        super(stage);
        generalParameters = GeneralParameters.createView(this);
        entryConcentration = EntryConcentration.createView(this);
        initialConditions = InitialConditions.createView(this);
        miConcentrationsCoeficient = MiConcentrationsCoeficient.createView(this);
        relaxationParameters = RelaxationParameters.createView(this);
        balancingFunction = BalancingFunction.createView(this);
        spatialInputForFlowConcentration = SpatialInputForFlowConcentration.createView(this);
        rainFallInputForFlowConcentration = RainFallInputForFlowConcentration.createView(this);
        flowInputForFlowConcentration = FlowInputForFlowConcentration.createView(this);
        chemicalInputForExchangeLayer = ChemicalInputForExchangeLayer.createView(this);
        outputFileName = OutputFileName.createView(this);
    }

    @Override
    public void setSubscription(LauncherOrder e) {
        //super.setSubcription(e);
        e.subscription(this, OrderType.SHOW_MENU, OrderType.SHOW_CONCENTRATION,
                OrderType.SHOW_INITIAL_CONDITIONS, OrderType.SHOW_MI_CONCENTRATION_COEFICIENT,
                OrderType.SHOW_RELAXATION_PARAMETERS, OrderType.SHOW_BALANCING_FUNCTION,
                OrderType.SHOW_SPATIAL_INPUT_FOR_FLOW_CONCENTRATION, OrderType.SHOW_RAINFALL_INPUT_FOR_FLOW_CONCENTRATION,
                 OrderType.SHOW_FLOW_INPUT_FOR_FLOW_CONCENTRATION,
                OrderType.SHOW_CHEMICAL_INPUT_FOR_EXCHANGE_LAYER, OrderType.SHOW_OUTPUT_FILE_NAME);
    }

    @Override
    public void treat(OrderType e) {
        switch (e) {
            case SHOW_MENU -> {
                this.getStage().setScene(this.generalParameters.getScene());
                getStage().show();
            }
            case SHOW_CONCENTRATION -> {
                this.getStage().setScene(this.entryConcentration.getScene());
                getStage().show();
            }
            case SHOW_INITIAL_CONDITIONS -> {
                this.getStage().setScene(this.initialConditions.getScene());
                getStage().show();
            }
            case SHOW_MI_CONCENTRATION_COEFICIENT -> {
                this.getStage().setScene(this.miConcentrationsCoeficient.getScene());
                getStage().show();
            }
            case SHOW_RELAXATION_PARAMETERS -> {
                this.getStage().setScene(this.relaxationParameters.getScene());
                getStage().show();
            }
            case SHOW_BALANCING_FUNCTION -> {
                this.getStage().setScene(this.balancingFunction.getScene());
                getStage().show();
            }
            case SHOW_SPATIAL_INPUT_FOR_FLOW_CONCENTRATION -> {
                this.getStage().setScene(this.spatialInputForFlowConcentration.getScene());
                getStage().show();
            }
            case SHOW_RAINFALL_INPUT_FOR_FLOW_CONCENTRATION -> {
                this.getStage().setScene(this.rainFallInputForFlowConcentration.getScene());
                getStage().show();
            }
            case SHOW_FLOW_INPUT_FOR_FLOW_CONCENTRATION -> {
                this.getStage().setScene(this.flowInputForFlowConcentration.getScene());
                getStage().show();
            }
            case SHOW_CHEMICAL_INPUT_FOR_EXCHANGE_LAYER -> {
                this.getStage().setScene(this.chemicalInputForExchangeLayer.getScene());
                getStage().show();
            }
            case SHOW_OUTPUT_FILE_NAME -> {
                this.getStage().setScene(this.outputFileName.getScene());
                getStage().show();
            }
        }
    }
}
