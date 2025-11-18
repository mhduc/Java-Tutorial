package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Expense;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseRepository {
    private static final String EXPENSES_FILE = "expenses.json";
    private final ObjectMapper objectMapper;

    public ExpenseRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<Expense> findAll() throws IOException {
        File file = new File(EXPENSES_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<Expense>>() {});
    }

    public Optional<Expense> findById(int id) throws IOException {
        List<Expense> expenses = findAll();
        return expenses.stream().filter(e -> e.getId() == id).findFirst();
    }

    public Expense save(Expense expense) throws IOException {
        List<Expense> expenses = findAll();
        if (expense.getId() == 0) {
            int maxId = expenses.stream().mapToInt(Expense::getId).max().orElse(0);
            expense.setId(maxId + 1);
        }
        expenses.removeIf(e -> e.getId() == expense.getId());
        expenses.add(expense);
        objectMapper.writeValue(new File(EXPENSES_FILE), expenses);
        return expense;
    }

    public boolean deleteById(int id) throws IOException {
        List<Expense> expenses = findAll();
        boolean removed = expenses.removeIf(e -> e.getId() == id);
        if (removed) {
            objectMapper.writeValue(new File(EXPENSES_FILE), expenses);
        }
        return removed;
    }
}