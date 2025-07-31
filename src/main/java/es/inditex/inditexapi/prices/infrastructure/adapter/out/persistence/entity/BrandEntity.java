package es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BRAND")
public class BrandEntity {

    @Id
    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "BRAND_NAME")
    private String brandName;
}