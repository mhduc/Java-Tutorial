package com.example.expense_tracker.service;

import com.example.expense_tracker.exception.ExpenseOperationException;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import com.example.expense_tracker.dto.CreateExpenseDto;
import com.example.expense_tracker.dto.UpdateExpenseDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense(CreateExpenseDto dto) throws ExpenseOperationException {
        try {
            if (dto.getDescription() == null || dto.getDescription().isBlank()) {
                throw new ExpenseOperationException("Description must not be blank", null);
            }
            if (dto.getAmount() < 0) {
                throw new ExpenseOperationException("Amount must be non-negative", null);
            }
            double amount = dto.getAmount();
            Expense expense = new Expense(0, dto.getDescription(), amount, dto.getDate() != null ? dto.getDate() : LocalDate.now());
            return expenseRepository.save(expense);
        } catch (IOException e) {
            throw new ExpenseOperationException("Failed to create expense", e);
        }
    }

    public Optional<Expense> getExpenseById(int id) throws ExpenseOperationException {
        try {
            return expenseRepository.findById(id);
        } catch (IOException e) {
            throw new ExpenseOperationException("Failed to retrieve expense with ID: " + id, e);
        }
    }

    public List<Expense> getAllExpenses() throws ExpenseOperationException {
        try {
            return expenseRepository.findAll();
        } catch (IOException e) {
            throw new ExpenseOperationException("Failed to retrieve expenses", e);
        }
    }

    public Optional<Expense> updateExpense(int id, UpdateExpenseDto dto) throws ExpenseOperationException {
        try {
            Optional<Expense> existing = expenseRepository.findById(id);
            if (existing.isEmpty()) {
                return Optional.empty();
            }
            Expense expense = existing.get();
            if (dto.getDescription() != null) {
                expense.setDescription(dto.getDescription());
            }
            if (dto.getAmount() != null) {
                expense.setAmount(dto.getAmount());
            }
            if (dto.getDate() != null) {
                expense.setDate(dto.getDate());
            }
            return Optional.of(expenseRepository.save(expense));
        } catch (IOException e) {
            throw new ExpenseOperationException("Failed to update expense with ID: " + id, e);
        }
    }

    public boolean deleteExpense(int id) throws ExpenseOperationException {
        try {
            return expenseRepository.deleteById(id);
        } catch (IOException e) {
            throw new ExpenseOperationException("Failed to delete expense with ID: " + id, e);
        }
    }

    public double getTotalAmount() throws ExpenseOperationException {
        return getAllExpenses().stream().mapToDouble(Expense::getAmount).sum();
    }

    public double getMonthlyTotal(int month) throws ExpenseOperationException {
        int year = LocalDate.now().getYear();
        return getAllExpenses().stream()
                .filter(e -> e.getDate() != null && e.getDate().getYear() == year && e.getDate().getMonthValue() == month)
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}