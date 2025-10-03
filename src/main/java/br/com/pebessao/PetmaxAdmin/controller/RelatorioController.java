package br.com.pebessao.PetmaxAdmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RelatorioController {

    @GetMapping("/Relatorios")
    public String relatorio() {
        return "administrativos/relatorio/relatorio";
    }
}
