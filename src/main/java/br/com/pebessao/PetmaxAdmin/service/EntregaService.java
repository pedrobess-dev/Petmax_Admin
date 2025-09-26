package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Entrega;
import br.com.pebessao.PetmaxAdmin.repository.EntregaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EntregaService {
    private final EntregaRepository entregaRepo;

    public EntregaService(EntregaRepository entregaRepo) {
        this.entregaRepo = entregaRepo;
    }

    public List<Entrega> listarTodos() {
        return entregaRepo.findAll();
    }

    public void salvar(Entrega entrega) {
        entregaRepo.save(entrega);
    }

    public Entrega buscarPorId(Integer identrega) {
        return entregaRepo.findById(String.valueOf(identrega)).orElse(null);
    }

    public String deletar(Integer identrega) {
        Entrega entrega = entregaRepo.findById(String.valueOf(identrega)).orElseThrow(() ->
            new IllegalArgumentException("Entrega inválida: " + identrega));

        entregaRepo.delete(entrega);
        return "Entrega excluída com sucesso!";
    }
}