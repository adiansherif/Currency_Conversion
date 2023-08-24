package com.example.demo.Entity;

import lombok.Data;

@Data

public class CurrencyData {
    private  String name;
    private  String symbol;
    private  String country;

    public CurrencyData(String name, String symbol, String country) {
        this.name = name;
        this.symbol = symbol;
        this.country = country;
    }
}
