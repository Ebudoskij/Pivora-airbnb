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
public class APIResults {
    @JsonProperty("result")
    private String result;
    @JsonProperty("base_code")
    private String base_code;
    @JsonProperty("conversion_rates")
    private ConversionRates conversion_rates;
}
