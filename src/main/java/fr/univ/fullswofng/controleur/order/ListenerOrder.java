package fr.univ.fullswofng.controleur.order;

public interface ListenerOrder {
    void treat(OrderType e);
    void setSubscription(LauncherOrder e);
}
