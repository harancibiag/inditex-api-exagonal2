package es.inditex.inditexapi.prices.application.port.in;

import es.inditex.inditexapi.prices.application.dto.PriceResponse;
import java.time.LocalDateTime;
import java.util.Optional;

public interface GetPriceUseCase {
    Optional<PriceResponse> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
