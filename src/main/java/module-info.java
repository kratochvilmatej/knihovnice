module cz.kratochvil.knihovnice {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.kratochvil.knihovnice to javafx.fxml;
    exports cz.kratochvil.knihovnice;
}