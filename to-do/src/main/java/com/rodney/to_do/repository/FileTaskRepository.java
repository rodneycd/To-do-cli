package com.rodney.to_do.repository;

import com.rodney.to_do.model.Task;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("file")
public class FileTaskRepository implements TaskRepository {

    private final Path filePath = Path.of("tasks.txt");

    @Override
    public Task create(Task task) {
        List<Task> existing = readAll();
        //search for highest present ID to assign the next task ID
        Long nextId = existing.stream()
                .mapToLong(Task::getId)
                .max()
                .orElse(0L) + 1;
        task.setId(nextId);
        existing.add(task);
        writeAll(existing);
        return task;
    }

    @Override
    public Task update(Task task) {
        List<Task> existing = readAll();
        existing.removeIf(t -> t.getId().equals(task.getId()));
        existing.add(task);
        writeAll(existing);
        return task;
    }

    @Override
    public List<Task> getTasks() {
        return readAll();
    }

    @Override
    public Optional<Task> getTaskByID(Long id) {
        return readAll().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    // helpers

    private List<Task> readAll() {
        try {
            if (!Files.exists(filePath)) {
                return new ArrayList<>();
            }
            List<Task> tasks = new ArrayList<>();
            for (String line : Files.readAllLines(filePath)) {
                if (line.isBlank()) continue;
                String[] parts = line.split("\\|", 3);
                Long id = Long.parseLong(parts[0]);
                String description = parts[1];
                Boolean completed = Boolean.parseBoolean(parts[2]);
                tasks.add(new Task(id, description, completed));
            }
            return tasks;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read tasks file", e);
        }
    }

    private void writeAll(List<Task> tasks) {
        try {
            List<String> lines = tasks.stream()
                    .map(t -> t.getId() + "|" + t.getDescription() + "|" + t.isCompleted())
                    .toList();
            Files.write(filePath, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write tasks file", e);
        }
    }
}