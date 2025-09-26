package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Promocao;
import br.com.pebessao.PetmaxAdmin.service.ProdutoService;
import br.com.pebessao.PetmaxAdmin.service.PromocaoService;
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
public class PromocaoController {
    private final PromocaoService promoService;
    private final ProdutoService prodService;

    public PromocaoController(PromocaoService promoService, ProdutoService prodService) {
        this.promoService = promoService;
        this.prodService = prodService;
    }

    @GetMapping("/PromocaoListar")
    public String listar(Model model) {
        model.addAttribute("promocoes", promoService.listarTodos());
        return "administrativos/promocao/promocao";
    }

    @GetMapping("/PromocaoNovo")
    public String novo(Model model) {
        model.addAttribute("promocao", new Promocao());
        model.addAttribute("produtos", prodService.listarTodos());
        model.addAttribute("modoEdicao", false);
        return "administrativos/promocao/promocaoCadastrar";
    }

    @PostMapping("/PromocaoSalvar")
    public String salvar(@Valid @ModelAttribute Promocao promocao, BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        model.addAttribute("produtos", prodService.listarTodos());

        if (result.hasErrors()) {
            return "administrativos/promocao/promocaoCadastrar";
        }

        boolean isEdit = promocao.getIdPromocao() != null;
        promoService.salvar(promocao);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Promoção editada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Promoção cadastrada com sucesso!");
        }

        return "redirect:/PromocaoListar";
    }

    @GetMapping("/PromocaoEditar/{idpromocao}")
    public String editar(@PathVariable Integer idpromocao, Model model) {
        model.addAttribute("promocao", promoService.buscarPorId(idpromocao));
        model.addAttribute("produtos", prodService.listarTodos());
        model.addAttribute("modoEdicao", true);
        return "administrativos/promocao/promocaoCadastrar";
    }

    @GetMapping("/PromocaoExcluir/{idpromocao}")
    public String deletar(@PathVariable Integer idpromocao, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = promoService.deletar(idpromocao);

        if (resultadoMensagem.contains("excluída com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/PromocaoListar";
    }
}