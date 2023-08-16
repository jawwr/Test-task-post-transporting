package com.example.post.controllers;

import com.example.post.models.PackageDeliveryStatus;
import com.example.post.models.PostPackage;
import com.example.post.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/office/{postOffice}/packages")
public class PackageDeliveryController {
    private final PackageService service;

    @Autowired
    public PackageDeliveryController(PackageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> registerPackage(@PathVariable("postOffice") long postOffice,
                                             @RequestBody PostPackage postPackage) {
        service.register(postOffice, postPackage);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{packageId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable("postOffice") long postOffice,
                                    @PathVariable("packageId") long packageId,
                                    @RequestParam(value = "status", required = true) PackageDeliveryStatus status) {
        service.updateDeliveryStatus(postOffice, packageId, status);
        return ResponseEntity.ok().build();
    }
}
