package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.order.ListenerOrder;
import javafx.stage.Stage;

import java.util.Collection;

public interface ViewManager extends ListenerOrder {
    Stage getStage();
    void addOrderListener(ListenerOrder listenerOrder);
    Collection<ListenerOrder> getOrderListeners();
    void addIteractiveView(InteractiveView interactiveView);
    Collection<InteractiveView> getInteractiveViews();
}
