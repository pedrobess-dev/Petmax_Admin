package br.com.pebessao.PetmaxAdmin.config;

import br.com.pebessao.PetmaxAdmin.service.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;

    public SecurityConfig(UsuarioDetailsService usuarioDetailsService) {
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/img/**", "/scss/**", "/vendor/**").permitAll()
                .requestMatchers("/login", "/register", "/UsuarioRegistrar").permitAll()
                        .requestMatchers("/VendaListar", "/ClienteListar", "/PromissoriaListar", "/EntregaListar").hasAnyRole("VENDEDOR", "ADMINISTRADOR")
                        .requestMatchers("/ProdutoListar", "/CategoriaListar", "/FornecedorListar", "/ReposicaoListar").hasAnyRole("ESTOQUISTA", "ADMINISTRADOR")
                        .requestMatchers("/BuscarFornecedores", "/PromocaoListar", "/Relatorios", "/UsuarioListar").hasRole("ADMINISTRADOR")
                .requestMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults()
        ).formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }
}
