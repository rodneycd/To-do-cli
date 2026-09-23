package com.rodney.to_do.repository;

import com.rodney.to_do.model.Task;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("memory")
public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();
    private long idCounter = 0;

    @Override
    public Task create(Task task){
        task.setId(++idCounter);
        tasks.add(task);
        return task;
    }

    @Override
    public Task update(Task task){
        tasks.removeIf(t -> t.getId().equals(task.getId()));
        tasks.add(task);
        return task;
    }

    @Override
    public List<Task> getTasks(){
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getId))
                .toList();
    }

    @Override
    public Optional<Task> getTaskByID(Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }
}