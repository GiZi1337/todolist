package com.example.todo.model;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {
    private List<ToDoItem> items;

    public ToDoList() {
        items = new ArrayList<>();
    }

    public List<ToDoItem> getItems() {
        return items;
    }

    public void addItem(ToDoItem item) {
        items.add(item);
    }

    public void removeItem(ToDoItem item) {
        items.remove(item);
    }

    public ToDoItem findItemByTitle(String title) {
        return items.stream().filter(item -> item.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }

    public void clearAllItems() {
        items.clear();
    }
}
