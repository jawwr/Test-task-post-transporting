package com.example.post.repositories;

import com.example.post.models.PackageDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PackageDeliveryRepository extends JpaRepository<PackageDelivery, Long> {
    List<PackageDelivery> findAllByPostPackageId(long packageId);

    @Transactional
    @Query(value = """
            select *
            from delivery_history
            where post_package_id = :#{#packageId}
            order by 1 desc
            limit 1
            """, nativeQuery = true)
    PackageDelivery findLastMovement(long packageId);
}
