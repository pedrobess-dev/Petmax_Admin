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
            'Authorization': 'Basic ' + btoa('admin:admin'),
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
            f.estabelecimento?.cnpj || '-',
            f.estabelecimento?.email || '-',
            f.estabelecimento?.telefone1 || '-',
            f.estabelecimento?.cep || '-',
            `${f.estabelecimento?.cidade?.nome || '-'}/${f.estabelecimento?.estado?.sigla || '-'}`,
            `${f.estabelecimento?.logradouro || '-'} ${f.estabelecimento?.numero || ''}`,
            `<button class="btn btn-success btn-sm"
                onclick='salvarFornecedor(${encodeURIComponent(JSON.stringify(f))})'>
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
        razaoSocial: fornecedor.razao_social,
        cnpj: fornecedor.estabelecimento?.cnpj,
        email: fornecedor.estabelecimento?.email || null,
        telefone: fornecedor.estabelecimento?.telefone1 || null,
        cep: fornecedor.estabelecimento?.cep || null,
        cidade: fornecedor.estabelecimento?.cidade || null,
        uf: fornecedor.estabelecimento?.estado || null,
        logradouro: fornecedor.estabelecimento?.logradouro || null,
        numero: fornecedor.estabelecimento?.numero || null,
        bairro: fornecedor.estabelecimento?.bairro || null
    };

    fetch(`/api/fornecedores/salvar`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(fornecedorPayload)
    })
    .then(response => {
        if (!response.ok) throw new Error("Erro ao salvar fornecedor");
        return response.json();
    })
    .then(data => {
        mostrarAlerta("Fornecedor salvo com sucesso: " + data.razao_social, "success");
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
