package io.stitch.stitch.service;

import io.stitch.stitch.entity.Usd;
import io.stitch.stitch.repos.USDRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class CurrencyScheduler {
    private final USDRepository usdRepo;
    private final WebClient client = WebClient.create("https://open.er-api.com/v6");

    public CurrencyScheduler(USDRepository usdRepo) {
        this.usdRepo = usdRepo;
    }
    @Scheduled(fixedRate = 15000) // every 15 seconds
    public void usdToEgp() {
        Map response = client.get()
                .uri("/latest/USD")
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map rates = (Map) response.get("rates");
        Double egp = Double.valueOf(rates.get("EGP").toString());
        egp = Math.round(egp * 10.0) / 10.0;

        Usd usd = usdRepo.findById(1L).orElse(new Usd());
        if(Double.compare(usd.getValue(), egp) != 0) {
            usd.setValue(egp);
            usdRepo.save(usd);
        }

    }
}
