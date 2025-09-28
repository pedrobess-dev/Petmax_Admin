package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.dto.FornecedorDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class FornecedorBuscaService {

    private final RestTemplate restTemplate = new RestTemplate();

    public FornecedorDTO buscarPorCnpj(String cnpj) {
        String url = "https://publica.cnpj.ws/cnpj/" + cnpj;

        ResponseEntity<FornecedorDTO> response =
                restTemplate.getForEntity(url, FornecedorDTO.class);

        return response.getBody();
    }
}

