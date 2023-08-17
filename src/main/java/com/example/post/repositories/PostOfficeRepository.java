package com.example.post.repositories;

import com.example.post.models.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {
    PostOffice findByIndex(long postOfficeIndex);
}
