package com.example.todo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class ToDoItem {
    private String title;
    private String description;
    private boolean completed;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private int priority;

    public ToDoItem() {

    }

    public ToDoItem(String title, String description, LocalDate dueDate, int priority, boolean completed) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = completed;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("dueDate")
    public LocalDate getDueDate() {
        return dueDate;
    }

    @JsonProperty("dueDate")
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @JsonProperty("priority")
    public int getPriority() {
        return priority;
    }

    @JsonProperty("priority")
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @JsonProperty("completed")
    public boolean isCompleted() {
        return completed;
    }

    @JsonProperty("completed")
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Due: %s, Priority: %d, Completed: %s)", title, description, dueDate, priority, completed ? "Yes" : "No");
    }
}

