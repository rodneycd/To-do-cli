package com.rodney.to_do.cli;

import com.rodney.to_do.model.Task;
import com.rodney.to_do.service.TaskService;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ToDoCLIRunner implements CommandLineRunner {

    private final TaskService taskService;

    public ToDoCLIRunner(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String @NonNull ... args) {
        System.out.println("To-Do CLI — type a command (or 'help', 'exit')");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine();
            String[] input = line.trim().split("\\s+");

            if (input.length == 0 || input[0].isBlank()) {
                continue;
            }

            String command = input[0];

            if (command.equals("exit") || command.equals("quit")) {
                System.out.println("Bye!");
                break;
            }

            switch (command) {
                case "add" -> handleAdd(input);
                case "list" -> handleList();
                case "complete" -> handleComplete(input);
                case "help" -> printUsage();
                default -> {
                    System.out.println("Unknown command: " + command);
                    printUsage();
                }
            }
        }
        scanner.close();
    }

    private void handleAdd(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: add <description>");
            return;
        }
        String description = String.join(" ", java.util.Arrays.asList(args).subList(1, args.length));
        Task created = taskService.addTask(description);
        System.out.println("Added task [" + created.getId() + "]: " + created.getDescription());
    }

    private void handleList() {
        List<Task> tasks = taskService.listTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }
        for (Task task : tasks) {
            String status = task.isCompleted() ? "x" : " ";
            System.out.println("[" + status + "] " + task.getId() + ": " + task.getDescription());
        }
    }

    private void handleComplete(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: complete <id>");
            return;
        }
        try {
            Long id = Long.parseLong(args[1]);
            boolean success = taskService.completeTask(id);
            if (success) {
                System.out.println("Completed task " + id);
            } else {
                System.out.println("No task found with id " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid id: " + args[1]);
        }
    }

    private void printUsage() {
        System.out.println("Commands:");
        System.out.println("  add <description>");
        System.out.println("  list");
        System.out.println("  complete <id>");
        System.out.println("  exit");
    }
}