package com.ejerciciospring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ejerciciospring.app.entity.Competicion;

public interface CompeticionRepository extends JpaRepository<Competicion, Long> {
}
