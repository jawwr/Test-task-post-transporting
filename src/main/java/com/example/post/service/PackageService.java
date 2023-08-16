package com.example.post.service;

import com.example.post.models.PackageDelivery;
import com.example.post.models.PackageDeliveryStatus;
import com.example.post.models.PostPackage;

import java.util.List;

public interface PackageService {
    void register(long postOffice, PostPackage postPackage);

    void arrive(long postOfficeIndex, long packageId);

    void departPackage(long postOffice, long packageId);

    void receivePackage(long postOffice, long packageId);

    PackageDeliveryStatus getDeliveryStatus(long packageId);

    List<PackageDelivery> getPackageDeliveryHistory(long packageId);

    PostPackage getPackageInfo(long packageId);
}
