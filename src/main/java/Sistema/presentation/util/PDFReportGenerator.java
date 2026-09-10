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
import Sistema.logic.Reserva;

import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class PDFReportGenerator {

    private static final DateTimeFormatter F_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter F_HORA = DateTimeFormatter.ofPattern("HH:mm");

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

    public static void generarReporteReservas(List<Reserva> lista, String destPath) throws Exception {
        PdfWriter writer = new PdfWriter(destPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph header = new Paragraph("Listado de Reservas").setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER);
        document.add(header);

        Table table = new Table(new float[]{80f, 150f, 80f, 90f, 130f, 80f});
        table.addHeaderCell("Id");
        table.addHeaderCell("Actividad");
        table.addHeaderCell("Fecha");
        table.addHeaderCell("Horario");
        table.addHeaderCell("Recursos");
        table.addHeaderCell("Estado");

        for (Reserva r : lista) {
            table.addCell(String.format("RES-%06d", r.getId()));
            table.addCell(r.getActividad() != null ? r.getActividad() : "");
            table.addCell(r.getFecha() != null ? r.getFecha().format(F_FECHA) : "");

            String horario = "";
            if (r.getHoraInicia() != null && r.getHoraTermina() != null) {
                horario = r.getHoraInicia().format(F_HORA) + " - " + r.getHoraTermina().format(F_HORA);
            }
            table.addCell(horario);

            String recursos = "";
            if (r.getRecursos() != null && !r.getRecursos().isEmpty()) {
                recursos = r.getRecursos().stream()
                        .map(Recurso::getId)
                        .collect(Collectors.joining(", "));
            }
            table.addCell(recursos);

            String estado = "ACTIVA";
            if (r.getFecha() != null && r.getHoraTermina() != null) {
                LocalDateTime fin = LocalDateTime.of(r.getFecha(), r.getHoraTermina());
                if (fin.isBefore(LocalDateTime.now())) estado = "FINALIZADA";
            }
            table.addCell(estado);
        }

        document.add(table);
        document.close();
    }

    public static void generarReporteMatriz(String titulo, String[] columnas, java.util.List<String[]> filas, String desPath) throws Exception{
        PdfWriter writer = new PdfWriter(desPath);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4.rotate());
        Document document = new Document(pdf);

        Paragraph header = new Paragraph(titulo).setFontSize(16).setBold().setTextAlignment(TextAlignment.CENTER);
        document.add(header);
        Table table = new Table(columnas.length);

        for(String columna : columnas){
            table.addHeaderCell(columna);
        }
        for (String[] fila : filas){
            for(String celda : fila){
                table.addCell(celda == null ? "" :celda);
            }
        }
        document.add(table);
        document.close();
    }
}

