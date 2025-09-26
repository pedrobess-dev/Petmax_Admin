package br.com.pebessao.PetmaxAdmin.repository;

import br.com.pebessao.PetmaxAdmin.dto.DashboardData;
import br.com.pebessao.PetmaxAdmin.model.Cliente;
import br.com.pebessao.PetmaxAdmin.model.Produto;
import br.com.pebessao.PetmaxAdmin.model.Venda;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VendaRepository extends JpaRepository<Venda, String> {
    @Query(
            value = "SELECT COALESCE(SUM(v.valorVenda), 0) FROM venda v",
            nativeQuery = true
    )
    double somaValorTotal();

    @Query(
            value = "SELECT COUNT(v) FROM venda v",
            nativeQuery = true
    )
    long totalVendas();

    @Query(
            value = "SELECT p.nomeproduto FROM venda v JOIN produto p ON v.idproduto = p.idproduto GROUP BY p.nomeproduto ORDER BY COUNT(v.idproduto) DESC LIMIT 1",
            nativeQuery = true
    )
    Optional<String> produtoMaisVendido();

    @Query("SELECT new br.com.pebessao.PetmaxAdmin.dto.DashboardData$VendaMes(" +
            "FUNCTION('to_char', v.dataVenda, 'Mon'), " +
            "SUM(v.valorVenda)) " +
            "FROM Venda v " +
            "WHERE v.dataVenda >= :dataInicio " +
            "GROUP BY FUNCTION('to_char', v.dataVenda, 'Mon'), FUNCTION('date_trunc', 'month', v.dataVenda) " +
            "ORDER BY FUNCTION('date_trunc', 'month', v.dataVenda)")
    List<DashboardData.VendaMes> vendasUltimos6Meses(@Param("dataInicio") LocalDate dataInicio);


    long countByProduto(Produto produto);
    long countByCliente(Cliente cliente);
}