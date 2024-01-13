module com.demo1.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.demo1.demo1 to javafx.fxml, gson;
    exports com.demo1.demo1;
}