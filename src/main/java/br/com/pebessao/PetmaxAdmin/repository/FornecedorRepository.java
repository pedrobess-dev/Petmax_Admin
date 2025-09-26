package br.com.pebessao.PetmaxAdmin.repository;

import br.com.pebessao.PetmaxAdmin.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, String> {
    boolean existsByCnpj(String cnpj);
}