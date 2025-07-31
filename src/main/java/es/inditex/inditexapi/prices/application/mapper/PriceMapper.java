package es.inditex.inditexapi.prices.application.mapper;

import es.inditex.inditexapi.prices.application.dto.PriceResponse;
import es.inditex.inditexapi.prices.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants; // <-- IMPORTANTE

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PriceMapper {

    @Mapping(source = "brand.brandId", target = "brandId")
    PriceResponse toResponse(Price price);
}