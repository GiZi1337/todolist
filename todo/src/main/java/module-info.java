module your.module.name {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    // Egyéb szükséges modulok
    exports com.example.todo;
    exports com.example.todo.controller;
    opens com.example.todo.controller;
    exports com.example.todo.model;
}
