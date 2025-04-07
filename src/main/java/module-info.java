module com.github.lfernandocn.pharmamanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.lfernandocn.pharmamanager to javafx.fxml;
    exports com.github.lfernandocn.pharmamanager;
}