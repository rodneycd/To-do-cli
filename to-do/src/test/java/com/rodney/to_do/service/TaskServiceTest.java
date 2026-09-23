package com.rodney.to_do.service;

import com.rodney.to_do.model.Task;
import com.rodney.to_do.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Test
    void addTask_createsNewTaskWithCompletedFalse() {
        TaskService taskService = new TaskService(taskRepository);

        when(taskRepository.create(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.addTask("Buy milk");

        assertEquals("Buy milk", result.getDescription());
        assertFalse(result.isCompleted());
        verify(taskRepository).create(any(Task.class));
    }

    @Test
    void completeTask_marksExistingTaskCompleted() {
        TaskService taskService = new TaskService(taskRepository);
        Task existing = new Task(1L, "Buy milk", false);

        when(taskRepository.getTaskByID(1L)).thenReturn(Optional.of(existing));

        boolean result = taskService.completeTask(1L);

        assertTrue(result);
        assertTrue(existing.isCompleted());
        verify(taskRepository).update(existing);
    }

    @Test
    void completeTask_returnsFalseWhenTaskNotFound() {
        TaskService taskService = new TaskService(taskRepository);

        when(taskRepository.getTaskByID(99L)).thenReturn(Optional.empty());

        boolean result = taskService.completeTask(99L);

        assertFalse(result);
        verify(taskRepository, never()).update(any());
    }

    @Test
    void listTasks_returnsWhatRepositoryProvides() {
        TaskService taskService = new TaskService(taskRepository);
        List<Task> expected = List.of(new Task(1L, "Buy milk", false));

        when(taskRepository.getTasks()).thenReturn(expected);

        List<Task> result = taskService.listTasks();

        assertEquals(expected, result);
    }
}