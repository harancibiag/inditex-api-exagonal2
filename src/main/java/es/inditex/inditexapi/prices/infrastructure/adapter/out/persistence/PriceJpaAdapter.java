package es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence;

import es.inditex.inditexapi.prices.application.port.out.PriceRepository;
import es.inditex.inditexapi.prices.domain.model.Price;
import es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.entity.PriceEntity;
import es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.mapper.PricePersistenceMapper;
import es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.repository.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PriceJpaAdapter implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    private final PricePersistenceMapper persistenceMapper;

    @Override
    public List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId) {
        List<PriceEntity> priceEntities = priceJpaRepository.findApplicablePrices(applicationDate, productId, brandId);
        return priceEntities.stream()
                .map(persistenceMapper::toDomain)
                .collect(Collectors.toList());
    }
}