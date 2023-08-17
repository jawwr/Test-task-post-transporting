package com.example.post.service;

import com.example.post.exception.PostOfficeAlreadyExistException;
import com.example.post.exception.PostOfficeNotExistException;
import com.example.post.models.PostOffice;
import com.example.post.repositories.PostOfficeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostOfficeServiceImpl implements PostOfficeService {
    private final PostOfficeRepository repository;

    @Override
    @Transactional
    public long createPostOffice(PostOffice office) {
        if (repository.findByIndex(office.getIndex()) != null) {
            throw new PostOfficeAlreadyExistException("Post office with this index already exist");
        }
        if (office.getIndex() == 0) {
            throw new PostOfficeNotExistException("Post office index mustn't be 0");
        }
        return repository.save(office).getIndex();
    }

    @Override
    public PostOffice getPostOfficeByIndex(long postOfficeId) {
        var savedPostOffice = repository.findByIndex(postOfficeId);
        if (savedPostOffice == null) {
            throw new PostOfficeNotExistException("Post office with id " + postOfficeId + " not exists");
        }
        return savedPostOffice;
    }
}
