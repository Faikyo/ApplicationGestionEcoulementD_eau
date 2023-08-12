package fr.univ.fullswofng.views;

import javafx.scene.Parent;
import javafx.scene.Scene;

public interface View {
    Scene getScene();
    void setScene(Scene scene);
    Parent getTopParent();
    void initialization();
}
