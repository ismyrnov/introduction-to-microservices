package com.epam.ismyrnov.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.ismyrnov.persistence.entity.ResourceEntity;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {}
