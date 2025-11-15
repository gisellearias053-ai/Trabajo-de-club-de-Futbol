package com.ejerciciospring.app.controller;

import com.ejerciciospring.app.entity.*;
import com.ejerciciospring.app.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClubController {

    private final ClubRepository clubRepo;
    private final EntrenadorRepository entRepo;
    private final AsociacionRepository asoRepo;
    private final CompeticionRepository compRepo;
    private final JugadorRepository jugRepo;

    public ClubController(ClubRepository clubRepo,
                          EntrenadorRepository entRepo,
                          AsociacionRepository asoRepo,
                          CompeticionRepository compRepo,
                          JugadorRepository jugRepo) {
        this.clubRepo = clubRepo;
        this.entRepo = entRepo;
        this.asoRepo = asoRepo;
        this.compRepo = compRepo;
        this.jugRepo = jugRepo;
    }

    /* ===== Página principal ===== */
    @GetMapping("/clubes")
    public String clubes(Model model,
                         @RequestParam(value = "q", required = false) String q,
                         @RequestParam(value = "ok", required = false) String ok) {

        List<Club> clubes = clubRepo.findAll();
        if (q != null && !q.isBlank()) {
            String needle = q.toLowerCase();
            clubes = clubes.stream()
                    .filter(c -> c.getNombre() != null && c.getNombre().toLowerCase().contains(needle))
                    .toList();
        }

        model.addAttribute("clubes", clubes);
        model.addAttribute("entrenadores", entRepo.findAll());
        model.addAttribute("asociaciones", asoRepo.findAll());
        model.addAttribute("competiciones", compRepo.findAll());
        model.addAttribute("ok", ok);
        return "clubes"; // tu clubes.html
    }

    /* ===== Crear / Editar Club ===== */
    @PostMapping("/club/guardar")
    public String guardarClub(@RequestParam(required = false) Long id,
                              @RequestParam String nombre,
                              @RequestParam(required = false) Long entrenadorId,
                              @RequestParam(required = false) Long asociacionId,
                              @RequestParam(name = "competicionIds", required = false) List<Long> competicionIds,
                              RedirectAttributes ra) {

        Club club = (id != null) ? clubRepo.findById(id).orElse(new Club()) : new Club();
        club.setNombre(nombre);

        // Entrenador
        if (entrenadorId != null && entrenadorId > 0) {
            entRepo.findById(entrenadorId).ifPresent(club::setEntrenador);
        } else {
            club.setEntrenador(null);
        }

        // Asociación
        if (asociacionId != null && asociacionId > 0) {
            asoRepo.findById(asociacionId).ifPresent(club::setAsociacion);
        } else {
            club.setAsociacion(null);
        }

        // Competiciones (many-to-many)
        if (competicionIds != null && !competicionIds.isEmpty()) {
            club.setCompeticiones(compRepo.findAllById(competicionIds));
        } else {
            club.setCompeticiones(new ArrayList<>());
        }

        clubRepo.save(club);
        ra.addAttribute("ok", "Club guardado correctamente");
        return "redirect:/clubes";
    }

    /* ===== Eliminar Club ===== */
    @PostMapping("/club/{id}/eliminar")
    public String eliminarClub(@PathVariable Long id, RedirectAttributes ra) {
        clubRepo.deleteById(id);
        ra.addAttribute("ok", "Club eliminado");
        return "redirect:/clubes";
    }

    /* ===== (Opc A) Crear / Editar Jugador dentro del club ===== */
    @PostMapping("/club/{clubId}/jugador/guardar")
    public String guardarJugador(@PathVariable Long clubId,
                                 @RequestParam(required = false) Long id,
                                 @RequestParam String nombre,
                                 @RequestParam String apellido,
                                 @RequestParam(required = false) Integer numero,
                                 @RequestParam(required = false) String posicion,
                                 RedirectAttributes ra) {

        Club club = clubRepo.findById(clubId).orElse(null);
        if (club == null) {
            ra.addAttribute("ok", "Club no encontrado");
            return "redirect:/clubes";
        }

        Jugador j = (id != null) ? jugRepo.findById(id).orElse(new Jugador()) : new Jugador();
        j.setNombre(nombre);
        j.setApellido(apellido);
        j.setNumero(numero != null ? numero : 0);
        j.setPosicion(posicion != null ? posicion : "");
        j.setClub(club);

        jugRepo.save(j);
        ra.addAttribute("ok", "Jugador guardado");
        return "redirect:/clubes";
    }

    /* ===== (Opc A) Eliminar Jugador dentro del club ===== */
    @PostMapping("/club/{clubId}/jugador/{jugadorId}/eliminar")
    public String eliminarJugador(@PathVariable Long clubId,
                                  @PathVariable Long jugadorId,
                                  RedirectAttributes ra) {
        jugRepo.deleteById(jugadorId);
        ra.addAttribute("ok", "Jugador eliminado");
        return "redirect:/clubes";
    }
}
