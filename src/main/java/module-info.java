module com.project.databaseproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.project.databaseproject to javafx.fxml;
    exports com.project.databaseproject;
}