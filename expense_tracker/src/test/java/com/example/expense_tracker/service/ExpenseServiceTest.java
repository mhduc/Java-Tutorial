package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.CreateExpenseDto;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.repository.ExpenseRepository;
import com.example.expense_tracker.exception.ExpenseOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository repository;

    @InjectMocks
    private ExpenseService service;

    @BeforeEach
    void setup() {
    }

    @Test
    void createExpense_validInput_savesAndReturns() throws IOException {
        CreateExpenseDto dto = new CreateExpenseDto("Food", 100.0, LocalDate.now());
        Expense saved = new Expense(1, dto.getDescription(), dto.getAmount(), dto.getDate());
        when(repository.save(any(Expense.class))).thenReturn(saved);

        Expense result = service.createExpense(dto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).save(any(Expense.class));
    }

    @Test
    void createExpense_negativeAmount_throwsOperationException() {
        CreateExpenseDto dto = new CreateExpenseDto("Food", -10.0, LocalDate.now());
        ExpenseOperationException ex = assertThrows(ExpenseOperationException.class, () -> service.createExpense(dto));
        assertTrue(ex.getMessage().contains("non-negative"));
        verifyNoInteractions(repository);
    }

    @Test
    void getTotalAmount_sumsAll() throws IOException {
        when(repository.findAll()).thenReturn(List.of(
                new Expense(1, "A", 10.0, LocalDate.now()),
                new Expense(2, "B", 5.5, LocalDate.now())
        ));

        double total = service.getTotalAmount();
        assertEquals(15.5, total, 0.0001);
    }

    @Test
    void getMonthlyTotal_filtersCurrentMonth() throws IOException {
        int year = LocalDate.now().getYear();
        when(repository.findAll()).thenReturn(List.of(
                new Expense(1, "A", 10.0, LocalDate.of(year, 1, 10)),
                new Expense(2, "B", 5.0, LocalDate.of(year, 2, 10)),
                new Expense(3, "C", 7.0, LocalDate.of(year - 1, 1, 10))
        ));

        double jan = service.getMonthlyTotal(1);
        double feb = service.getMonthlyTotal(2);
        assertEquals(10.0, jan, 0.0001);
        assertEquals(5.0, feb, 0.0001);
    }
}