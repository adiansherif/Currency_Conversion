package com.example.demo.Service;

import com.example.demo.DTOs.ConversionDto;

import com.example.demo.DTOs.ImageDto;
import com.example.demo.DTOs.LatestDto;
import com.example.demo.Enums.Currencies;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    public String getConversion(String apiUrl) {
        try {
            ConversionDto response = restTemplate.getForObject(apiUrl, ConversionDto.class);
            assert response != null;
            Double responseAmount = Double.parseDouble(response.getAmount());
            Double conversionRate = Double.parseDouble(response.getConversionRate());

            // Calculate the converted amount
            Double convertedAmount = responseAmount * conversionRate;

            // Convert the result to JSON
            return convertToJson(convertedAmount);
        } catch (Exception e) {
            logger.error("Error occurred while fetching conversion: {}", e.getMessage());
            return "{\"error\": \"An error occurred while fetching conversion.\"}";
        }
    }

    private String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while serializing to JSON: {}", e.getMessage());
            return "{\"error\": \"An error occurred while serializing to JSON.\"}";
        }
    }
    

    public LatestDto getComparisonRates(String apiUrl) {
        try {
            return restTemplate.getForObject(apiUrl, LatestDto.class);
        }catch(Exception e){
            logger.error("Error occurred while fetching comparison rates: {}", e.getMessage());
            return new LatestDto();
        }
    }

    public List<ImageDto> getAllCurrencyImages() {
        List<ImageDto> currencyImages = new ArrayList<>();

        for (Currencies currencyEnum : Currencies.values()) {
            currencyImages.add(new ImageDto(currencyEnum.getCurrency(), currencyEnum.getFlagUrl()));
        }

        return currencyImages;
    }

    public Object getImage(String countryCode){
        for (Currencies currencyEnum : Currencies.values()) {
            if (currencyEnum.getCurrency().equalsIgnoreCase(countryCode)) {
                return new ImageDto(currencyEnum.getCurrency(), currencyEnum.getFlagUrl());
            }
        }
        return "Currency not found";

    }


}

