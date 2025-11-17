package br.com.pebessao.PetmaxAdmin.service;

import br.com.pebessao.PetmaxAdmin.model.Promissoria;
import br.com.pebessao.PetmaxAdmin.repository.RelatorioRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.net.URL;
import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    public void gerarVendasMensais(HttpServletResponse response) throws Exception {

        List<Object[]> dados = relatorioRepository.buscarVendasMensais();

        // Configura resposta HTTP para download
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","attachment; filename=vendas_mensais.pdf");

        // Cria documento PDF (iText 7)
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        Table cabecalho = new Table(UnitValue.createPercentArray(new float[]{1, 4}));
        cabecalho.setWidth(UnitValue.createPercentValue(100));

        URL logoUrl = getClass().getResource("/static/img/PetAdmin - logo.jpg");
        if (logoUrl == null) {
            throw new RuntimeException("Logo não encontrada no classpath!");
        }

        ImageData logoData = ImageDataFactory.create(logoUrl.toURI().toString());
        Image logo = new Image(logoData);

        logo.scaleToFit(100, 100);
        logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.LEFT);
        Cell cellLogo = new Cell().add(logo);
        cellLogo.setBorder(Border.NO_BORDER);
        cellLogo.setHorizontalAlignment(HorizontalAlignment.LEFT);
        cabecalho.addCell(cellLogo);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Cell cellNome = new Cell();
        cellNome.add(new Paragraph("Petmax Admin - Sistema de Gerenciamento de Estoque para Petshop")
                .setFont(font).setFontSize(18));
        cellNome.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cellNome.setBorder(Border.NO_BORDER);
        cabecalho.addCell(cellNome);

        document.add(cabecalho);
        document.add(new Paragraph("\n"));

        // Título
        Paragraph titulo = new Paragraph("Relatório de Vendas Mensais")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(titulo);

        // Tabela (3 colunas)
        Table tabela = new Table(UnitValue.createPercentArray(new float[]{3, 3, 3}));
        tabela.setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho
        tabela.addHeaderCell(new Cell().add(new Paragraph("Mês/Ano").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Total Vendido (R$)").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Quantidade Vendida").setBold()));

        // Linhas
        for (Object[] row : dados) {
            tabela.addCell(row[0].toString());
            tabela.addCell(row[1].toString());
            tabela.addCell(row[2].toString());
        }

        document.add(tabela);

        document.close();
    }

    public void gerarProdutosPopulares(HttpServletResponse response) throws Exception {

        List<Object[]> dados = relatorioRepository.buscarProdutosPopulares();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=produtos_populares.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Table cabecalho = new Table(UnitValue.createPercentArray(new float[]{1, 4}));
        cabecalho.setWidth(UnitValue.createPercentValue(100));

        URL logoUrl = getClass().getResource("/static/img/PetAdmin - logo.jpg");
        if (logoUrl == null) {
            throw new RuntimeException("Logo não encontrada no classpath!");
        }

        ImageData logoData = ImageDataFactory.create(logoUrl.toURI().toString());
        Image logo = new Image(logoData);

        logo.scaleToFit(100, 100);
        logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.LEFT);
        Cell cellLogo = new Cell().add(logo);
        cellLogo.setBorder(Border.NO_BORDER);
        cellLogo.setHorizontalAlignment(HorizontalAlignment.LEFT);
        cabecalho.addCell(cellLogo);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Cell cellNome = new Cell();
        cellNome.add(new Paragraph("Petmax Admin - Sistema de Gerenciamento de Estoque para Petshop")
                .setFont(font).setFontSize(18));
        cellNome.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cellNome.setBorder(Border.NO_BORDER);
        cabecalho.addCell(cellNome);

        document.add(cabecalho);
        document.add(new Paragraph("\n"));

        Paragraph titulo = new Paragraph("Relatório de Produtos Mais Populares\n\n")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18);

        document.add(titulo);

        Table tabela = new Table(UnitValue.createPercentArray(new float[]{3, 3, 3}));
        tabela.setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho
        tabela.addHeaderCell(new Cell().add(new Paragraph("Produto").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Qtd. Vendida").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Total Vendido (R$)").setBold()));

        // Dados
        for (Object[] row : dados) {
            tabela.addCell((row[0].toString()));
            tabela.addCell((row[1].toString()));
            tabela.addCell((row[2].toString()));
        }

        document.add(tabela);
        document.close();
    }

    public void gerarPromissoriasAberto(HttpServletResponse response) throws Exception {

        List<Promissoria> dados = relatorioRepository.buscarPromissoriasNaoPagasOuVencidas();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=promissorias_aberto.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Table cabecalho = new Table(UnitValue.createPercentArray(new float[]{1, 4}));
        cabecalho.setWidth(UnitValue.createPercentValue(100));

        URL logoUrl = getClass().getResource("/static/img/PetAdmin - logo.jpg");
        if (logoUrl == null) {
            throw new RuntimeException("Logo não encontrada no classpath!");
        }

        ImageData logoData = ImageDataFactory.create(logoUrl.toURI().toString());
        Image logo = new Image(logoData);

        logo.scaleToFit(100, 100);
        logo.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.LEFT);
        Cell cellLogo = new Cell().add(logo);
        cellLogo.setBorder(Border.NO_BORDER);
        cellLogo.setHorizontalAlignment(HorizontalAlignment.LEFT);
        cabecalho.addCell(cellLogo);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Cell cellNome = new Cell();
        cellNome.add(new Paragraph("Petmax Admin - Sistema de Gerenciamento de Estoque para Petshop")
                .setFont(font).setFontSize(18));
        cellNome.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cellNome.setBorder(Border.NO_BORDER);
        cabecalho.addCell(cellNome);

        document.add(cabecalho);
        document.add(new Paragraph("\n"));

        Paragraph titulo = new Paragraph("Relatório de Promissórias em Aberto\n\n")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18);

        document.add(titulo);

        Table tabela = new Table(UnitValue.createPercentArray(new float[]{2, 3, 3, 2, 2, 3, 3}));
        tabela.setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho
        tabela.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Cliente").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Produto").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Qtd. Vendida").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Total Vendido (R$)").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Status").setBold()));
        tabela.addHeaderCell(new Cell().add(new Paragraph("Data de Validade").setBold()));

        // Dados
        for (Promissoria p : dados) {
            tabela.addCell(String.valueOf(p.getIdPromissoria()));
            tabela.addCell(p.getCliente().getNomeCliente());
            tabela.addCell(p.getProduto().getNomeProduto());
            tabela.addCell(String.valueOf(p.getQtdVendida()));
            tabela.addCell(String.valueOf(p.getValor()));
            tabela.addCell(p.getStatus());
            tabela.addCell(p.getDataValidade().toString());
        }

        document.add(tabela);
        document.close();
    }
}
