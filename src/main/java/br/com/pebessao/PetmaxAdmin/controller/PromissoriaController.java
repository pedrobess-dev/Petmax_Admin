package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Promissoria;
import br.com.pebessao.PetmaxAdmin.service.ClienteService;
import br.com.pebessao.PetmaxAdmin.service.ProdutoService;
import br.com.pebessao.PetmaxAdmin.service.PromissoriaService;
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
public class PromissoriaController {
    private final PromissoriaService promService;
    private final ClienteService cliService;
    private final ProdutoService prodService;

    public PromissoriaController(PromissoriaService promService, ClienteService cliService,
                                 ProdutoService prodService) {
        this.promService = promService;
        this.cliService = cliService;
        this.prodService = prodService;
    }

    @GetMapping("/PromissoriaListar")
    public String listar(Model model) {
        model.addAttribute("promissorias", promService.listarTodos());
        return "cadastros/promissoria/promissoria";
    }

    @GetMapping("/PromissoriaNovo")
    public String novo(Model model) {
        model.addAttribute("promissoria", new Promissoria());
        model.addAttribute("clientes", cliService.listarTodos());
        model.addAttribute("produtos", prodService.listarTodos());
        model.addAttribute("modoEdicao", false);
        return "cadastros/promissoria/promissoriaCadastrar";
    }

    @PostMapping("/PromissoriaSalvar")
    public String salvar(@Valid @ModelAttribute Promissoria promissoria, BindingResult result,
                         Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("clientes", cliService.listarTodos());
        model.addAttribute("produtos", prodService.listarTodos());

        if (result.hasErrors()) {
            return "cadastros/promissoria/promissoriaCadastrar";
        }

        boolean isEdit = promissoria.getIdPromissoria() != null;
        promService.salvar(promissoria);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Promissoria editada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Promissoria cadastrada com sucesso!");
        }

        return "redirect:/PromissoriaListar";
    }

    @GetMapping("/PromissoriaEditar/{idpromissoria}")
    public String editar(@PathVariable Integer idpromissoria, Model model) {
        model.addAttribute("promissoria", promService.buscarPorId(idpromissoria));
        model.addAttribute("clientes", cliService.listarTodos());
        model.addAttribute("produtos", prodService.listarTodos());
        model.addAttribute("modoEdicao", true);
        return "cadastros/promissoria/promissoriaCadastrar";
    }

    @GetMapping("/PromissoriaExcluir/{idpromissoria}")
    public String deletar(@PathVariable Integer idpromissoria, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = promService.deletar(idpromissoria);

        if (resultadoMensagem.contains("excluída com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/PromissoriaListar";
    }
}