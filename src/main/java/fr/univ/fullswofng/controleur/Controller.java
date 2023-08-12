package fr.univ.fullswofng.controleur;

import fr.univ.fullswofng.controleur.order.LauncherOrder;
import fr.univ.fullswofng.views.ViewManager;

public interface Controller extends LauncherOrder {
    ViewManager getViewManager();
    void run();
}
