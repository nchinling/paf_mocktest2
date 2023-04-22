package sg.edu.nus.iss.paf2.model;

import java.time.LocalDate;

public class Task {
    private String description;
    private Integer priority;
    private LocalDate dueDate;
    private String username;
    public Task() {
    }

    
    public Task(String description, Integer priority, LocalDate dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Task(String description, Integer priority, LocalDate dueDate, String username) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.username = username;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return "Task [description=" + description + ", priority=" + priority + ", dueDate=" + dueDate + ", username="
                + username + "]";
    }

    
    
}
