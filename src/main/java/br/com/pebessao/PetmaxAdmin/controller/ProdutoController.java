package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Produto;
import br.com.pebessao.PetmaxAdmin.service.CategoriaService;
import br.com.pebessao.PetmaxAdmin.service.FornecedorService;
import br.com.pebessao.PetmaxAdmin.service.ProdutoService;
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
public class ProdutoController {
    private final ProdutoService prodService;
    private final CategoriaService catService;
    private final FornecedorService fornService;

    public ProdutoController(ProdutoService prodService, CategoriaService catService,
                             FornecedorService fornService) {
        this.prodService = prodService;
        this.catService = catService;
        this.fornService = fornService;
    }

    @GetMapping("/ProdutoListar")
    public String listar(Model model) {
        model.addAttribute("produtos", prodService.listarTodos());
        return "cadastros/produto/produto";
    }

    @GetMapping("/ProdutoNovo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", catService.listarTodos());
        model.addAttribute("fornecedores", fornService.listarTodos());
        model.addAttribute("modoEdicao", false);
        return "cadastros/produto/produtoCadastrar";
    }

    @PostMapping("/ProdutoSalvar")
    public String salvar(@ModelAttribute @Valid Produto produto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("categorias", catService.listarTodos());
        model.addAttribute("fornecedores", fornService.listarTodos());

        if (result.hasErrors()) {
            return "cadastros/produto/produtoCadastrar";
        }

        boolean isEdit = produto.getIdProduto() != null;
        prodService.salvar(produto);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto editado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto cadastrado com sucesso!");
        }

        return "redirect:/ProdutoListar";
    }

    @GetMapping("/ProdutoEditar/{idproduto}")
    public String editar(@PathVariable Integer idproduto, Model model) {
        model.addAttribute("produto", prodService.buscarPorId(idproduto));
        model.addAttribute("categorias", catService.listarTodos());
        model.addAttribute("fornecedores", fornService.listarTodos());
        model.addAttribute("modoEdicao", true);
        return "cadastros/produto/produtoCadastrar";
    }

    @GetMapping("/ProdutoExcluir/{idproduto}")
    public String deletar(@PathVariable Integer idproduto, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = prodService.deletar(idproduto);

        if (resultadoMensagem.contains("excluído com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/ProdutoListar";
    }
}