package com.example.post.controllers.implementation;

import com.example.post.controllers.api.PackageApi;
import com.example.post.models.PackageDeliveryDto;
import com.example.post.models.PostPackage;
import com.example.post.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PackageController implements PackageApi {
    private final PackageService service;

    @Autowired
    public PackageController(PackageService service) {
        this.service = service;
    }


    @Override
    public ResponseEntity<PostPackage> getPackageInfoById(long packageId) {
        return ResponseEntity.ok(service.getPackageInfo(packageId));
    }

    @Override
    public ResponseEntity<PackageDeliveryDto> getDeliveryStatusByPackageId(long packageId) {
        return ResponseEntity.ok(service.getDeliveryStatus(packageId));
    }

    @Override
    public ResponseEntity<List<PackageDeliveryDto>> getDeliveryHistoryByPackageId(long packageId) {
        return ResponseEntity.ok(service.getPackageDeliveryHistory(packageId));
    }
}
