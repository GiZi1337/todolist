package com.example.todo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoListTest {
    private ToDoList todoList;
    private ToDoItem item1;
    private ToDoItem item2;

    @BeforeEach
    public void setUp() {
        todoList = new ToDoList();
        item1 = new ToDoItem("Task 1", "Description 1", LocalDate.now(), 1, false);
        item2 = new ToDoItem("Task 2", "Description 2", LocalDate.now().plusDays(1), 2, false);
    }

    @Test
    public void testAddItem() {
        todoList.addItem(item1);
        assertEquals(1, todoList.getItems().size());
        assertTrue(todoList.getItems().contains(item1));
    }

    @Test
    public void testRemoveItem() {
        todoList.addItem(item1);
        todoList.removeItem(item1);
        assertEquals(0, todoList.getItems().size());
        assertFalse(todoList.getItems().contains(item1));
    }

    @Test
    public void testFindItemByTitle() {
        todoList.addItem(item1);
        todoList.addItem(item2);
        ToDoItem foundItem = todoList.findItemByTitle("Task 1");
        assertNotNull(foundItem);
        assertEquals("Task 1", foundItem.getTitle());
    }

    @Test
    public void testClearAllItems() {
        todoList.addItem(item1);
        todoList.addItem(item2);
        todoList.clearAllItems();
        assertEquals(0, todoList.getItems().size());
    }
}
