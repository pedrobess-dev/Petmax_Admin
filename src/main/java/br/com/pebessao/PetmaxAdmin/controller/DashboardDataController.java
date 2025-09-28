package br.com.pebessao.PetmaxAdmin.controller;

import br.com.pebessao.PetmaxAdmin.dto.DashboardData;
import br.com.pebessao.PetmaxAdmin.repository.PromissoriaRepository;
import br.com.pebessao.PetmaxAdmin.repository.VendaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DashboardDataController {

    private final VendaRepository vendaRepository;
    private final PromissoriaRepository promissoriaRepository;

    public DashboardDataController(VendaRepository vendaRepository,
                                   PromissoriaRepository promissoriaRepository) {
        this.vendaRepository = vendaRepository;
        this.promissoriaRepository = promissoriaRepository;
    }

    @GetMapping("/dashboard-data")
    public DashboardData getDashboardData() {
        DashboardData data = new DashboardData();

        data.setTotalVendas((int) vendaRepository.totalVendas());
        data.setValorTotalVendas(vendaRepository.somaValorTotal());
        data.setProdutoMaisVendido(
                vendaRepository.produtoMaisVendido().orElse("Nenhum")
        );
        data.setValorTotalPromissorias(promissoriaRepository.somaValorTotal());

        LocalDate dataInicio = LocalDate.now().minusMonths(6);

        // 🔹 Converter resultado Object[] para DashboardData.VendaMes
        List<DashboardData.VendaMes> vendasMes = vendaRepository.vendasUltimos6Meses(dataInicio)
                .stream()
                .map(r -> new DashboardData.VendaMes(
                        (String) r[0],                // mês (ex: Jan, Feb...)
                        ((Number) r[1]).doubleValue() // valor
                ))
                .collect(Collectors.toList());

        data.setVendasUltimos6Meses(vendasMes);

        return data;
    }
}
