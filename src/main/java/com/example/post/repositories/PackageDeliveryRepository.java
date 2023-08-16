package com.example.post.repositories;

import com.example.post.models.PackageDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageDeliveryRepository extends JpaRepository<PackageDelivery, Long> {
    List<PackageDelivery> findAllByPostPackageId(long packageId);

    PackageDelivery findTopByPostPackageId(long packageId);
}
