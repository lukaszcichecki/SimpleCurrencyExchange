package com.comak.sce.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long accountId;

    @NotBlank(message = "Account owner first name is mandatory !")
    private String ownerFirstName;

    @NotBlank(message = "Account owner last name is mandatory !")
    private String ownerLastName;


    @NotNull(message = "Initial account balance is mandatory !")
    @DecimalMin(message = "Initial account balance must be min 1 !", value = "1.0")
    @Digits(integer=9, fraction=2, message = "Initial account balance must have max 9 digits and max 2 decimal places")
    private BigDecimal accountBalance;

    private String currentCurrencyCode;
}
