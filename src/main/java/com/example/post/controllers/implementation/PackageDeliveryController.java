package com.example.post.controllers.implementation;

import com.example.post.controllers.api.PackageDeliveryApi;
import com.example.post.models.PackageDeliveryStatus;
import com.example.post.models.PostPackage;
import com.example.post.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PackageDeliveryController implements PackageDeliveryApi {
    private final PackageService service;

    @Autowired
    public PackageDeliveryController(PackageService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<?> registerPackage(long postOffice,
                                             PostPackage postPackage) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(postOffice, postPackage));
    }

    @Override
    public ResponseEntity<?> updateStatus(long postOfficeId,
                                          long packageId,
                                          PackageDeliveryStatus status) {
        service.updateDeliveryStatus(postOfficeId, packageId, status);
        return ResponseEntity.ok().build();
    }
}
