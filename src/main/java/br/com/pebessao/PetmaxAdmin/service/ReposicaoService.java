package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Reposicao;
import br.com.pebessao.PetmaxAdmin.repository.ReposicaoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReposicaoService {
    private final ReposicaoRepository reposicaoRepo;

    public ReposicaoService(ReposicaoRepository reposicaoRepo) {
        this.reposicaoRepo = reposicaoRepo;
    }

    public List<Reposicao> listarTodos() {
        return reposicaoRepo.findAll();
    }

    public void salvar(Reposicao reposicao) {
        reposicaoRepo.save(reposicao);
    }

    public Reposicao buscarPorId(Integer idreposicao) {
        return reposicaoRepo.findById(String.valueOf(idreposicao)).orElse(null);
    }

    public String deletar(Integer idreposicao) {
        Reposicao reposicao = reposicaoRepo.findById(String.valueOf(idreposicao)).orElseThrow(() ->
            new IllegalArgumentException("Reposição inválida " + idreposicao));

        reposicaoRepo.delete(reposicao);
        return "Reposição excluída com sucesso!";
    }
}