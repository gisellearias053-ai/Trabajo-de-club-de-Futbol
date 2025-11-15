package com.ejerciciospring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;

import com.ejerciciospring.app.entity.Asociacion;
import com.ejerciciospring.app.entity.Club;
import com.ejerciciospring.app.entity.Competicion;
import com.ejerciciospring.app.entity.Entrenador;
import com.ejerciciospring.app.entity.Jugador;

import com.ejerciciospring.app.repository.AsociacionRepository;
import com.ejerciciospring.app.repository.ClubRepository;
import com.ejerciciospring.app.repository.CompeticionRepository;
import com.ejerciciospring.app.repository.EntrenadorRepository;
import com.ejerciciospring.app.repository.JugadorRepository;

@SpringBootApplication
public class EjerciciospringApplication {

    public static void main(String[] args) {
        SpringApplication.run(EjerciciospringApplication.class, args);
    }

    /**
     * Semilla de datos para que la página /clubes tenga información inicial.
     * Se ejecuta solo si no hay clubes creados (evita duplicados si cambias a una BD persistente).
     */
    @Bean
    public CommandLineRunner seedData(EntrenadorRepository entrenadorRepo,
                                      ClubRepository clubRepo,
                                      JugadorRepository jugadorRepo,
                                      AsociacionRepository asociacionRepo,
                                      CompeticionRepository competicionRepo) {

        return args -> {
            if (clubRepo.count() > 0) {
                // Ya hay datos, no sembrar de nuevo
                return;
            }

            // === Asociación
            Asociacion aso = new Asociacion();
            aso.setNombre("Federación Colombiana de Fútbol");
            aso.setPais("Colombia");
            aso.setPresidente("Presidente FCF");
            asociacionRepo.save(aso);

            // === Entrenador
            Entrenador ent = new Entrenador();
            ent.setNombre("Rafael");
            ent.setApellido("Dudamel");
            entrenadorRepo.save(ent);

            // === Competiciones
            Competicion c1 = new Competicion();
            c1.setNombre("Liga BetPlay");
            c1.setMontoPremio(5_000_000);
            competicionRepo.save(c1);

            Competicion c2 = new Competicion();
            c2.setNombre("Copa Colombia");
            c2.setMontoPremio(2_000_000);
            competicionRepo.save(c2);

            // === Club
            Club club = new Club();
            club.setNombre("Atlético Bucaramanga");
            club.setEntrenador(ent);   // @OneToOne
            club.setAsociacion(aso);   // @ManyToOne

            // @ManyToMany (lado dueño está en Club)
            List<Competicion> comps = new ArrayList<>();
            comps.add(c1);
            comps.add(c2);
            club.setCompeticiones(comps);

            clubRepo.save(club);

            // === Jugadores (relación @ManyToOne hacia Club)
            Jugador j1 = new Jugador();
            j1.setNombre("Daniel");
            j1.setApellido("Gómez");
            j1.setNumero(10);
            j1.setPosicion("Delantero");
            j1.setClub(club);
            jugadorRepo.save(j1);

            Jugador j2 = new Jugador();
            j2.setNombre("Luis");
            j2.setApellido("Rojas");
            j2.setNumero(5);
            j2.setPosicion("Mediocampista");
            j2.setClub(club);
            jugadorRepo.save(j2);
        };
    }
}
