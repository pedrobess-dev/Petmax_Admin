package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Usuario;
import br.com.pebessao.PetmaxAdmin.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepo.findAll();
    }

    public void salvar(Usuario usuario) {
        usuarioRepo.save(usuario);
    }

    public Usuario buscarPorId(Integer idusuario) {
        return usuarioRepo.findById(String.valueOf(idusuario)).orElse(null);
    }

    public String deletar(Integer idusuario) {
        Usuario usuario = usuarioRepo.findById(String.valueOf(idusuario)).orElseThrow(() ->
            new IllegalArgumentException("Usuário inválido: " + idusuario));

        usuarioRepo.delete(usuario);
        return "Usuário '" + usuario.getNomeUsuario() + "' excluído com sucesso!";
    }

    public boolean cpfExiste(String cpf) {
        return usuarioRepo.existsByCpf(cpf);
    }
}