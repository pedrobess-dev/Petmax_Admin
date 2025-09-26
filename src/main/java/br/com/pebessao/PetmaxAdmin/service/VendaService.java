package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Venda;
import br.com.pebessao.PetmaxAdmin.repository.EntregaRepository;
import br.com.pebessao.PetmaxAdmin.repository.VendaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VendaService {
    private final VendaRepository vendaRepo;
    private final EntregaRepository entregaRepo;

    public VendaService(VendaRepository vendaRepo, EntregaRepository entregaRepo) {
        this.vendaRepo = vendaRepo;
        this.entregaRepo = entregaRepo;
    }

    public List<Venda> listarTodos() {
        return vendaRepo.findAll();
    }

    public void salvar(Venda venda) {
        vendaRepo.save(venda);
    }

    public Venda buscarPorId(Integer idvenda) {
        return vendaRepo.findById(String.valueOf(idvenda)).orElse(null);
    }

    public String deletar(Integer idvenda) {
        Venda venda = vendaRepo.findById(String.valueOf(idvenda)).orElseThrow(() ->
            new IllegalArgumentException("Venda inválida: " + idvenda));

        long entregasCount = entregaRepo.countByVenda(venda);

        if (entregasCount > 0) {
            return "Não foi possível excluir a venda '" + venda.getIdVenda() + "' porque há " + entregasCount + " entrega(s) associada(s) a ela. Remova as entregas primeiro.";
        } else {
            vendaRepo.delete(venda);
            return "Venda '" + venda.getIdVenda() + "' excluída com sucesso!";
        }
    }
}