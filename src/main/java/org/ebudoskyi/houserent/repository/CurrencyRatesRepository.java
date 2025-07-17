package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.CurrencyRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRatesRepository extends JpaRepository<CurrencyRates, Long> {
    @Query("SELECT c FROM CurrencyRates c WHERE c.baseCurrency = :currency")
    Optional<CurrencyRates> findByBaseCurrency(@Param("currency") String baseCurrency);
}
