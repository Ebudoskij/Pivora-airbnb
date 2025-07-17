package org.ebudoskyi.houserent.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "currency.api")
@Getter
@Setter
public class CurrencyRateApiConfig {
    private String url;
    private String key;
}

