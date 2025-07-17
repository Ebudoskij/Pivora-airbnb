package org.ebudoskyi.houserent.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "currency_data")
@Getter
@Setter
@NoArgsConstructor

public class CurrencyRates {
    @Id
    private Long id = 1L;

    @NotNull
    @Column(nullable = false)
    private String baseCurrency;

    @NotNull
    @Column(nullable = false)
    private double USD;

    @NotNull
    @Column(nullable = false)
    private double EUR;

    @NotNull
    @Column(nullable = false)
    private double GBP;

    @NotNull
    @Column(nullable = false)
    private double CHF;
}
