function formatarDadosParaPrompt(vendasUltimos6Meses, produtoMaisVendido) {
    let historico = vendasUltimos6Meses.map(v => `${v.mes}: R$${v.valor.toFixed(2)}`).join(", ");
    return `Com base no seguinte histórico de vendas mensais (Últimos 6 meses): [${historico}] e sabendo que o produto atualmente mais vendido é "${produtoMaisVendido}", faça uma previsão de vendas para o próximo mês. Estime o valor total da receita em Reais (R$) e preveja qual produto ou categoria de produto será a mais vendida.`;
}

async function fetchPrevisaoVendas(userQuery) {
    document.getElementById("forecastRevenue").textContent = "Calculando...";
    document.getElementById("topProductPrediction").textContent = "Analisando...";
    document.getElementById("predictionSummary").textContent = "Aguarde a análise da inteligência artificial...";

    const systemPrompt = "Você é um analista financeiro e de vendas. Sua tarefa é analisar o histórico de vendas fornecido e criar uma previsão concisa para o próximo mês. Use apenas o formato JSON especificado. Não use pontuação no final das sentenças do resumo.";

    const payload = {
        contents: [{ parts: [{ text: userQuery }] }],
        systemInstruction: { parts: [{ text: systemPrompt }] },
        generationConfig: {
            responseMimeType: "application/json",
            responseSchema: {
                type: "OBJECT",
                properties: {
                    forecastRevenue: {
                        type: "STRING",
                        description: "Valor total da receita prevista para o próximo mês, formatado em R$ com separador de milhares."
                    },
                    topProductPrediction: {
                        type: "STRING",
                        description: "O nome ou categoria do produto com maior previsão de vendas."
                    },
                    predictionSummary: {
                        type: "STRING",
                        description: "Um resumo conciso de uma frase sobre a previsão, citando tendências ou crescimento esperado."
                    }
                },
                required: ["forecastRevenue", "topProductPrediction", "predictionSummary"],
                propertyOrdering: ["forecastRevenue", "topProductPrediction", "predictionSummary"]
            }
        }
    };

    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error("Falha na API da Gemini: " + errorText);
        }

        const result = await response.json();
        const jsonText = result.candidates?.[0]?.content?.parts?.[0]?.text;

        if (!jsonText) throw new Error("Resposta da IA vazia ou mal formatada.");

        const data = JSON.parse(jsonText);

        // Exibir a previsão no dashboard
        document.getElementById("forecastRevenue").textContent = data.forecastRevenue;
        document.getElementById("topProductPrediction").textContent = data.topProductPrediction;
        document.getElementById("predictionSummary").textContent = data.predictionSummary;

    } catch (error) {
        console.error("Erro ao gerar previsão de IA:", error);
        document.getElementById("forecastRevenue").textContent = "Erro na previsão";
        document.getElementById("topProductPrediction").textContent = "Erro na previsão";
        document.getElementById("predictionSummary").textContent = "Não foi possível carregar a previsão. Verifique o console para detalhes.";
    }
}

document.addEventListener("DOMContentLoaded", function() {
    fetch("dashboard-data")
        .then(response => response.json())
        .then(data => {
            document.getElementById("totalVendas").textContent = data.totalVendas;
            document.getElementById("valorTotalVendas").textContent = "R$ " + data.valorTotalVendas.toFixed(2);
            document.getElementById("produtoMaisVendido").textContent = data.produtoMaisVendido;
            document.getElementById("valorTotalPromissorias").textContent = "R$ " + data.valorTotalPromissorias.toFixed(2);

            // Processando dados dos últimos 6 meses
            let meses = [];
            let valores = [];
            data.vendasUltimos6Meses.forEach(mesVenda => {
                meses.push(mesVenda.mes);
                valores.push(mesVenda.valor);
            });

            // Criando gráfico dos últimos 6 meses
            new Chart(document.getElementById("graficoVendasUltimos6Meses"), {
                type: "line",
                data: {
                    labels: meses,
                    datasets: [{
                        label: "Valor Total de Vendas",
                        data: valores,
                        backgroundColor: "rgba(75, 0, 130, 0.2)",
                        borderColor: "rgba(75, 0, 130, 1)",
                        borderWidth: 2,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { display: true }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            // Criando gráfico de valores
            new Chart(document.getElementById("graficoValor"), {
                type: "pie",
                data: {
                    labels: ["Valor Total", "Valor Promissória"],
                    datasets: [{
                        data: [data.valorTotalVendas, data.valorTotalPromissorias],
                        backgroundColor: ["#6610f2", "#dc3545"]
                    }]
                },
                options: {
                    responsive: true
                }
            });

            // INICIA A PREVISÃO DE IA AQUI:
            const userQuery = formatarDadosParaPrompt(data.vendasUltimos6Meses, data.produtoMaisVendido);
            fetchPrevisaoVendas(userQuery);

        })
        .catch(error => console.error("Erro ao carregar dados do dashboard:", error));
});
