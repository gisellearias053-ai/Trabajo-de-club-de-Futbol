package com.ejerciciospring.app.controller;

import com.ejerciciospring.app.entity.Entrenador;
import com.ejerciciospring.app.repository.EntrenadorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/entrenador")
public class EntrenadorController {

    private final EntrenadorRepository repo;

    public EntrenadorController(EntrenadorRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Long id,
                          @RequestParam String nombre,
                          @RequestParam String apellido,
                          RedirectAttributes ra) {

        Entrenador e = (id != null) ? repo.findById(id).orElse(new Entrenador()) : new Entrenador();
        e.setNombre(nombre);
        e.setApellido(apellido);
        repo.save(e);

        ra.addAttribute("ok", "Entrenador guardado");
        return "redirect:/clubes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addAttribute("ok", "Entrenador eliminado");
        return "redirect:/clubes";
    }
}
