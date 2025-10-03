// Mapeamento dos tipos de relatório para nomes amigáveis
const reportNames = {
    'vendas_periodo': 'Vendas por Período',
    'produtos_vendidos': 'Produtos Populares',
    'promissorias_aberto': 'Promissórias Pendentes',
    'dados_dashboard': 'Dados do Dashboard e Previsão'
};

let currentReportType = '';

/**
 * Abre o modal de ação (Gerar/Imprimir) para relatórios que não precisam de filtro de data.
 * @param {string} reportType - O tipo de relatório selecionado.
 */
function abrirModalAcao(reportType) {
    currentReportType = reportType;
    const name = reportNames[reportType] || 'Relatório Desconhecido';

    // Atualiza o placeholder e exibe o modal
    document.getElementById('reportNamePlaceholder').textContent = name;
    $('#actionModal').modal('show');
}

/**
 * Abre o modal de filtro (datas) para relatórios que exigem seleção de período.
 * @param {string} reportType - O tipo de relatório selecionado.
 */
function abrirModalFiltro(reportType) {
    currentReportType = reportType;
    const name = reportNames[reportType] || 'Relatório Desconhecido';

    // Atualiza o título do modal de filtro
    document.getElementById('filterReportName').textContent = name;
    $('#filterModal').modal('show');
}

/**
 * Processa a geração de relatório com base nas datas selecionadas (do Modal de Filtro).
 */
function processarRelatorioComFiltro() {
    const dataInicio = document.getElementById('dataInicio').value;
    const dataFim = document.getElementById('dataFim').value;

    if (!dataInicio || !dataFim) {
        alert("Por favor, preencha as datas de início e fim.");
        return;
    }

    $('#filterModal').modal('hide');

    // Lógica real de integração com a API Spring para geração de PDF com datas
    console.log(`Gerando Relatório de ${reportNames[currentReportType]} de ${dataInicio} até ${dataFim}`);
    // Exemplo de chamada API (Você deve implementar o endpoint Spring)
    // fetch(`/relatorios/gerar/${currentReportType}?dataInicio=${dataInicio}&dataFim=${dataFim}`);

    alert(`Relatório "${reportNames[currentReportType]}" (de ${dataInicio} a ${dataFim}) gerado com sucesso!`);
}

/**
 * Função para simular o download do PDF (para relatórios simples).
 * Esta função deve chamar o endpoint Spring que retorna o arquivo PDF.
 * @param {string} reportType - O tipo de relatório a ser baixado.
 */
function downloadRelatorio(reportType) {
    // Lógica real de integração com a API Spring para geração de PDF (sem datas)
    console.log(`Simulando geração e download do relatório: ${reportNames[reportType]}`);
    $('#actionModal').modal('hide');

    // Exemplo de chamada API
    // window.location.href = `/relatorios/gerar/${reportType}`;

    alert(`Relatório "${reportNames[reportType]}" gerado com sucesso!`);
}

/**
 * Função para simular a impressão (para relatórios simples).
 * @param {string} reportType - O tipo de relatório a ser impresso.
 */
function imprimirRelatorio(reportType) {
    // Lógica real de impressão (usando window.print() ou API Spring para impressão direta)
    console.log(`Simulando impressão do relatório: ${reportNames[reportType]}`);
    $('#actionModal').modal('hide');
    alert(`Enviando Relatório "${reportNames[reportType]}" para impressão...`);
}
