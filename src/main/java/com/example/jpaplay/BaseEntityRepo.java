package com.example.jpaplay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BaseEntityRepo extends JpaRepository<BaseEntity, Long> {
    @Query("from BaseEntity b left outer join fetch b.children where b.id = :id")
    Optional<BaseEntity> findByIdWithChildren(@Param("id") Long id);
}
