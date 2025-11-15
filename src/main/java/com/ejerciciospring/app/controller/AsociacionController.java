package com.ejerciciospring.app.controller;

import com.ejerciciospring.app.entity.Asociacion;
import com.ejerciciospring.app.repository.AsociacionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/asociacion")
public class AsociacionController {

    private final AsociacionRepository repo;

    public AsociacionController(AsociacionRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Long id,
                          @RequestParam String nombre,
                          @RequestParam(required = false) String pais,
                          @RequestParam(required = false) String presidente,
                          RedirectAttributes ra) {

        Asociacion a = (id != null) ? repo.findById(id).orElse(new Asociacion()) : new Asociacion();
        a.setNombre(nombre);
        a.setPais(pais);
        a.setPresidente(presidente);
        repo.save(a);

        ra.addAttribute("ok", "Asociación guardada");
        return "redirect:/clubes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        repo.deleteById(id);
        ra.addAttribute("ok", "Asociación eliminada");
        return "redirect:/clubes";
    }
}

