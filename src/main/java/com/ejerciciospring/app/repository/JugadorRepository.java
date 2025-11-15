package com.ejerciciospring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ejerciciospring.app.entity.Jugador;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
}