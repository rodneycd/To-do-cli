package com.rodney.to_do.service;

import com.rodney.to_do.model.Task;
import com.rodney.to_do.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(String description) {
        Task task = new Task(null, description, false);
        return taskRepository.create(task);
    }

    public List<Task> listTasks() {
        return taskRepository.getTasks();
    }

    public boolean completeTask(Long id) {
        Optional<Task> existing = taskRepository.getTaskByID(id);
        if (existing.isEmpty()) {
            return false;
        }
        Task task = existing.get();
        task.setCompleted(true);
        taskRepository.update(task);
        return true;
    }
}
