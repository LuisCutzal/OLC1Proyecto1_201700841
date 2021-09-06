/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import com.mycompany.proyecto1_2021s2.Archivo;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import com.mycompany.proyecto1_2021s2.Ventana;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sandr
 */
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.category.DefaultCategoryDataset;


public class GraficaLineas {
    public LinkedList<Caracteristica> entraeda;
    String nombreArchivo;
    String tituloGrafica;
    public GraficaLineas(LinkedList<Caracteristica> lista){
        this.entraeda=lista;
    }
    
    
    public void Valores(){
    for(Caracteristica carac: this.entraeda){
        switch(carac.tipo){
            case 0:// el titulo
                System.out.println("titulo->"+carac.valor.valor);
                if(carac.valor.tipo==4){
                    this.tituloGrafica=carac.valor.valor.toString();
                }
            case 5: // nombre del archivo
                System.out.println("nombre del Archivo->"+carac.valor.valor);
                if(carac.valor.tipo==4){
                    this.nombreArchivo=carac.valor.valor.toString();
                }
            }
        }
    }

    
    public void generar_graficaLineas(){
        DefaultCategoryDataset dataset= new DefaultCategoryDataset();
        JFreeChart LineasG = ChartFactory.createLineChart(
                "",
                "ArchivosInternos",
                "Puntaje",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        for(Archivo arch: Ventana.datos_archivos){
            if(arch.getUbi()=="A" && arch.getNombreArchivo().equals(this.nombreArchivo)){
                dataset.addValue(arch.getListaVariables().size(),"PROYECTO A","variables");
                dataset.addValue(arch.getListaClases().size(),"PROYECTO A","clases");
                dataset.addValue(arch.getListaMetodos().size(),"PROYECTO A","metodos");
           }
           if(arch.getUbi()=="B" && arch.getNombreArchivo().equals(this.nombreArchivo)){
                dataset.addValue(arch.getListaVariables().size(),"PROYECTO B","variables");
                dataset.addValue(arch.getListaClases().size(),"PROYECTO B","clases");
                dataset.addValue(arch.getListaMetodos().size(),"PROYECTO B","metodos");
           }
        }
        ChartFrame frame = new ChartFrame("Grafica de Lineas", LineasG);
            frame.pack();
            frame.setVisible(true);
    }
    

}