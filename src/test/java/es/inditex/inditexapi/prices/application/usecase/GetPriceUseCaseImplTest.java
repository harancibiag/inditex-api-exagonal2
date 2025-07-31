package es.inditex.inditexapi.prices.application.usecase;

import es.inditex.inditexapi.prices.application.dto.PriceResponse;
import es.inditex.inditexapi.prices.application.mapper.PriceMapper;
import es.inditex.inditexapi.prices.application.port.out.PriceRepository;
import es.inditex.inditexapi.prices.domain.model.Brand;
import es.inditex.inditexapi.prices.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetPriceUseCase Unit Tests")
class GetPriceUseCaseImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private GetPriceUseCaseImpl getPriceUseCase;

    private LocalDateTime testDate;
    private Long testProductId;
    private Long testBrandId;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.parse("2020-06-14T16:00:00");
        testProductId = 35455L;
        testBrandId = 1L;
    }

    @Test
    @DisplayName("Debe devolver el precio con la prioridad más alta cuando existen múltiples candidatos")
    void shouldReturnHighestPriorityPrice_whenMultiplePricesApply() {
        Brand brand = new Brand(testBrandId, "ZARA");
        Price lowPriorityPrice = new Price(1L, brand, testDate.minusHours(1), testDate.plusHours(1), testProductId, 0, new BigDecimal("35.50"), "EUR");
        Price highPriorityPrice = new Price(2L, brand, testDate.minusHours(2), testDate.plusHours(2), testProductId, 1, new BigDecimal("25.45"), "EUR"); // <- El ganador

        when(priceRepository.findApplicablePrices(testDate, testProductId, testBrandId))
                .thenReturn(List.of(lowPriorityPrice, highPriorityPrice));

        PriceResponse expectedResponse = PriceResponse.builder().priceAmount(new BigDecimal("25.45")).priceList(2L).build();
        when(priceMapper.toResponse(highPriorityPrice)).thenReturn(expectedResponse);

        Optional<PriceResponse> result = getPriceUseCase.findApplicablePrice(testDate, testProductId, testBrandId);

        assertThat(result).isPresent();
        assertThat(result.get().getPriceList()).isEqualTo(2L);
        assertThat(result.get().getPriceAmount()).isEqualTo(new BigDecimal("25.45"));

        verify(priceRepository, times(1)).findApplicablePrices(testDate, testProductId, testBrandId);
        verify(priceMapper, times(1)).toResponse(highPriorityPrice);
    }

    @Test
    @DisplayName("Debe devolver un Optional vacío cuando no se encuentran precios")
    void shouldReturnEmptyOptional_whenNoPricesFound() {
        when(priceRepository.findApplicablePrices(testDate, testProductId, testBrandId))
                .thenReturn(Collections.emptyList());

        Optional<PriceResponse> result = getPriceUseCase.findApplicablePrice(testDate, testProductId, testBrandId);
        assertThat(result).isNotPresent();
        verify(priceMapper, never()).toResponse(any(Price.class));
    }
}