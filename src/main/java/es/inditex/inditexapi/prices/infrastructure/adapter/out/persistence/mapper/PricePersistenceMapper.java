package es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.mapper;

import es.inditex.inditexapi.prices.domain.model.Brand;
import es.inditex.inditexapi.prices.domain.model.Price;
import es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.entity.BrandEntity;
import es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PricePersistenceMapper {

    Price toDomain(PriceEntity entity);
    Brand brandEntityToBrand(BrandEntity brandEntity);
}
