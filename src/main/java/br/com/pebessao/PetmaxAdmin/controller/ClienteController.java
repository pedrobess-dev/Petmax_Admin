package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Cliente;
import br.com.pebessao.PetmaxAdmin.service.ClienteService;
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
public class ClienteController {
    private final ClienteService cliService;

    public ClienteController(ClienteService cliService) {
        this.cliService = cliService;
    }

    @GetMapping("/ClienteListar")
    public String listar(Model model) {
        model.addAttribute("clientes", cliService.listarTodos());
        return "cadastros/cliente/cliente";
    }

    @GetMapping("/ClienteNovo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("modoEdicao", false);
        return "cadastros/cliente/clienteCadastrar";
    }

    @PostMapping("/ClienteSalvar")
    public String salvar(@Valid @ModelAttribute Cliente cliente, BindingResult result,
                         Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cadastros/cliente/clienteCadastrar";
        }

        boolean isEdit = cliente.getIdCliente() != null;
        cliService.salvar(cliente);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Cliente editado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Cliente cadastrado com sucesso!");
        }

        return "redirect:/ClienteListar";
    }

    @GetMapping("/ClienteEditar/{idcliente}")
    public String editar(@PathVariable Integer idcliente, Model model) {
        model.addAttribute("cliente", cliService.buscarPorId(idcliente));
        model.addAttribute("modoEdicao", true);
        return "cadastros/cliente/clienteCadastrar";
    }

    @GetMapping("/ClienteExcluir/{idcliente}")
    public String deletar(@PathVariable Integer idcliente, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = cliService.deletar(idcliente);

        if (resultadoMensagem.contains("excluído com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/ClienteListar";
    }
}