package com.ejerciciospring.app.controller;

import com.ejerciciospring.app.entity.Competicion;
import com.ejerciciospring.app.repository.CompeticionRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/competicion")
public class CompeticionController {

    private final CompeticionRepository repo;

    public CompeticionController(CompeticionRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Long id,
                          @RequestParam String nombre,
                          @RequestParam(required = false) Integer montoPremio,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
                          RedirectAttributes ra) {

        Competicion c = (id != null) ? repo.findById(id).orElse(new Competicion()) : new Competicion();
        c.setNombre(nombre);
        c.setMontoPremio(montoPremio);
        c.setFechaInicio(fechaInicio);
        c.setFechaFin(fechaFin);
        repo.save(c);

        ra.addAttribute("ok", "Competición guardada");
        return "redirect:/clubes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addAttribute("ok", "Competición eliminada");
        return "redirect:/clubes";
    }
}
