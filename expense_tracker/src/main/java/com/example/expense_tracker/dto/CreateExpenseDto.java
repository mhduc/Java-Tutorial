package com.example.expense_tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseDto {
    private String description;
    private double amount;
    private LocalDate date;
}