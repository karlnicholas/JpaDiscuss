package com.example.jpaplay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseChildEntityRepo extends JpaRepository<BaseChildEntity, Long> {
    List<BaseChildEntity> findAllByBaseEntity_Id(Long id);
}
