package org.ebudoskyi.houserent.service.CurrencyRatesApiServices;

import org.ebudoskyi.houserent.Configuration.CurrencyRateApiConfig;
import org.ebudoskyi.houserent.model.CurrencyRates;
import org.ebudoskyi.houserent.repository.CurrencyRatesRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.*;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyRatesService {
    private final CurrencyRatesRepository currencyRatesRepository;
    private  final static OkHttpClient client = new OkHttpClient();
    private final CurrencyRateApiConfig config;

    @Autowired
    public CurrencyRatesService(CurrencyRatesRepository currencyRatesRepository, CurrencyRateApiConfig config) {
        this.currencyRatesRepository = currencyRatesRepository;
        this.config = config;
    }

    public CurrencyRates getCurrencyRates(String currency) {
        return currencyRatesRepository
                .findByBaseCurrency(currency)
                .orElseThrow(() -> new RuntimeException("There is no currency rates in DB!"));
    }

    public void updateRatesFromApi(){
        String requestUrl = config.getUrl() + config.getKey() + "/latest/UAH";

        Request request = new Request.Builder().url(requestUrl).build();

        try (Response response = client.newCall(request).execute()){
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ObjectMapper mapper = new ObjectMapper();
            String res = response.body().string(); // it immediately closes connection
            APIResults result = mapper.readValue(res, APIResults.class);

            CurrencyRates currencyRates = getCurrencyRates(result);

            currencyRatesRepository.save(currencyRates);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private CurrencyRates getCurrencyRates(APIResults result) {
        CurrencyRates currencyRates = new CurrencyRates();
        HashMap<String, Double> rates = new HashMap<>();
        rates.put("USD", result.getConversion_rates().getUSD());
        rates.put("EUR", result.getConversion_rates().getEUR());
        rates.put("GBP", result.getConversion_rates().getGBP());
        rates.put("CHF", result.getConversion_rates().getCHF());
        Map<String, Double> adjustedRates = rates.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Math.round(1/(entry.getValue()) * 100.0) / 100.0
                ));
        currencyRates.setBaseCurrency(result.getBase_code());
        currencyRates.setUSD(adjustedRates.get("USD"));
        currencyRates.setEUR(adjustedRates.get("EUR"));
        currencyRates.setGBP(adjustedRates.get("GBP"));
        currencyRates.setCHF(adjustedRates.get("CHF"));
        return currencyRates;
    }
}
