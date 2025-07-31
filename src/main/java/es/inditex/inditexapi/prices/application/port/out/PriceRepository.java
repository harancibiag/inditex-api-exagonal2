package es.inditex.inditexapi.prices.application.port.out;

import es.inditex.inditexapi.prices.domain.model.Price;
import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId);
}