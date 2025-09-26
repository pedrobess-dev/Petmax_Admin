package br.com.pebessao.PetmaxAdmin.repository;

import br.com.pebessao.PetmaxAdmin.model.Cliente;
import br.com.pebessao.PetmaxAdmin.model.Promissoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromissoriaRepository extends JpaRepository<Promissoria, String> {
    @Query(
            value = "SELECT COALESCE(SUM(p.valor), 0) FROM promissoria p",
            nativeQuery = true
    )
    double somaValorTotal();

    long countByCliente(Cliente cliente);
}