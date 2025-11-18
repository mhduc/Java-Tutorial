package com.example.task_tracker;

import com.example.task_tracker.controller.TaskController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TaskTrackerApplication {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: java -jar task_tracker.jar <command> [options]");
			System.err.println("Commands:");
			System.err.println("  add <description>              Add a new task");
			System.err.println("  update <id> <description>      Update a task");
			System.err.println("  delete <id>                    Delete a task");
			System.err.println("  mark-in-progress <id>          Mark a task as in progress");
			System.err.println("  mark-done <id>                 Mark a task as done");
			System.err.println("  list                           List all tasks");
			System.err.println("  list done                      List done tasks");
			System.err.println("  list todo                      List todo tasks");
			System.err.println("  list in-progress               List in-progress tasks");
			return;
		}

		ConfigurableApplicationContext context = SpringApplication.run(TaskTrackerApplication.class, args);
		TaskController taskController = context.getBean(TaskController.class);

		try {
			String command = args[0];
			
			switch (command) {
				case "add":
					if (args.length < 2) {
						System.err.println("Usage: add <description>");
						break;
					}
					String description = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
					taskController.addTask(description);
					break;
					
				case "update":
					if (args.length < 3) {
						System.err.println("Usage: update <id> <description>");
						break;
					}
					int updateId = Integer.parseInt(args[1]);
					String updateDescription = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
					taskController.updateTask(updateId, updateDescription);
					break;
					
				case "delete":
					if (args.length < 2) {
						System.err.println("Usage: delete <id>");
						break;
					}
					int deleteId = Integer.parseInt(args[1]);
					taskController.deleteTask(deleteId);
					break;
					
				case "mark-in-progress":
					if (args.length < 2) {
						System.err.println("Usage: mark-in-progress <id>");
						break;
					}
					int inProgressId = Integer.parseInt(args[1]);
					taskController.markInProgress(inProgressId);
					break;
					
				case "mark-done":
					if (args.length < 2) {
						System.err.println("Usage: mark-done <id>");
						break;
					}
					int doneId = Integer.parseInt(args[1]);
					taskController.markDone(doneId);
					break;
					
				case "list":
					if (args.length > 1) {
						String status = args[1];
						switch (status) {
							case "done":
								taskController.listDoneTasks();
								break;
							case "todo":
								taskController.listTodoTasks();
								break;
							case "in-progress":
								taskController.listInProgressTasks();
								break;
							default:
								System.err.println("Invalid status. Use: done, todo, in-progress");
						}
					} else {
						taskController.listAllTasks();
					}
					break;
					
				default:
					System.err.println("Unknown command: " + command);
					System.err.println("Use --help to see available commands");
			}
		} catch (NumberFormatException e) {
			System.err.println("Invalid number format. Please provide a valid task ID.");
		} catch (Exception e) {
			System.err.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		} finally {
			context.close();
		}
	}
}