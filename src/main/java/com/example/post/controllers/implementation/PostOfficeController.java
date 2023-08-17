package com.example.post.controllers.implementation;

import com.example.post.controllers.api.PostOfficeApi;
import com.example.post.models.PostOffice;
import com.example.post.service.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostOfficeController implements PostOfficeApi {
    private final PostOfficeService service;

    @Autowired
    public PostOfficeController(PostOfficeService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<Long> createPostOffice(PostOffice office) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPostOffice(office));
    }

    @Override
    public ResponseEntity<PostOffice> getPostOfficeById(long officeId) {
        return ResponseEntity.ok(service.getPostOfficeByIndex(officeId));
    }
}
