package com.example.expense_tracker.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExpenseDto {
    private String description;
    private Double amount;
    private LocalDate date;
}