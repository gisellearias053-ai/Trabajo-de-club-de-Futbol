package com.ejerciciospring.app.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clubes")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    // 1. Un club tiene un entrenador
    @OneToOne
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;

    // 2. Un club tiene muchos jugadores
    // usamos @JoinColumn para que la FK quede en la tabla jugadores
    @OneToMany
    @JoinColumn(name = "club_id")
    private List<Jugador> jugadores;

    // 3. Muchos clubes pueden pertenecer a una misma asociación
    @ManyToOne
    @JoinColumn(name = "asociacion_id")
    private Asociacion asociacion;

    // 4. Muchos clubes pueden estar en muchas competiciones
    @ManyToMany
    @JoinTable(
            name = "club_competicion",
            joinColumns = @JoinColumn(name = "club_id"),
            inverseJoinColumns = @JoinColumn(name = "competicion_id")
    )
    private List<Competicion> competiciones;

    // ====== getters y setters ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Asociacion getAsociacion() {
        return asociacion;
    }

    public void setAsociacion(Asociacion asociacion) {
        this.asociacion = asociacion;
    }

    public List<Competicion> getCompeticiones() {
        return competiciones;
    }

    public void setCompeticiones(List<Competicion> competiciones) {
        this.competiciones = competiciones;
    }
}
