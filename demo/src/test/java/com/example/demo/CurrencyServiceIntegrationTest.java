package com.example.demo;

import com.example.demo.DTOs.LatestDto;
import com.example.demo.Service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CurrencyServiceIntegrationTest {

    @Autowired
    private CurrencyService currencyService;

    @Test
    public void testConversionCaching() {
        // Call the cached method twice
        double result1 = currencyService.getConversion("apiUrl");
        double result2 = currencyService.getConversion("apiUrl");

        // Assert that the results are the same
        assertEquals(result1, result2);
    }

    @Test
    public void testComparisonCaching() {
        // Call the cached method twice
        LatestDto result1 = currencyService.getComparisonRates("apiUrl");
        LatestDto result2 = currencyService.getComparisonRates("apiUrl");

        // Assert that the results are the same
        assertEquals(result1, result2);
    }

    @Test
    public void testImageCaching() {
        // Call the cached method twice
        Object result1 = currencyService.getImage("USD");
        Object result2 = currencyService.getImage("USD");

        // Assert that the results are the same
        assertEquals(result1, result2);
    }
}
