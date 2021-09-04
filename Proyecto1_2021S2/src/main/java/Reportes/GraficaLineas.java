/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;

import com.mycompany.proyecto1_2021s2.Ventana;
import com.mycompany.proyecto1_2021s2.Variables;
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
import org.jfree.data.xy.XYSeriesCollection;


public class GraficaLineas {
    
    public LinkedList<Caracteristica> lista_caracteristica;
    String etiqueta_archivo ;
    String ety;
    String titulo_grafica ;
    public LinkedList<String> ejex = new LinkedList<>();
    public LinkedList<Object> valores = new LinkedList<>();
    
    public GraficaLineas(LinkedList<Caracteristica> lista_caracteristica){
        this.lista_caracteristica = lista_caracteristica;
    }
    
    public void valores(){
        for(Caracteristica caract : this.lista_caracteristica){
            
            
            switch(caract.tipo){
                case 0: //titulo  
                    System.out.println("titulo -> " + caract.valor.valor);
                    if(caract.valor.tipo == 4){
                        titulo_grafica = caract.valor.valor.toString();
                    }else if(caract.valor.tipo == 3){
                        for(Variables var : Ventana.variables_FCA){
                            if(var.getIdentificador().equalsIgnoreCase(caract.valor.valor.toString())){
                                Valor val = (Valor)var.valor;
                                titulo_grafica = val.valor.toString();
                            }
                        }
                     
                    }
                    
                    break;
                case 2: //valores
                    System.out.println("valores -> ");
                    for(Valor val : caract.lista_valores){
                        if(val.tipo == 1 || val.tipo == 2 || val.tipo == 4){ 
                            this.valores.add(val.valor);
                        }else if(val.tipo == 3){ //id
                            //se recomienda tener una lista de variables donde se guarde su nombre y valor 
                            for(Variables variable: Ventana.variables_FCA){
                                if(variable.getIdentificador().equalsIgnoreCase(val.valor.toString())){
                                    this.valores.add(variable.getValor());
                                }
                            }
                        }
                    }
                    break;
                case 5: //archivo
                    System.out.println("Archivo -> " + caract.valor.valor);
                    if(caract.valor.tipo == 4){
                        etiqueta_archivo = caract.valor.valor.toString();
                    }
                    break;

                /*case 3: //titulox
                    System.out.println("titulox -> " + caract.valor.valor);
                    if(caract.valor.tipo == 4){
                        etiqueta_eje_X = caract.valor.valor.toString();
                    }
                    break;
                case 4: //tituloy
                    System.out.println("tituloy -> " + caract.valor.valor);
                    if(caract.valor.valor instanceof String){
                        etiqueta_eje_Y = caract.valor.valor.toString();
                    }
                    break;*/
            }
            
        }
    }
    
    public void generar_graficaLineas(){
        
        /*
            SE PROCEDE A GRAFICAR 
        */
            /*DefaultCategoryDataset dataset = new DefaultCategoryDataset(); //se utilizo la libreria jfreechart-1.5.3
            JFreeChart chart= ChartFactory.createBarChart(
                    titulo_grafica,      
                    etiqueta_archivo,
                    ety,
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,  
                    false,  
                    false
                   
           );*/
            XYSeriesCollection dataset= new XYSeriesCollection();
            JFreeChart chart = ChartFactory.createXYLineChart(
                    titulo_grafica,//titulo de la grafica
                    etiqueta_archivo, //nombre del archivo
                    ,//putnuaciones
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,  
                    true,  
                    false
            );
            
            
            
            
           //recorro la lista de "ejex" y "valores"
           for(int i=0; i < this.ejex.size(); i++){
               if(this.valores.get(i) instanceof Integer){
                    dataset.setValue((int)this.valores.get(i), this.ejex.get(i), this.ejex.get(i));
               }else if(this.valores.get(i) instanceof Double){
                   dataset.setValue((double)this.valores.get(i), this.ejex.get(i), this.ejex.get(i));
               }
           }
           
            ChartFrame frame = new ChartFrame("Grafica de Lineas", chart);
            frame.pack();
            frame.setVisible(true);
            
          /*  
            GENERAR IMAGEN
            int width = 640;   
            int height = 480;
        
            File barChart = new File( "BarChart"+".jpeg" ); 
            try {
                ChartUtilities.saveChartAsJPEG( barChart , chart , width , height );    //se utilizo jfreechart-1.0.1
            
            } catch (IOException ex) {
                Logger.getLogger(GBarras.class.getName()).log(Level.SEVERE, null, ex);
            }
           */ 
    }
    
}
