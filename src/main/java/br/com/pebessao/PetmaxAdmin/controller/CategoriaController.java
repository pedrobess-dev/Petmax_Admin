package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Categoria;
import br.com.pebessao.PetmaxAdmin.service.CategoriaService;
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
public class CategoriaController {
    private final CategoriaService catService;

    public CategoriaController(CategoriaService catService) {
        this.catService = catService;
    }

    @GetMapping("/CategoriaListar")
    public String listar(Model model) {
        model.addAttribute("categorias", this.catService.listarTodos());
        return "cadastros/categoria/categoria";
    }

    @GetMapping("/CategoriaNovo")
    public String novo(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("modoEdicao", false);
        return "cadastros/categoria/categoriaCadastrar";
    }

    @PostMapping("/CategoriaSalvar")
    public String salvar(@Valid @ModelAttribute Categoria categoria, BindingResult result,
                         Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cadastros/categoria/categoriaCadastrar";
        }

        boolean isEdit = categoria.getIdCategoria() != null;
        catService.salvar(categoria);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Categoria editada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Categoria cadastrada com sucesso!");
        }

        return "redirect:/CategoriaListar";
    }

    @GetMapping("/CategoriaEditar/{idcategoria}")
    public String editar(@PathVariable Integer idcategoria, Model model) {
        model.addAttribute("categoria", catService.buscarPorId(idcategoria));
        model.addAttribute("modoEdicao", true);
        return "cadastros/categoria/categoriaCadastrar";
    }

    @GetMapping("/CategoriaExcluir/{idcategoria}")
    public String deletar(@PathVariable Integer idcategoria, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = catService.deletar(idcategoria);

        if (resultadoMensagem.contains("excluída com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/CategoriaListar";
    }
}