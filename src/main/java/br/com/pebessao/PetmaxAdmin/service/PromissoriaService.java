package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Promissoria;
import br.com.pebessao.PetmaxAdmin.repository.PromissoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PromissoriaService {
    private final PromissoriaRepository promissoriaRepo;

    public PromissoriaService(PromissoriaRepository promissoriaRepo) {
        this.promissoriaRepo = promissoriaRepo;
    }

    public List<Promissoria> listarTodos() {
        return promissoriaRepo.findAll();
    }

    public void salvar(Promissoria promissoria) {
        promissoriaRepo.save(promissoria);
    }

    public Promissoria buscarPorId(Integer idpromissoria) {
        return promissoriaRepo.findById(String.valueOf(idpromissoria)).orElse(null);
    }

    public String deletar(Integer idpromissoria) {
        Promissoria promissoria = promissoriaRepo.findById(String.valueOf(idpromissoria)).orElseThrow(() ->
            new IllegalArgumentException("Promissoria inválida: " + idpromissoria));

        promissoriaRepo.delete(promissoria);
        return "Promissoria excluída com sucesso!";
    }
}