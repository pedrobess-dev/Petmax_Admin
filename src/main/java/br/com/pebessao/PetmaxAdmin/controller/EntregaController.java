package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Entrega;
import br.com.pebessao.PetmaxAdmin.service.EntregaService;
import br.com.pebessao.PetmaxAdmin.service.VendaService;
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
public class EntregaController {
    private final EntregaService entService;
    private final VendaService venService;

    public EntregaController(EntregaService entService, VendaService venService) {
        this.entService = entService;
        this.venService = venService;
    }

    @GetMapping("/EntregaListar")
    public String listar(Model model) {
        model.addAttribute("entregas", entService.listarTodos());
        return "cadastros/entrega/entrega";
    }

    @GetMapping("/EntregaNovo")
    public String novo(Model model) {
        model.addAttribute("entrega", new Entrega());
        model.addAttribute("vendas", venService.listarTodos());
        model.addAttribute("modoEdicao", false);
        return "cadastros/entrega/entregaCadastrar";
    }

    @PostMapping("/EntregaSalvar")
    public String salvar(@Valid @ModelAttribute Entrega entrega, BindingResult result,
                         Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("vendas", venService.listarTodos());

        if (result.hasErrors()) {
            return "cadastros/entrega/entregaCadastrar";
        }

        boolean isEdit = entrega.getIdEntrega() != null;
        entService.salvar(entrega);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Entrega editada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Entrega cadastrada com sucesso!");
        }

        return "redirect:/EntregaListar";
    }

    @GetMapping("/EntregaEditar/{identrega}")
    public String editar(@PathVariable Integer identrega, Model model) {
        model.addAttribute("entrega", entService.buscarPorId(identrega));
        model.addAttribute("vendas", venService.listarTodos());
        model.addAttribute("modoEdicao", true);
        return "cadastros/entrega/entregaCadastrar";
    }

    @GetMapping("/EntregaExcluir/{identrega}")
    public String deletar(@PathVariable Integer identrega, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = entService.deletar(identrega);

        if (resultadoMensagem.contains("excluída com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/EntregaListar";
    }
}
