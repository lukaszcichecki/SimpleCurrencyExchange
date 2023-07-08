package com.comak.sce.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter @NoArgsConstructor
public class ExchangeData {
    private String table;
    private String currency;
    private String code;
    private ArrayList<ExchangeRateData> rates;
}
