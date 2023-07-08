package com.comak.sce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency_exchange_operations")
@Data
public class CurrencyExchangeOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal exchangeRate;
    private LocalDateTime operationDatetime;
    private BigDecimal operationValue;
    private String operationCurrencyCode;
    private BigDecimal currentAccountBalance;
}
