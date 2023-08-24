package com.example.demo.Service;

import com.example.demo.DTOs.ConversionDto;

import com.example.demo.DTOs.ImageDto;
import com.example.demo.DTOs.LatestDto;
import com.example.demo.Enums.Currencies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final RestTemplate restTemplate;

    public double getConversion(String apiUrl) {
        // Call the API using RestTemplate and retrieve the response
        ConversionDto response = restTemplate.getForObject(apiUrl, ConversionDto.class);
        assert response != null;
        Double responseAmount = Double.parseDouble(response.getAmount());
        Double conversionRate = Double.parseDouble(response.getConversionRate());

        // Calculate the converted amount

        return responseAmount * conversionRate;
    }

    public LatestDto getComparisonRates(String apiUrl) {
        return restTemplate.getForObject(apiUrl, LatestDto.class);
    }

    public List<ImageDto> getAllCurrencyImages() {
        List<ImageDto> currencyImages = new ArrayList<>();

        for (Currencies currencyEnum : Currencies.values()) {
            currencyImages.add(new ImageDto(currencyEnum.getCurrency(), currencyEnum.getFlagUrl()));
        }

        return currencyImages;
    }


}

