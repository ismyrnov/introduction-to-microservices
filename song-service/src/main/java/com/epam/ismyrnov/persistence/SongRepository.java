package com.epam.ismyrnov.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.ismyrnov.persistence.entity.SongEntity;

public interface SongRepository extends JpaRepository<SongEntity, Long> {}
