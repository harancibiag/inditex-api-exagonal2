package es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.repository;

import es.inditex.inditexapi.prices.infrastructure.adapter.out.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p FROM PriceEntity p JOIN p.brand b WHERE b.brandId = :brandId AND p.productId = :productId AND :applicationDate BETWEEN p.startDate AND p.endDate")
    List<PriceEntity> findApplicablePrices(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId);
}