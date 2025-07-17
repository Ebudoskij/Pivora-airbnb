package org.ebudoskyi.houserent.service.CurrencyRatesApiServices;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyRatesScheduler {

    private final CurrencyRatesService currencyRatesService;

    @Autowired
    public CurrencyRatesScheduler(CurrencyRatesService currencyRatesService) {
        this.currencyRatesService = currencyRatesService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void scheduledCurrencyUpdate() {
        currencyRatesService.updateRatesFromApi();
    }
}
