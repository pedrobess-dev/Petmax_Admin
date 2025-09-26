package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.model.Venda;
import br.com.pebessao.PetmaxAdmin.service.ClienteService;
import br.com.pebessao.PetmaxAdmin.service.ProdutoService;
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
public class VendaController {
    private final VendaService venService;
    private final ClienteService cliService;
    private final ProdutoService prodService;

    public VendaController(VendaService venService, ClienteService cliService, ProdutoService prodService) {
        this.venService = venService;
        this.cliService = cliService;
        this.prodService = prodService;
    }

    @GetMapping("/VendaListar")
    public String listar(Model model) {
        model.addAttribute("vendas", venService.listarTodos());
        return "cadastros/venda/venda";
    }

    @GetMapping("/VendaNovo")
    public String novo(Model model) {
        model.addAttribute("venda", new Venda());
        model.addAttribute("clientes", cliService.listarTodos());
        model.addAttribute("produtos", prodService.listarTodos());
        model.addAttribute("modoEdicao", false);
        return "cadastros/venda/vendaCadastrar";
    }

    @PostMapping("/VendaSalvar")
    public String salvar(@Valid @ModelAttribute Venda venda, BindingResult result, Model model,
                         RedirectAttributes redirectAttributes) {
        model.addAttribute("clientes", cliService.listarTodos());
        model.addAttribute("produtos", prodService.listarTodos());

        if (result.hasErrors()) {
            return "cadastros/venda/vendaCadastrar";
        }

        boolean isEdit = venda.getIdVenda() != null;
        venService.salvar(venda);

        if (isEdit) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Venda editada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Venda cadastrada com sucesso!");
        }

        return "redirect:/VendaListar";
    }

    @GetMapping("/VendaEditar/{idvenda}")
    public String editar(@PathVariable Integer idvenda, Model model) {
        model.addAttribute("venda", venService.buscarPorId(idvenda));
        model.addAttribute("clientes", cliService.listarTodos());
        model.addAttribute("produtos", prodService.listarTodos());
        model.addAttribute("modoEdicao", true);
        return "cadastros/venda/vendaCadastrar";
    }

    @GetMapping("/VendaExcluir/{idvenda}")
    public String deletar(@PathVariable Integer idvenda, RedirectAttributes redirectAttributes) {
        String resultadoMensagem = venService.deletar(idvenda);

        if (resultadoMensagem.contains("excluída com sucesso")) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", resultadoMensagem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", resultadoMensagem);
        }

        return "redirect:/VendaListar";
    }
}