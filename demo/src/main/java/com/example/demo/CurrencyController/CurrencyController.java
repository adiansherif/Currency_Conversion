package com.example.demo.CurrencyController;

import com.example.demo.DTOs.ImageDto;
import com.example.demo.DTOs.LatestDto;
import com.example.demo.Service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/currency")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/convert")
    public ResponseEntity<Double> convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam Double amount) {
        try {
            String apiUrl = "https://currencyexchange-wbtr.onrender.com/pair/"+fromCurrency+"/" + toCurrency + "/" + amount;
            double convertedAmount = currencyService.getConversion(apiUrl);
            return ResponseEntity.ok(convertedAmount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(0.0);
        }
    }

    @GetMapping("/compare")
    public ResponseEntity<LatestDto> compareWithBase(
            @RequestParam String base
    ) {
        try {
            String apiUrl = "https://currencyexchange-wbtr.onrender.com/latest/" + base;
            LatestDto response = currencyService.getComparisonRates(apiUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LatestDto()); // Handle the error and return an appropriate response
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ImageDto>> retrieveImages(){
        try {
            List<ImageDto> currencyImages = currencyService.getAllCurrencyImages();
            return ResponseEntity.ok(currencyImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
