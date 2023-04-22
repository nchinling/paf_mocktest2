package sg.edu.nus.iss.paf2.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String username;
    private String name;

    private List<Task> todoList = new ArrayList<Task>();

    public User() {
    }

    public User(String userId, String username, String name, List<Task> todoList) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.todoList = todoList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Task> todoList) {
        this.todoList = todoList;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", name=" + name + ", todoList=" + todoList + "]";
    }

    



    
}
