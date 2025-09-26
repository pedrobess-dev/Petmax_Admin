package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Cliente;
import br.com.pebessao.PetmaxAdmin.repository.ClienteRepository;
import br.com.pebessao.PetmaxAdmin.repository.PromissoriaRepository;
import br.com.pebessao.PetmaxAdmin.repository.VendaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepo;
    private final PromissoriaRepository promissoriaRepo;
    private final VendaRepository vendaRepo;

    public ClienteService(ClienteRepository clienteRepo, PromissoriaRepository promissoriaRepo, VendaRepository vendaRepo) {
        this.clienteRepo = clienteRepo;
        this.promissoriaRepo = promissoriaRepo;
        this.vendaRepo = vendaRepo;
    }

    public List<Cliente> listarTodos() {
        return clienteRepo.findAll();
    }

    public void salvar(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    public Cliente buscarPorId(Integer idcliente) {
        return clienteRepo.findById(String.valueOf(idcliente)).orElse(null);
    }

    public String deletar(Integer idcliente) {
        Cliente cliente = clienteRepo.findById(String.valueOf(idcliente)).orElseThrow(() ->
            new IllegalArgumentException("Cliente inválido: " + idcliente));

        long promissoriasCount = promissoriaRepo.countByCliente(cliente);
        long vendasCount = vendaRepo.countByCliente(cliente);

        if (promissoriasCount > 0) {
            return "Não foi possível excluir o cliente '" + cliente.getNomeCliente() + "' porque há " + promissoriasCount + " promissoria(s) associada(s) a ele. Remova as promissorias primeiro.";
        } else if (vendasCount > 0) {
            return "Não foi possível excluir o cliente '" + cliente.getNomeCliente() + "' porque há " + vendasCount + " venda(s) associada(s) a ele. Remova as vendas primeiro.";
        } else {
            clienteRepo.delete(cliente);
            return "Cliente '" + cliente.getNomeCliente() + "' excluído com sucesso!";
        }
    }
}