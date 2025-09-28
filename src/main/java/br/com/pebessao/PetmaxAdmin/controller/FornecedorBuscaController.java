package br.com.pebessao.PetmaxAdmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FornecedorBuscaController {

    @GetMapping("/BuscarFornecedores")
    public String buscarFornecedores() {
        return "administrativos/buscaFornecedor/buscaFornecedor";
    }
}
