package com.example.expense_tracker;

import com.example.expense_tracker.controller.ExpenseController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class ExpenseTrackerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ExpenseTrackerApplication.class, args);
        ExpenseController controller = context.getBean(ExpenseController.class);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Expense Tracker");
            System.out.println("1. Add expense");
            System.out.println("2. Update expense");
            System.out.println("3. Delete expense");
            System.out.println("4. List all expenses");
            System.out.println("5. Total amount");
            System.out.println("6. Monthly total");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        System.out.print("Description: ");
                        String desc = scanner.nextLine();
                        if (desc == null || desc.isBlank()) {
                            System.out.println("Description must not be blank");
                            break;
                        }
                        System.out.print("Amount: ");
                        String amountStr = scanner.nextLine();
                        double amount;
                        try {
                            amount = Double.parseDouble(amountStr);
                        } catch (NumberFormatException ex) {
                            System.out.println("Invalid number format");
                            break;
                        }
                        if (amount < 0) {
                            System.out.println("Amount must be non-negative");
                            break;
                        }
                        controller.addExpense(desc, amount);
                        break;
                    case "2":
                        System.out.print("ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("New description (blank to skip): ");
                        String newDesc = scanner.nextLine();
                        System.out.print("New amount (blank to skip): ");
                        String amtStr = scanner.nextLine();
                        Double newAmount = amtStr.isBlank() ? null : Double.parseDouble(amtStr);
                        controller.updateExpense(id, newDesc.isBlank() ? null : newDesc, newAmount);
                        break;
                    case "3":
                        System.out.print("ID: ");
                        int delId = Integer.parseInt(scanner.nextLine());
                        controller.deleteExpense(delId);
                        break;
                    case "4":
                        controller.listAllExpenses();
                        break;
                    case "5":
                        controller.printTotalAmount();
                        break;
                    case "6":
                        System.out.print("Month (1-12): ");
                        String monthStr = scanner.nextLine();
                        int month;
                        try {
                            month = Integer.parseInt(monthStr);
                        } catch (NumberFormatException ex) {
                            System.out.println("Invalid number format");
                            break;
                        }
                        if (month < 1 || month > 12) {
                            System.out.println("Month must be between 1 and 12");
                            break;
                        }
                        controller.printMonthlyTotal(month);
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
        context.close();
    }
}