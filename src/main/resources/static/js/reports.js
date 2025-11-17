let relatorioSelecionado = null;

// Quando o usuário clica no card
function gerarRelatorio(tipo) {
    relatorioSelecionado = tipo;

    const nomes = {
        "vendas_mensais": "Vendas Mensais",
        "produtos_vendidos": "Produtos Populares",
        "promissorias_aberto": "Promissórias em Aberto",
        "clientes_dividas": "Dados do Dashboard"
    };

    document.getElementById("reportNamePlaceholder").innerText = nomes[tipo];

    $("#reportModal").modal('show');
}

// Botão "Gerar PDF"
function downloadRelatorio() {
    if (!relatorioSelecionado) return;

    window.location.href = `/Relatorios/${relatorioSelecionado}/pdf`;

    $("#reportModal").modal('hide');
}

// Botão "Imprimir"
function imprimirRelatorio() {
    if (!relatorioSelecionado) return;

    window.open(`/Relatorios/${relatorioSelecionado}/pdf`, "_blank");

    $("#reportModal").modal('hide');
}
