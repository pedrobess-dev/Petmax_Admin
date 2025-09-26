package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Produto;
import br.com.pebessao.PetmaxAdmin.repository.ProdutoRepository;
import br.com.pebessao.PetmaxAdmin.repository.PromocaoRepository;
import br.com.pebessao.PetmaxAdmin.repository.ReposicaoRepository;
import br.com.pebessao.PetmaxAdmin.repository.VendaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepo;
    private final ReposicaoRepository reposicaoRepo;
    private final VendaRepository vendaRepo;
    private final PromocaoRepository promocaoRepo;

    public ProdutoService(ProdutoRepository produtoRepo, ReposicaoRepository reposicaoRepo,
                          VendaRepository vendaRepo, PromocaoRepository promocaoRepo) {
        this.produtoRepo = produtoRepo;
        this.reposicaoRepo = reposicaoRepo;
        this.vendaRepo = vendaRepo;
        this.promocaoRepo = promocaoRepo;
    }

    public List<Produto> listarTodos() {
        return produtoRepo.findAll();
    }

    public void salvar(Produto produto) {
        produtoRepo.save(produto);
    }

    public Produto buscarPorId(Integer idproduto) {
        return produtoRepo.findById(String.valueOf(idproduto)).orElse(null);
    }

    public String deletar(Integer idproduto) {
        Produto produto = produtoRepo.findById(String.valueOf(idproduto)).orElseThrow(() ->
            new IllegalArgumentException("Produto inválido: " + idproduto));

        long reposicaoCount = reposicaoRepo.countByProduto(produto);
        long vendaCount = vendaRepo.countByProduto(produto);
        long promocaoCount = promocaoRepo.countByProduto(produto);

        if (reposicaoCount > 0) {
            return "Não foi possível excluir o produto '" + produto.getNomeProduto() + "' porque há " + reposicaoCount + " reposição(ões) associada(s) a ele. Remova as reposições primeiro.";
        } else if (vendaCount > 0) {
            return "Não foi possível excluir o produto '" + produto.getNomeProduto() + "' porque há " + vendaCount + " venda(s) associada(s) a ele. Remova as vendas primeiro.";
        } else if (promocaoCount > 0) {
            return "Não foi possível excluir o produto '" + produto.getNomeProduto() + "' porque há " + promocaoCount + " promoção(ões) associada(s) a ele. Remova as promoçõse primeiro.";
        } else {
            produtoRepo.delete(produto);
            return "Produto '" + produto.getNomeProduto() + "' excluído com sucesso!";
        }
    }
}