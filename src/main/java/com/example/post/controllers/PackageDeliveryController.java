package com.example.post.controllers;

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

    @PutMapping("/{packageId}")
    public ResponseEntity<?> arrive(@PathVariable("postOffice") long postOffice,
                                    @PathVariable("packageId") long packageId) {
        service.arrive(postOffice, packageId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{packageId}")
    public ResponseEntity<?> departPackage(@PathVariable("postOffice") long postOffice,
                                           @PathVariable("packageId") long packageId) {
        service.departPackage(postOffice, packageId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{packageId}")
    public ResponseEntity<?> receivePackage(@PathVariable("postOffice") long postOffice,
                                            @PathVariable("packageId") long packageId) {
        service.receivePackage(postOffice, packageId);
        return ResponseEntity.ok().build();
    }
}
