package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Usuario;
import br.com.pebessao.PetmaxAdmin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNomeUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return User.builder()
                .username(usuario.getNomeUsuario())
                .password(usuario.getSenha()) // senha já criptografada
                .roles(usuario.getPapel().toUpperCase()) // ADMINISTRADOR, VENDEDOR, ESTOQUISTA
                .build();
    }
}
