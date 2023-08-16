package com.example.post.controllers;

import com.example.post.models.PackageDeliveryDto;
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
@RequestMapping("/api/v1/package")
public class PackageController {
    private final PackageService service;

    @Autowired
    public PackageController(PackageService service) {
        this.service = service;
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<PostPackage> getPackageInfo(@PathVariable("packageId") long packageId) {
        return ResponseEntity.ok(service.getPackageInfo(packageId));
    }

    @GetMapping("/{packageId}/status")
    public ResponseEntity<PackageDeliveryDto> getDeliveryStatus(@PathVariable("packageId") long packageId) {
        return ResponseEntity.ok(service.getDeliveryStatus(packageId));
    }

    @GetMapping("/{packageId}/history")
    public ResponseEntity<List<PackageDeliveryDto>> getDeliveryHistory(@PathVariable("packageId") long packageId) {
        return ResponseEntity.ok(service.getPackageDeliveryHistory(packageId));
    }
}
