package es.inditex.inditexapi.prices.application.usecase;

import es.inditex.inditexapi.prices.application.dto.PriceResponse;
import es.inditex.inditexapi.prices.application.mapper.PriceMapper;
import es.inditex.inditexapi.prices.application.port.in.GetPriceUseCase;
import es.inditex.inditexapi.prices.application.port.out.PriceRepository;
import es.inditex.inditexapi.prices.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class GetPriceUseCaseImpl implements GetPriceUseCase {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public Optional<PriceResponse> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        List<Price> candidatePrices = priceRepository.findApplicablePrices(applicationDate, productId, brandId);

        return candidatePrices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .map(priceMapper::toResponse);
    }
}