package org.ebudoskyi.houserent.service.CurrencyRatesApiServices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class ConversionRates {
    @JsonProperty("USD")
    private double USD;
    @JsonProperty("EUR")
    private double EUR;
    @JsonProperty("GBP")
    private double GBP;
    @JsonProperty("CHF")
    private double CHF;
}
