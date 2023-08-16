package com.example.post.service;

import com.example.post.models.PostOffice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostOfficeServiceImpl implements PostOfficeService {
    @Override
    public long createOffice(PostOffice office) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public PostOffice getOfficeById(long postOfficeId) {
        throw new RuntimeException("Not implemented");
    }
}
