package com.example.post.service;

import com.example.post.models.PackageDelivery;
import com.example.post.models.PackageDeliveryStatus;
import com.example.post.models.PostPackage;

import java.util.List;

public interface PackageService {
    long register(long postOfficeIndex, PostPackage postPackage);

    void updateDeliveryStatus(long postOfficeIndex, long packageId, PackageDeliveryStatus status);

    PackageDelivery getDeliveryStatus(long packageId);

    List<PackageDelivery> getPackageDeliveryHistory(long packageId);

    PostPackage getPackageInfo(long packageId);
}
