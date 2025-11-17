package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.service.RelatorioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/Relatorios")
    public String relatorio() {
        return "administrativos/relatorio/relatorio";
    }

    @GetMapping("/Relatorios/vendas_mensais/pdf")
    public void gerarRelatorioVendasMensais(HttpServletResponse response) throws Exception {
        relatorioService.gerarVendasMensais(response);
    }

    @GetMapping("/Relatorios/produtos_populares/pdf")
    public void gerarProdutosPopulares(HttpServletResponse response) throws Exception {
        relatorioService.gerarProdutosPopulares(response);
    }

    @GetMapping("/Relatorios/promissorias_aberto/pdf")
    public void gerarPromissoriasAberto(HttpServletResponse response) throws Exception {
        relatorioService.gerarPromissoriasAberto(response);
    }
}
