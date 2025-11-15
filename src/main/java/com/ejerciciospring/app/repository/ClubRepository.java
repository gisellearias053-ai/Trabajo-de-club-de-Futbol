package com.ejerciciospring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ejerciciospring.app.entity.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}