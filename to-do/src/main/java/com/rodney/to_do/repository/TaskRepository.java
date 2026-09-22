package com.rodney.to_do.repository;
import com.rodney.to_do.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task create(Task task);
    Task update(Task task);
    List<Task> getTasks();
    Optional<Task> getTaskByID(Long id);
}
