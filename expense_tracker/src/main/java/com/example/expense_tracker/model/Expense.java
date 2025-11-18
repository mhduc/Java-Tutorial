package com.example.expense_tracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    private int id;
    private String description;
    private double amount;
    private LocalDate date;
}