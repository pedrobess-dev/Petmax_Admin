package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Fornecedor;
import br.com.pebessao.PetmaxAdmin.repository.FornecedorRepository;
import br.com.pebessao.PetmaxAdmin.repository.ProdutoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepo;
    private final ProdutoRepository produtoRepo;

    public FornecedorService(FornecedorRepository fornecedorRepo, ProdutoRepository produtoRepo) {
        this.fornecedorRepo = fornecedorRepo;
        this.produtoRepo = produtoRepo;
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorRepo.findAll();
    }

    public void salvar(Fornecedor fornecedor) {
        fornecedorRepo.save(fornecedor);
    }

    public Fornecedor buscarPorId(Integer idfornecedor) {
        return fornecedorRepo.findById(String.valueOf(idfornecedor)).orElse(null);
    }

    public String deletar(Integer idfornecedor) {
        Fornecedor fornecedor = fornecedorRepo.findById(String.valueOf(idfornecedor)).orElseThrow(() ->
            new IllegalArgumentException("Fornecedor inválido: " + idfornecedor));

        long produtosCount = produtoRepo.countByFornecedor(fornecedor);

        if (produtosCount > 0) {
            return "Não foi possível excluir o fornecedor '" + fornecedor.getNomeFornecedor() + "' porque há " + produtosCount + " produto(s) associados(s) a ele. Remova os produtos primeiro.";
        } else {
            fornecedorRepo.delete(fornecedor);
            return "Fornecedor '" + fornecedor.getNomeFornecedor() + "' excluído com sucesso!";
        }
    }

    public boolean cnpjExiste(String cnpj) {
        return fornecedorRepo.existsByCnpj(cnpj);
    }
}