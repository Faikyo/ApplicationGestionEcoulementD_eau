package fr.univ.fullswofng.views;

import javafx.scene.Scene;

public abstract class AbstractView implements View {
    private Scene scene;

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void initialization() {
        // Crée une nouvelle scene et met en tant que conteneur principal le getTopParent de la vue en question
        this.scene = new Scene(this.getTopParent());
    }
}
