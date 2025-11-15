package com.ejerciciospring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ejerciciospring.app.entity.Entrenador;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
}