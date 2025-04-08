module com.github.lfernandocn.pharmamanager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.lfernandocn.pharmamanager to javafx.fxml;
    opens com.github.lfernandocn.pharmamanager.controllers to javafx.fxml; // necessário se os controllers estiverem aqui

    exports com.github.lfernandocn.pharmamanager;
    exports com.github.lfernandocn.pharmamanager.controllers;
    exports com.github.lfernandocn.pharmamanager.models;
    exports com.github.lfernandocn.pharmamanager.utils;
    exports com.github.lfernandocn.pharmamanager.repositorys;
}