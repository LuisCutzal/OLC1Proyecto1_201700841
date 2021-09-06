/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repos;

import java.util.LinkedList;
import olc1proyecto1.principalWindow;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import olc1proyecto1.Archivo;
/**
 *
 * @author ASUS
 */
public class GLineas {
    
    public LinkedList<Caracteristica> entraeda;
    String nombreArchivo;
    String tituloGrafica;
    
    public GLineas(LinkedList<Caracteristica> inn){
        
        this.entraeda=inn;
    
    
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
    

    public void createLineGraph(){
    
        System.out.println("creandoLagrafica");
        JFreeChart Linchart = ChartFactory.createLineChart("","ArchivosInternos","Puntaje",createDataset(),PlotOrientation.VERTICAL,true,true,false);
        ChartFrame frame = new ChartFrame("Grafica de Lineas", Linchart);
            frame.pack();
            frame.setVisible(true);
        
    
    }
    
    private DefaultCategoryDataset createDataset(){
       
        DefaultCategoryDataset dataset= new DefaultCategoryDataset();
       
        for(Archivo arch: principalWindow.datos_archivos){
        
           if(arch.getUbicacion()==1 && arch.getNombreArchivo().equals(this.nombreArchivo)){
                     dataset.addValue(arch.getListaVariables().size(),"PROYECTO A","variables");
                    dataset.addValue(arch.getListaClases().size(),"PROYECTO A","clases");
                   dataset.addValue(arch.getListaMetdos().size(),"PROYECTO A","metodos");
           }
            
           if(arch.getUbicacion()==2 && arch.getNombreArchivo().equals(this.nombreArchivo)){
                     dataset.addValue(arch.getListaVariables().size(),"PROYECTO B","variables");
                    dataset.addValue(arch.getListaClases().size(),"PROYECTO B","clases");
                   dataset.addValue(arch.getListaMetdos().size(),"PROYECTO B","metodos");
           }
        }
        
    
       
       
    return dataset;
    }
}
