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

        if (fornecedorRepository.existsByCnpj(dto.getCnpj())) {
            throw new IllegalArgumentException("CNPJ " + dto.getCnpj() + " já está cadastrado.");
        }

        // Se a API externa não fornecer o email, ele pode ir nulo,
        // o que causaria um DataIntegrityViolationException no banco
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail do fornecedor é obrigatório e não foi encontrado na API.");
        }

        if (dto.getNumero() == null || dto.getNumero() < 0) {
            // Se a API externa retornou algo que virou 'null' ou um valor negativo
            throw new IllegalArgumentException("O número do endereço é obrigatório e deve ser um valor válido (maior ou igual a zero, se S/N não for o caso).");
        }

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

