module be.vghf{
    requires javafx.controls;
    requires javafx.fxml;

    requires java.persistence; // Correct module name for JPA

    opens be.vghf to javafx.fxml;
    exports be.vghf;
    exports be.vghf.controllers;
    opens be.vghf.controllers to javafx.fxml;
}
