package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Promocao;
import br.com.pebessao.PetmaxAdmin.repository.PromocaoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PromocaoService {
    private final PromocaoRepository promocaoRepo;

    public PromocaoService(PromocaoRepository promocaoRepo) {
        this.promocaoRepo = promocaoRepo;
    }

    public List<Promocao> listarTodos() {
        return promocaoRepo.findAll();
    }

    public void salvar(Promocao promocao) {
        promocaoRepo.save(promocao);
    }

    public Promocao buscarPorId(Integer idpromocao) {
        return promocaoRepo.findById(String.valueOf(idpromocao)).orElse(null);
    }

    public String deletar(Integer idpromocao) {
        Promocao promocao = promocaoRepo.findById(String.valueOf(idpromocao)).orElseThrow(() ->
            new IllegalArgumentException("Promoção inválida " + idpromocao));

        promocaoRepo.delete(promocao);
        return "Promoção excluída com sucesso!";
    }
}