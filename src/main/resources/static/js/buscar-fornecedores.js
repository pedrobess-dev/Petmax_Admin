// Inicializa o DataTable uma vez ao carregar a página
let fornecedorTable;
document.addEventListener("DOMContentLoaded", () => {
    fornecedorTable = $('#datatable').DataTable({
        "scrollY": "300px",
        "scrollCollapse": true,
        "autoWidth": false,
        "oLanguage": {
            "sProcessing": "Processando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "Nenhum registro encontrado.",
            "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
            "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
            "sSearch": "Buscar:",
            "oPaginate": {
                "sFirst": "Primeiro",
                "sPrevious": "Anterior",
                "sNext": "Seguinte",
                "sLast": "Último"
            }
        }
    });
});

function buscarFornecedor() {
    const cnpj = document.getElementById("cnpj").value.trim();

    if (!cnpj) {
        mostrarAlerta("Digite um CNPJ válido.", "danger");
        return;
    }

    fetch(`/api/fornecedores/buscar/${cnpj}`, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) throw new Error("Erro ao buscar fornecedor");
        return response.json();
    })
    .then(f => {
        fornecedorTable.clear(); // Limpa linhas antigas

        if (!f || !f.estabelecimento?.cnpj) {
            mostrarAlerta("Nenhum fornecedor encontrado.", "danger");
            return;
        }

        fornecedorTable.row.add([
            f.razao_social || '-',
            formatarCNPJ(f.estabelecimento?.cnpj) || '-',
            f.estabelecimento?.email || '-',
            `(${f.estabelecimento?.ddd1 || '-'})${f.estabelecimento?.telefone1 || '-'}`,
            f.estabelecimento?.cep || '-',
            `${f.estabelecimento?.cidade?.nome || '-'}/${f.estabelecimento?.estado?.sigla || '-'}`,
            `${f.estabelecimento?.bairro || '-'}, ${f.estabelecimento?.logradouro || '-'}, ${f.estabelecimento?.numero || ''}`,
            `<button class="btn btn-success btn-sm"
                onclick='salvarFornecedor("${encodeURIComponent(JSON.stringify(f))}")'>
                <i class="fa fa-save"></i> Salvar
            </button>`
        ]).draw();
    })
    .catch(error => {
        mostrarAlerta("Erro ao buscar fornecedor: " + error.message, "danger");
    });
}

function salvarFornecedor(fornecedorJson) {
    const fornecedor = JSON.parse(decodeURIComponent(fornecedorJson));

    const fornecedorPayload = {
        nomeFornecedor: fornecedor.razao_social || "-",
        cnpj: formatarCNPJ(fornecedor.estabelecimento?.cnpj) || "-",
        email: fornecedor.estabelecimento?.email || "-",
        telefone: fornecedor.estabelecimento?.ddd1 && fornecedor.estabelecimento?.telefone1
                    ? `(${fornecedor.estabelecimento.ddd1})${fornecedor.estabelecimento.telefone1}`.substring(0, 14)
                    : "-",
        cep: fornecedor.estabelecimento?.cep || "-",
        cidade: fornecedor.estabelecimento?.cidade?.nome || "-",
        uf: fornecedor.estabelecimento?.estado?.sigla || "-",
        bairro: fornecedor.estabelecimento?.bairro || "-",
        rua: fornecedor.estabelecimento?.logradouro || "-",
        numero: fornecedor.estabelecimento?.numero && fornecedor.estabelecimento?.numero !== "S/NR"
                ? parseInt(fornecedor.estabelecimento?.numero)
                : 0
    };

    fetch(`/api/fornecedores/salvar`, {
        method: "POST",
        headers: {
                "Content-Type": "application/json"
        },
        body: JSON.stringify(fornecedorPayload)
    })
    .then(response => {
    if (!response.ok) {
        return response.text().then(text => {
            throw new Error(text || "Erro desconhecido ao salvar fornecedor.");
        });
    }
    return response.json(); // Se OK, continua lendo o JSON
    })
    .then(data => {
        mostrarAlerta("Fornecedor salvo com sucesso: " + data.nomeFornecedor, "success");
    })
    .catch(error => {
    mostrarAlerta("Erro ao salvar fornecedor: " + error.message, "danger");
    });
}


function mostrarAlerta(mensagem, tipo) {
    const container = document.getElementById("alertContainer");
    container.innerHTML = `
        <div class="alert alert-${tipo} alert-dismissible fade show mt-3" role="alert">
            <span>${mensagem}</span>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>`;
}

function formatarCNPJ(cnpj) {
    if (!cnpj) return '-';
    cnpj = cnpj.replace(/\D/g, ''); // garante só números
    return cnpj.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, "$1.$2.$3/$4-$5");
}
