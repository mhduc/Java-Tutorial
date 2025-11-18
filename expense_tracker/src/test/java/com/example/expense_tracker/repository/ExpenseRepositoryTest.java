package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.Expense;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseRepositoryTest {

    private ExpenseRepository repository;
    private File testFile;

    @BeforeEach
    void setup() {
        String path = "target/test-data/expenses-test.json";
        testFile = new File(path);
        if (testFile.exists()) {
            testFile.delete();
        }
        repository = new ExpenseRepository(path);
    }

    @AfterEach
    void cleanup() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void saveAndFindAll_persistsData() throws IOException {
        Expense e1 = new Expense(0, "Food", 10.0, LocalDate.now());
        Expense e2 = new Expense(0, "Transport", 5.0, LocalDate.now());

        Expense s1 = repository.save(e1);
        Expense s2 = repository.save(e2);

        assertTrue(s1.getId() > 0);
        assertTrue(s2.getId() > 0);

        List<Expense> all = repository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void findById_returnsExpenseWhenExists() throws IOException {
        Expense e1 = new Expense(0, "Food", 10.0, LocalDate.now());
        Expense saved = repository.save(e1);

        Optional<Expense> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Food", found.get().getDescription());
    }

    @Test
    void deleteById_removesExpense() throws IOException {
        Expense e1 = new Expense(0, "Food", 10.0, LocalDate.now());
        Expense saved = repository.save(e1);

        boolean deleted = repository.deleteById(saved.getId());
        assertTrue(deleted);
        assertTrue(repository.findAll().isEmpty());
    }
}