package es.inditex.inditexapi.prices.infrastructure.adapter.in.web;

import es.inditex.inditexapi.prices.application.dto.PriceResponse;
import es.inditex.inditexapi.prices.application.port.in.GetPriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
@Tag(name = "2. Prices", description = "Endpoint para la consulta de precios.")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    @Operation(summary = "Obtener precio aplicable", description = "Devuelve el precio activo para un producto, cadena y fecha específicos, aplicando la prioridad más alta.")
    @GetMapping("/query")
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @Parameter(description = "Fecha de aplicación (formato: yyyy-MM-dd HH:mm:ss)", example = "2020-06-14 10:00:00")
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime applicationDate,

            @Parameter(description = "ID del producto", example = "35455")
            @RequestParam Long productId,

            @Parameter(description = "ID de la cadena", example = "1")
            @RequestParam Long brandId) {

        return getPriceUseCase.findApplicablePrice(applicationDate, productId, brandId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}