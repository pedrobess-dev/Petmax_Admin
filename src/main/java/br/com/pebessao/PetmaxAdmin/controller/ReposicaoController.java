package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Reposicao;
import br.com.pebessao.PetmaxAdmin.service.ProdutoService;
import br.com.pebessao.PetmaxAdmin.service.ReposicaoService;
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
public class ReposicaoController {
    private final ReposicaoService repoService;
    private final ProdutoService prodService;

    public ReposicaoController(ReposicaoService repoService, ProdutoService prodService) {
        this.repoService = repoService;
        this.prodService = prodService;
    }

    @GetMapping("/ReposicaoListar")
    public String listar(Model model) {
        model.addAttribute("reposicoes", repoService.listarTodos());
        return "cadastros/reposicao/reposicao";
    }

    @GetMapping("/ReposicaoNovo")
    public String novo(Model model) {
        model.addAttribute("reposicao", new Reposicao());
        model.addAttribute("produtos", prodService.listarTodos());
        model.addAttribute("modoEdicao", false);
        return "cadastros/reposicao/reposicaoCadastrar";
    }

    @PostMapping("/ReposicaoSalvar")
    public String salvar(@ModelAttribute @Valid Reposicao reposicao, BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        model.addAttribute("produtos", prodService.listarTodos());

        if (result.hasErrors()) {
            return "cadastros/reposicao/reposicaoCadastrar";
        }

        boolean isEdit = reposicao.getIdReposicao() != null;
        repoService.salvar(reposicao);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Reposição editada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Reposição cadastrada com sucesso!");
        }

        return "redirect:/ReposicaoListar";
    }

    @GetMapping("/ReposicaoEditar/{idreposicao}")
    public String editar(@PathVariable Integer idreposicao, Model model) {
        model.addAttribute("reposicao", repoService.buscarPorId(idreposicao));
        model.addAttribute("produtos", prodService.listarTodos());
        model.addAttribute("modoEdicao", true);
        return "cadastros/reposicao/reposicaoCadastrar";
    }

    @GetMapping("/ReposicaoExcluir/{idreposicao}")
    public String deletar(@PathVariable Integer idreposicao, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = repoService.deletar(idreposicao);

        if (resultadoMensagem.contains("excluída com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/ReposicaoListar";
    }
}