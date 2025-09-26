package br.com.pebessao.PetmaxAdmin.repository;

import br.com.pebessao.PetmaxAdmin.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, String> {
}