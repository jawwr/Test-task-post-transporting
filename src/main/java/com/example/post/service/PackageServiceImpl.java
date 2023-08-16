package com.example.post.service;

import com.example.post.models.PackageDelivery;
import com.example.post.models.PackageDeliveryStatus;
import com.example.post.models.PostPackage;
import com.example.post.repositories.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PackageServiceImpl implements PackageService {
    private final PackageRepository repository;

    @Override
    public void register(long postOffice, PostPackage postPackage) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void arrive(long postOfficeIndex, long packageId) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void departPackage(long postOffice, long packageId) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void receivePackage(long postOffice, long packageId) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public PackageDeliveryStatus getDeliveryStatus(long packageId) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<PackageDelivery> getPackageDeliveryHistory(long packageId) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public PostPackage getPackageInfo(long packageId) {
        throw new RuntimeException("Not implemented");
    }
}
