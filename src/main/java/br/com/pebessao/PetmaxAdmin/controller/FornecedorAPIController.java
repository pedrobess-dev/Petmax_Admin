package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.dto.FornecedorDTO;
import br.com.pebessao.PetmaxAdmin.dto.FornecedorSalvarDTO;
import br.com.pebessao.PetmaxAdmin.model.Fornecedor;
import br.com.pebessao.PetmaxAdmin.service.FornecedorBuscaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorAPIController {

    @Autowired
    private FornecedorBuscaService fornecedorBuscaService;

    @GetMapping("/buscar/{cnpj}")
    public FornecedorDTO buscarFornecedor(@PathVariable String cnpj) {
        return fornecedorBuscaService.buscarPorCnpj(cnpj);
    }

    @PostMapping("/salvar")
    public ResponseEntity<Fornecedor> salvarFornecedor(@RequestBody FornecedorSalvarDTO dto) {
        Fornecedor fornecedor = fornecedorBuscaService.salvarFornecedor(dto);
        return ResponseEntity.ok(fornecedor);
    }
}
