package es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRICES")

public class PriceEntity {

    @Id
    @Column(name = "PRICE_LIST")
    private Long priceList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "BRAND_ID")
    private BrandEntity brand;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private BigDecimal priceAmount;

    @Column(name = "CURR")
    private String curr;
}