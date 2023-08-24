package com.example.demo.Mappers;

import com.example.demo.Entity.CurrencyData;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CurrencyDataLoader {
    public static void main(String[] args) throws IOException {

        // Replace with the actual API endpoint
        String apiUrl = "https://api.currencyfreaks.com/v2.0/supported-currencies";

        ObjectMapper objectMapper = new ObjectMapper();
        CurrencyData[] currencyDataArray = objectMapper.readValue(new URL(apiUrl), CurrencyData[].class);

        for (CurrencyData currencyData : currencyDataArray) {
            Currencies.addCurrency(currencyData.getName(), currencyData.getSymbol(), currencyData.getCountry());
        }
    }
}

