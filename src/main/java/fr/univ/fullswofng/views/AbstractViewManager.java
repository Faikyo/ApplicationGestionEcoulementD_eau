package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.order.ListenerOrder;
import fr.univ.fullswofng.views.upperNavBar.Procedures;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractViewManager implements ViewManager{
    private Stage stage;
    private Collection<ListenerOrder> orderListeners;
    private  Collection<InteractiveView> interactiveViews;

    public AbstractViewManager(Stage stage) {
        this.stage = stage;
        interactiveViews = new ArrayList<>();
        orderListeners = new ArrayList<>();

        // J'initialise le stage pour la classe Procedures
        new Procedures(this.stage);

        // Il faut AUTOMATIQUEMENT ajouter le Gestionnaire de vue en tant qu'écouteur d'ordres car il va gérer leS
        // changement de vue donc il devera forcément réagir à ce type d'ordre.

        this.orderListeners.add(this);

    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void addOrderListener(ListenerOrder listenerOrder) {
        this.orderListeners.add(listenerOrder);
    }

    @Override
    public Collection<ListenerOrder> getOrderListeners() {
        return orderListeners;
    }

    @Override
    public void addIteractiveView(InteractiveView interactiveView) {
        this.interactiveViews.add(interactiveView);
    }

    @Override
    public Collection<InteractiveView> getInteractiveViews() {
        return interactiveViews;
    }
}
