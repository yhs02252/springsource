package com.example.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcDTO {
    private int num1;
    private int num2;
    private String op;
}
