package br.com.pebessao.PetmaxAdmin.repository;

import br.com.pebessao.PetmaxAdmin.model.Produto;
import br.com.pebessao.PetmaxAdmin.model.Reposicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReposicaoRepository extends JpaRepository<Reposicao, String> {
    long countByProduto(Produto produto);
}
