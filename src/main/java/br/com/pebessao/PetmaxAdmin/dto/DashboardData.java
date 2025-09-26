package br.com.pebessao.PetmaxAdmin.dto;

import java.util.List;

public class DashboardData {
    private int totalVendas;
    private double valorTotalVendas;
    private String produtoMaisVendido;
    private double valorTotalPromissorias;
    private List<VendaMes> vendasUltimos6Meses;

    public int getTotalVendas() {
        return this.totalVendas;
    }

    public void setTotalVendas(int totalVendas) {
        this.totalVendas = totalVendas;
    }

    public double getValorTotalVendas() {
        return this.valorTotalVendas;
    }

    public void setValorTotalVendas(double valorTotalVendas) {
        this.valorTotalVendas = valorTotalVendas;
    }

    public String getProdutoMaisVendido() {
        return this.produtoMaisVendido;
    }

    public void setProdutoMaisVendido(String produtoMaisVendido) {
        this.produtoMaisVendido = produtoMaisVendido;
    }

    public double getValorTotalPromissorias() {
        return this.valorTotalPromissorias;
    }

    public void setValorTotalPromissorias(double valorTotalPromissorias) {
        this.valorTotalPromissorias = valorTotalPromissorias;
    }

    public List<VendaMes> getVendasUltimos6Meses() {
        return this.vendasUltimos6Meses;
    }

    public void setVendasUltimos6Meses(List<VendaMes> vendasUltimos6Meses) {
        this.vendasUltimos6Meses = vendasUltimos6Meses;
    }

    public static class VendaMes {
        private String mes;
        private double valor;

        public VendaMes(String mes, double valor) {
            this.mes = mes;
            this.valor = valor;
        }

        public String getMes() {
            return this.mes;
        }

        public void setMes(String mes) {
            this.mes = mes;
        }

        public double getValor() {
            return this.valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }
}