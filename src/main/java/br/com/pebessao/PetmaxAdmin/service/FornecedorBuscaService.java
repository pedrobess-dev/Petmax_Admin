package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.dto.FornecedorDTO;
import br.com.pebessao.PetmaxAdmin.dto.FornecedorSalvarDTO;
import br.com.pebessao.PetmaxAdmin.model.Fornecedor;
import br.com.pebessao.PetmaxAdmin.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class FornecedorBuscaService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public FornecedorDTO buscarPorCnpj(String cnpj) {
        String url = "https://publica.cnpj.ws/cnpj/" + cnpj;

        ResponseEntity<FornecedorDTO> response =
                restTemplate.getForEntity(url, FornecedorDTO.class);

        return response.getBody();
    }

    public Fornecedor salvarFornecedor(FornecedorSalvarDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNomeFornecedor(dto.getNomeFornecedor());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setCep(dto.getCep());
        fornecedor.setBairro(dto.getBairro());
        fornecedor.setRua(dto.getRua());
        fornecedor.setNumero(dto.getNumero());
        fornecedor.setCidade(dto.getCidade());
        fornecedor.setUf(dto.getUf());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setEmail(dto.getEmail());

        return fornecedorRepository.save(fornecedor);
    }
}

