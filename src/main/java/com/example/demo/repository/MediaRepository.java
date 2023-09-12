package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Media;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
