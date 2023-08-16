package com.example.post.controllers;

import com.example.post.models.PostOffice;
import com.example.post.service.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/office")
public class PostOfficeController {
    private final PostOfficeService service;

    @Autowired
    public PostOfficeController(PostOfficeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> createPostOffice(@RequestBody PostOffice office) {
        return ResponseEntity.ok(service.createOffice(office));
    }

    @GetMapping("/{officeId}")
    public ResponseEntity<PostOffice> getPostOfficeServiceById(@PathVariable("officeId") long officeId) {
        return ResponseEntity.ok(service.getOfficeById(officeId));
    }
}
