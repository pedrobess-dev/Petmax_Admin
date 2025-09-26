package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Fornecedor;
import br.com.pebessao.PetmaxAdmin.service.FornecedorService;
import br.com.pebessao.PetmaxAdmin.validator.CNPJValidator;
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
public class FornecedorController {
    private final FornecedorService fornService;

    public FornecedorController(FornecedorService fornService) {
        this.fornService = fornService;
    }

    @GetMapping("/FornecedorListar")
    public String listar(Model model) {
        model.addAttribute("fornecedores", fornService.listarTodos());
        return "cadastros/fornecedor/fornecedor";
    }

    @GetMapping("/FornecedorNovo")
    public String novo(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        model.addAttribute("modoEdicao", false);
        return "cadastros/fornecedor/fornecedorCadastrar";
    }

    @PostMapping("/FornecedorSalvar")
    public String salvar(@Valid @ModelAttribute Fornecedor fornecedor, BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cadastros/fornecedor/fornecedorCadastrar";
        }

        if (!CNPJValidator.isCNPJ(fornecedor.getCnpj())) {
            model.addAttribute("fornecedor", fornecedor);
            model.addAttribute("erroCnpj", "CNPJ inválido! Verifique o número.");
            return "cadastros/fornecedor/fornecedorCadastrar";
        }

        boolean cnpjExiste = fornService.cnpjExiste(fornecedor.getCnpj());
        boolean isEdit = fornecedor.getIdFornecedor() != null;

        if (cnpjExiste && (fornecedor.getIdFornecedor() == null || !fornService.buscarPorId(fornecedor.getIdFornecedor()).getCnpj().equals(fornecedor.getCnpj()))) {
            model.addAttribute("fornecedor", fornecedor);
            model.addAttribute("erroCnpj", "CNPJ inválido! CNPJ já cadastrado!");
            return "cadastros/fornecedor/fornecedorCadastrar";
        }

        fornService.salvar(fornecedor);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Fornecedor editado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Fornecedor cadastrado com sucesso!");
        }

        return "redirect:/FornecedorListar";
    }

    @GetMapping("/FornecedorEditar/{idfornecedor}")
    public String editar(@PathVariable Integer idfornecedor, Model model) {
        model.addAttribute("fornecedor", fornService.buscarPorId(idfornecedor));
        model.addAttribute("modoEdicao", true);
        return "cadastros/fornecedor/fornecedorCadastrar";
    }

    @GetMapping("/FornecedorExcluir/{idfornecedor}")
    public String deletar(@PathVariable Integer idfornecedor, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = fornService.deletar(idfornecedor);

        if (resultadoMensagem.contains("excluído com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/FornecedorListar";
    }
}