package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.dto.FornecedorDTO;
import br.com.pebessao.PetmaxAdmin.dto.FornecedorSalvarDTO;
import br.com.pebessao.PetmaxAdmin.model.Fornecedor;
import br.com.pebessao.PetmaxAdmin.service.FornecedorBuscaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> salvarFornecedor(@RequestBody FornecedorSalvarDTO dto) {
        try {
            // Implementar a checagem de duplicidade no Service é o ideal.
            Fornecedor fornecedor = fornecedorBuscaService.salvarFornecedor(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(fornecedor);
        } catch (DataIntegrityViolationException e) {
            // Erro de restrição do banco (CNPJ ou Email já existem)
            e.printStackTrace();
            String mensagemErro = "Erro ao salvar fornecedor. CNPJ ou Email já estão cadastrados ou dados obrigatórios ausentes.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
        } catch (IllegalArgumentException e) {
            // Erro lançado pelo Service (ex: CNPJ já cadastrado)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Erro inesperado
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor: " + e.getMessage());
        }
    }
}
