package com.example.post.service;

import com.example.post.models.PostOffice;

public interface PostOfficeService {
    long createOffice(PostOffice office);

    PostOffice getOfficeById(long postOfficeId);
}
