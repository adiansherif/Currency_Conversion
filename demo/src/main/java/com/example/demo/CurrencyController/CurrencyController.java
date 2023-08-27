package com.example.demo.CurrencyController;

import com.example.demo.DTOs.ImageDto;
import com.example.demo.DTOs.LatestDto;
import com.example.demo.Service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/currency")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@EnableCaching
public class CurrencyController{

    private final CurrencyService currencyService;
    private final ObjectMapper objectMapper;

    @GetMapping("/convert")
    @Cacheable("conversion")
    public String convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam Double amount) {
        try {
            String apiUrl = "https://currencyexchange-wbtr.onrender.com/pair/" + fromCurrency + "/" + toCurrency + "/" + amount;
            return currencyService.getConversion(apiUrl);
        } catch (Exception e) {
            return convertToJson(new ErrorResponse("An error occurred while converting currency."));
        }
    }
    
    @GetMapping("/compare")
    @Cacheable("comparison")
    public LatestDto compareWithBase(
            @RequestParam String base
    ) {
        try {
            String apiUrl = "https://currencyexchange-wbtr.onrender.com/latest/" + base;
            return currencyService.getComparisonRates(apiUrl);
        } catch (Exception e) {
            return new LatestDto(); // Handle the error and return an appropriate response
        }
    }

    @GetMapping("/")
    @Cacheable("images")
    public List<ImageDto> retrieveImages(){
        try {
            return currencyService.getAllCurrencyImages();
        } catch (Exception e) {
            return null;
        }
    }
    @GetMapping("/image")
    @Cacheable("image")
    public Object retrieveOneImage(@RequestParam String countryCode){
        try {
            return currencyService.getImage(countryCode);
        } catch (Exception e) {
            return null;
        }
    }

    @CacheEvict(allEntries = true, value = {"conversion", "comparison", "images", "image"})
    @PostMapping("/clear-cache")
    public String clearCache() {
        return "Cache cleared.";
    }
}
private String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "{\"error\": \"An error occurred while serializing to JSON.\"}";
        }
    }

    private static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
