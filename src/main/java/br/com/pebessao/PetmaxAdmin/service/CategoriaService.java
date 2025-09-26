package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Categoria;
import br.com.pebessao.PetmaxAdmin.repository.CategoriaRepository;
import br.com.pebessao.PetmaxAdmin.repository.ProdutoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepo;
    private final ProdutoRepository produtoRepo;

    public CategoriaService(CategoriaRepository categoriaRepo, ProdutoRepository produtoRepo) {
        this.categoriaRepo = categoriaRepo;
        this.produtoRepo = produtoRepo;
    }

    public List<Categoria> listarTodos() {
        return categoriaRepo.findAll();
    }

    public void salvar(Categoria categoria) {
        categoriaRepo.save(categoria);
    }

    public Categoria buscarPorId(Integer idcategoria) {
        return categoriaRepo.findById(String.valueOf(idcategoria)).orElse(null);
    }

    public String deletar(Integer idcategoria) {
        Categoria categoria = categoriaRepo.findById(String.valueOf(idcategoria)).orElseThrow(() ->
            new IllegalArgumentException("Categoria inválida: " + idcategoria));

        long produtosCount = produtoRepo.countByCategoria(categoria);

        if (produtosCount > 0) {
            return "Não foi possível excluir a categoria '" + categoria.getNomeCategoria() + "' porque há " + produtosCount + " produto(s) associado(s) a ela. Remova os produtos primeiro.";
        } else {
            categoriaRepo.delete(categoria);
            return "Categoria '" + categoria.getNomeCategoria() + "' excluída com sucesso!";
        }
    }
}