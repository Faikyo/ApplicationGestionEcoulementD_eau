module fr.univ.fullswofng {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.web;


    opens fr.univ.fullswofng to javafx.fxml;
    exports fr.univ.fullswofng;
    exports fr.univ.fullswofng.controleur;
    opens fr.univ.fullswofng.controleur to javafx.fxml;
    opens fr.univ.fullswofng.views to javafx.fxml;
}