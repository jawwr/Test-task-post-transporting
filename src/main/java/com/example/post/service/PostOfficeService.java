package com.example.post.service;

import com.example.post.models.PostOffice;

public interface PostOfficeService {
    long createPostOffice(PostOffice office);

    PostOffice getPostOfficeByIndex(long postOfficeId);
}
