package com.Estructura.API.repository;

import com.Estructura.API.model.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTagRepository extends JpaRepository<BlogTag, Long> {
}
