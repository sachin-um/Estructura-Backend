package com.Estructura.API.repository;

import com.Estructura.API.model.Blog;
import com.Estructura.API.model.Tag;
import com.Estructura.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findByTagsIn(Set<Tag> tags);
    List<Blog> findAllByUser(User user);

    @Override
    Optional<Blog> findById(Long id);
}
