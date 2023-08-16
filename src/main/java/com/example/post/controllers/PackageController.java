package com.example.post.controllers;

import com.example.post.models.PackageDelivery;
import com.example.post.models.PostPackage;
import com.example.post.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/package/{packageId}")
public class PackageController {
    private final PackageService service;

    @Autowired
    public PackageController(PackageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PostPackage> getPackageInfo(@PathVariable("packageId") long packageId) {
        return ResponseEntity.ok(service.getPackageInfo(packageId));
    }

    @GetMapping("/status")
    public ResponseEntity<PackageDelivery> getDeliveryStatus(@PathVariable("packageId") long packageId) {
        return ResponseEntity.ok(service.getDeliveryStatus(packageId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<PackageDelivery>> getDeliveryHistory(@PathVariable("packageId") long packageId) {
        return ResponseEntity.ok(service.getPackageDeliveryHistory(packageId));
    }
}
