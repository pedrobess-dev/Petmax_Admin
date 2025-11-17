package br.com.pebessao.PetmaxAdmin.repository;

import br.com.pebessao.PetmaxAdmin.model.Promissoria;
import br.com.pebessao.PetmaxAdmin.model.Venda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RelatorioRepository extends CrudRepository<Venda, Integer> {
    @Query(value =
            "SELECT TO_CHAR(v.dataVenda, 'MM/YYYY') AS mes_ano, " +
                    "SUM(v.valorVenda) AS total_vendas, " +
                    "SUM(v.qtdVendida) AS quantidade " +
                    "FROM Venda v " +
                    "GROUP BY mes_ano " +
                    "ORDER BY MIN(v.dataVenda);",
            nativeQuery = true)
    List<Object[]> buscarVendasMensais();

    @Query("SELECT p.nomeProduto, SUM(v.qtdVendida), SUM(v.qtdVendida * p.preco) " +
            "FROM Venda v JOIN v.produto p " +
            "GROUP BY p.nomeProduto ORDER BY SUM(v.qtdVendida) DESC")
    List<Object[]> buscarProdutosPopulares();

    @Query("SELECT p FROM Promissoria p " +
            "WHERE p.status = 'NÃO PAGO' OR p.dataValidade < CURRENT_DATE " +
            "ORDER BY p.dataValidade ASC")
    List<Promissoria> buscarPromissoriasNaoPagasOuVencidas();
}
