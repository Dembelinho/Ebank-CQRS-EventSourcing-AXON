package com.sdia.ebankcqrseventsourcingaxon.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor @Builder
public class CreateAccountRequestDTO {
    private double initialBalance;
    private String currency;
}
