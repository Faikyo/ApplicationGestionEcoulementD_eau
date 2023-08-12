package fr.univ.fullswofng.views;

import fr.univ.fullswofng.controleur.ControllerImpl;

public interface InteractiveView extends View{
    ControllerImpl getController();
    void setController(ControllerImpl controller);
}
