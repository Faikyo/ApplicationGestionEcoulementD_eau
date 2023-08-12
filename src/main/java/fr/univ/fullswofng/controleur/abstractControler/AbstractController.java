package fr.univ.fullswofng.controleur.abstractControler;

import fr.univ.fullswofng.controleur.Controller;
import fr.univ.fullswofng.controleur.order.ListenerOrder;
import fr.univ.fullswofng.controleur.order.OrderType;
import fr.univ.fullswofng.views.ViewManager;

import java.util.*;

public abstract class AbstractController implements Controller {

    private ViewManager abstractViewManager;
    private Map<OrderType, Collection<ListenerOrder>> subcribers;
    public ViewManager getViewManager() {
        return abstractViewManager;
    }


    public AbstractController(ViewManager viewManager){
        this.abstractViewManager = viewManager;
        this.subcribers = new HashMap<>();
        Arrays.stream(OrderType.values()).forEach(t -> {this.subcribers.put(t, new ArrayList<>());});
    }

    public void subscription(ListenerOrder listenerOrder, OrderType... types)
    {
        Arrays.stream(types).forEach(t -> this.subcribers.get(t).add(listenerOrder));
    }

    public void fireOrder(OrderType orderType)
    {
        this.subcribers.get(orderType).stream().forEach(type -> type.treat(orderType));
    }

    public abstract void run();
}
