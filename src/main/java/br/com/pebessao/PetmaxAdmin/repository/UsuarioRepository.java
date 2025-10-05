package br.com.pebessao.PetmaxAdmin.repository;

import br.com.pebessao.PetmaxAdmin.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    boolean existsByCpf(String cpf);
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
}
