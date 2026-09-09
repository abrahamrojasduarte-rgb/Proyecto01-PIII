package Sistema.presentation.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import Sistema.logic.CategoriaRecurso;
import Sistema.logic.Funcionario;
import Sistema.logic.Recurso;

import java.util.List;

public class PDFReportGenerator {

    public static void generarReporteFuncionarios(List<Funcionario> lista, String destPath) throws Exception {
        PdfWriter writer = new PdfWriter(destPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph header = new Paragraph("Reporte de Funcionarios")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(header);

        Table table = new Table(new float[]{100f, 200f, 100f});
        table.addHeaderCell("ID / Cédula");
        table.addHeaderCell("Nombre");
        table.addHeaderCell("Teléfono");

        for (Funcionario f : lista) {
            table.addCell(f.getId());
            table.addCell(f.getNombre());
            table.addCell(f.getTelefono());
        }

        document.add(table);
        document.close();
    }

    public static void generarReporteCategorias(List<CategoriaRecurso> lista, String destPath) throws Exception {
        PdfWriter writer = new PdfWriter(destPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph header = new Paragraph("Reporte de Categorías")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(header);

        Table table = new Table(new float[]{100f, 300f});
        table.addHeaderCell("ID");
        table.addHeaderCell("Descripción");

        for (CategoriaRecurso c : lista) {
            table.addCell(String.valueOf(c.getID()));
            table.addCell(c.getDescripcion());
        }

        document.add(table);
        document.close();
    }

    public static void generarReporteRecursos(List<Recurso> lista, String destPath) throws Exception {
        PdfWriter writer = new PdfWriter(destPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph header = new Paragraph("Reporte de Recursos")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(header);

        Table table = new Table(new float[]{100f, 150f, 200f});
        table.addHeaderCell("ID / Activo");
        table.addHeaderCell("Categoría");
        table.addHeaderCell("Descripción");

        for (Recurso r : lista) {
            table.addCell(r.getId());
            table.addCell(r.getCategoria() != null ? r.getCategoria().getDescripcion() : "N/A");
            table.addCell(r.getDescripcion());
        }

        document.add(table);
        document.close();
    }
}
