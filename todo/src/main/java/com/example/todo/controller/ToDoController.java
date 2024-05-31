package com.example.todo.controller;

import com.example.todo.model.ToDoItem;
import com.example.todo.model.ToDoList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ToDoController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private ChoiceBox<Integer> priorityChoiceBox;
    @FXML
    private ListView<ToDoItem> todoListView;
    @FXML
    private CheckBox completedCheckBox;

    private ToDoList todoList;
    private ObservableList<ToDoItem> observableTodoList;
    private ObjectMapper objectMapper;
    private final String filePath = "todos.json";

    public ToDoController() {
        todoList = new ToDoList();
        observableTodoList = FXCollections.observableArrayList(todoList.getItems());
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        loadToDoList();
    }

    @FXML
    public void initialize() {
        priorityChoiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        todoListView.setItems(observableTodoList);
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                titleField.setText(newValue.getTitle());
                descriptionField.setText(newValue.getDescription());
                dueDatePicker.setValue(newValue.getDueDate());
                priorityChoiceBox.setValue(newValue.getPriority());
                completedCheckBox.setSelected(newValue.isCompleted());
            }
        });
    }

    @FXML
    private void addTask(ActionEvent event) {
        String title = titleField.getText();
        String description = descriptionField.getText();
        LocalDate dueDate = dueDatePicker.getValue();
        Integer priority = priorityChoiceBox.getValue();
        boolean completed = completedCheckBox.isSelected();

        if (title.isEmpty() || description.isEmpty() || dueDate == null || priority == null) {
            showAlert("Invalid Input", "Please fill in all fields.");
            return;
        }

        if (dueDate.isBefore(LocalDate.now())) {
            showAlert("Invalid Date", "Due date cannot be in the past.");
            return;
        }

        ToDoItem item = new ToDoItem(title, description, dueDate, priority, completed);
        todoList.addItem(item);
        observableTodoList.setAll(todoList.getItems());
        clearFields();
        saveToDoList();
    }

    @FXML
    private void removeTask(ActionEvent event) {
        ToDoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            todoList.removeItem(selectedItem);
            observableTodoList.setAll(todoList.getItems());
            clearFields();
            saveToDoList();
        } else {
            showAlert("No Selection", "Please select a task to remove.");
        }
    }

    @FXML
    private void updateTask(ActionEvent event) {
        ToDoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setTitle(titleField.getText());
            selectedItem.setDescription(descriptionField.getText());
            selectedItem.setDueDate(dueDatePicker.getValue());
            selectedItem.setPriority(priorityChoiceBox.getValue());
            selectedItem.setCompleted(completedCheckBox.isSelected());
            observableTodoList.setAll(todoList.getItems());
            clearFields();
            saveToDoList();
        } else {
            showAlert("No Selection", "Please select a task to update.");
        }
    }

    private void loadToDoList() {
        try {
            File file = new File(filePath);
            if (file.exists() && file.length() > 0) {
                List<ToDoItem> items = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, ToDoItem.class));
                todoList.getItems().addAll(items);
                observableTodoList.setAll(todoList.getItems());
            } else {
                todoList = new ToDoList();
                saveToDoList();  // Create the file if it does not exist
            }
        } catch (MismatchedInputException e) {
            showAlert("Error", "JSON format error: " + e.getMessage());
            e.printStackTrace();
            todoList = new ToDoList();
            saveToDoList();  // Create the file if it does not exist
        } catch (IOException e) {
            showAlert("Error", "Error loading to-do list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveToDoList() {
        try {
            objectMapper.writeValue(new File(filePath), todoList.getItems());
        } catch (IOException e) {
            showAlert("Error", "Error saving to-do list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        titleField.clear();
        descriptionField.clear();
        dueDatePicker.setValue(null);
        priorityChoiceBox.setValue(null);
        completedCheckBox.setSelected(false);
    }
}
