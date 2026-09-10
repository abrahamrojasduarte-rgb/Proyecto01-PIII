package Sistema.presentation.estadisticas;

import com.github.lgooddatepicker.components.DatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;

public class viewEstadisticas implements PropertyChangeListener {

    private JPanel panelEstadisticas;
    private JPanel recursosPanel;
    private JPanel actividadesPanel;
    private JPanel fechaPanel;
    private JButton cargarRecursosButton;
    private JButton imprimirRecursosButton;
    private JScrollPane recursosScroll;
    private JTable recursosTable;
    private JPanel graficoRecursos;
    private JPanel fechaDyHPanel;
    private DatePicker desdeRecursosPicker;
    private DatePicker hastaRecursosPicker;
    private JButton cargarActividadesButton;
    private JButton imprimirActividadesButton;
    private DatePicker desdeActividadesPicker;
    private DatePicker hastaActividadesPicker;
    private JScrollPane actividadesScroll;
    private JTable actividadesTable;
    private JPanel graficoActividades;

    private Model model;
    private Controller controller;

    public viewEstadisticas(){
        graficoRecursos.setLayout(new BorderLayout());
        graficoActividades.setLayout(new BorderLayout());

        cargarRecursosButton.addActionListener(e -> {
            try {
                controller.cargarRecursos(desdeRecursosPicker.getDate(), hastaRecursosPicker.getDate());
            } catch (Exception ex){
                JOptionPane.showMessageDialog(panelEstadisticas, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cargarActividadesButton.addActionListener(e -> {
            try{
                controller.cargarActividades(desdeActividadesPicker.getDate(), hastaActividadesPicker.getDate());
            } catch (Exception ex){
                JOptionPane.showMessageDialog(panelEstadisticas, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        imprimirRecursosButton.addActionListener(e -> {
            String ruta = pedirRuta("recursos.pdf");
            if(ruta == null){
                return;
            }
            try{
                controller.imprimirRecursos(ruta);
                JOptionPane.showMessageDialog(panelEstadisticas,"Reporte generado", "Informacion",JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(panelEstadisticas,ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        imprimirActividadesButton.addActionListener(e->{
            String ruta = pedirRuta("actividades.pdf");
            if(ruta == null){
                return;
            }
            try{
                controller.imprimirActividades(ruta);
                JOptionPane.showMessageDialog(panelEstadisticas, "Reporte generado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(panelEstadisticas, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller){
        this.controller =controller;
    }

    public JPanel getPanelEstadisticas(){
        return panelEstadisticas;
    }

    private String pedirRuta(String nombre){
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(nombre));

        if(chooser.showSaveDialog(panelEstadisticas) != JFileChooser.APPROVE_OPTION){
            return null;
        }

        String ruta = chooser.getSelectedFile().getAbsolutePath();
        if(!ruta.toLowerCase().endsWith(".pdf")){
            ruta = ruta + ".pdf";
        }
        return ruta;
    }

    private void mostrarGrafico(JPanel contenedor, List<String[]> datos, String titulo, String ejeX, String serie){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for(String[] fila : datos){
            dataset.addValue(Integer.parseInt(fila[1]), serie, fila[0]);
        }

        JFreeChart chart = ChartFactory.createBarChart(titulo,ejeX,"Cantidad",dataset,PlotOrientation.VERTICAL,true, true,false);
        ChartPanel chartPanel = new ChartPanel(chart);

        contenedor.removeAll();
        contenedor.add(chartPanel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch(evt.getPropertyName()){
            case Model.RECURSOS:
                List<String[]> recursos = model.getRecursos();
                String[][] datosRecurso = new String[recursos.size()][];
                for (int i = 0; i < recursos.size(); i++) {
                    datosRecurso[i] = recursos.get(i);
                }
                recursosTable.setModel(new DefaultTableModel(datosRecurso, new String[]{"Categoria", "Cantidad"}));
                mostrarGrafico(graficoRecursos, recursos, "Recursos usados", "Categoria", "Recurso");
                break;
            case Model.ACTIVIDADES:
                List<String[]> actividades = model.getActividades();
                String[][] datosActividades = new String[actividades.size()][];
                for (int i = 0; i < actividades.size(); i++) {
                    datosActividades[i] = actividades.get(i);
                }
                recursosTable.setModel(new DefaultTableModel(datosActividades, new String[]{"Categoria", "Cantidad"}));
                mostrarGrafico(graficoActividades, actividades, "Actividades realizadas", "Semana", "Semana");
                break;
        }
        panelEstadisticas.revalidate();
        panelEstadisticas.repaint();
    }
}
