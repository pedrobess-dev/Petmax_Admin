package br.com.pebessao.PetmaxAdmin.repository;

import br.com.pebessao.PetmaxAdmin.model.Entrega;
import br.com.pebessao.PetmaxAdmin.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregaRepository extends JpaRepository<Entrega, String> {
    long countByVenda(Venda venda);
}