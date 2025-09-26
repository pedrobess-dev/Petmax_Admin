package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Usuario;
import br.com.pebessao.PetmaxAdmin.service.UsuarioService;
import br.com.pebessao.PetmaxAdmin.validator.CPFValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {
    private final UsuarioService usuService;

    public UsuarioController(UsuarioService usuService) {
        this.usuService = usuService;
    }

    @GetMapping("/UsuarioListar")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuService.listarTodos());
        return "administrativos/usuario/usuario";
    }

    @GetMapping("/UsuarioNovo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("modoEdicao", false);
        return "administrativos/usuario/usuarioCadastrar";
    }

    @PostMapping("/UsuarioSalvar")
    public String salvar(@Valid @ModelAttribute Usuario usuario, BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "administrativos/usuario/usuarioCadastrar";
        }

        if (!CPFValidator.isCPF(usuario.getCpf())) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("erroCpf", "CPF inválido! Verifique o número.");
            return "administrativos/usuario/usuarioCadastrar";
        }

        boolean cpfExiste = usuService.cpfExiste(usuario.getCpf());
        boolean isEdit = usuario.getIdUsuario() != null;

        if (cpfExiste && (usuario.getIdUsuario() == null || !usuService.buscarPorId(usuario.getIdUsuario()).getCpf().equals(usuario.getCpf()))) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("erroCpf", "CPF inválido! CPF já cadastrado!");
            return "administrativos/usuario/usuarioCadastrar";
        }

        usuService.salvar(usuario);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário editado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
        }

        return "redirect:/UsuarioListar";
    }

    @GetMapping("/UsuarioEditar/{idusuario}")
    public String editar(@PathVariable Integer idusuario, Model model) {
        model.addAttribute("usuario", usuService.buscarPorId(idusuario));
        model.addAttribute("modoEdicao", true);
        return "administrativos/usuario/usuarioCadastrar";
    }

    @GetMapping("/UsuarioExcluir/{idusuario}")
    public String deletar(@PathVariable Integer idusuario, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = usuService.deletar(idusuario);

        if (resultadoMensagem.contains("excluído com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/UsuarioListar";
    }
}