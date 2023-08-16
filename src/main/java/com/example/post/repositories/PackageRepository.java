package com.example.post.repositories;

import com.example.post.models.PostPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PostPackage, Long> {
}
