package com.ejerciciospring.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ejerciciospring.app.entity.Asociacion;

@Repository
public interface AsociacionRepository extends JpaRepository<Asociacion, Long> {
}