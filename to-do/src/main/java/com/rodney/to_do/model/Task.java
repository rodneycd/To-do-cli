package com.rodney.to_do.model;

public class Task {
    private Long id;
    private String description;
    private Boolean completed;

    public Task(){

    }

    public Task(Long id, String description, Boolean completed){
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{id=" + id + ", description='" + description + "', completed=" + completed + '}';
    }
}
