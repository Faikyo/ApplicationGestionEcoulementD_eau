package fr.univ.fullswofng.controleur.order;

public interface LauncherOrder {
    void subscription (ListenerOrder listenerOrder, OrderType... types);
    void fireOrder(OrderType orderType);
}
