package io.stitch.stitch.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class CurrencyScheduler {

    private final WebClient client = WebClient.create("https://open.er-api.com/v6");
    public Double usdToEgp(Double priceUSD) {
        Map response = client.get()
                .uri("/latest/USD")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map rates = (Map) response.get("rates");
        Double egp = Double.valueOf(rates.get("EGP").toString()) * priceUSD;
        egp = Math.round(egp * 10.0) / 10.0;
        return egp;
    }
}
