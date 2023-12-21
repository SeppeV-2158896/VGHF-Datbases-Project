module be.vghf.vghfdatabase {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
    requires java.persistence;

    opens be.vghf.vghfdatabase to javafx.fxml;
    exports be.vghf.vghfdatabase;
}