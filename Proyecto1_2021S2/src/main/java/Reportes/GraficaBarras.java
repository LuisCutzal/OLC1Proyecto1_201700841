/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;
import com.mycompany.proyecto1_2021s2.DirImagen;
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import com.mycompany.proyecto1_2021s2.Ventana;
import com.mycompany.proyecto1_2021s2.Variables;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficaBarras {
    
    public LinkedList<Caracteristica> lista_caracteristica;
    String etiqueta_eje_X ;
    String etiqueta_eje_Y ;
    String titulo_grafica ;
    public LinkedList<String> ejex = new LinkedList<>();
    public LinkedList<Object> valores = new LinkedList<>();
    
    public GraficaBarras(LinkedList<Caracteristica> lista_caracteristica){
        this.lista_caracteristica = lista_caracteristica;
    }
    
    public void valores(){
        for(Caracteristica caract : this.lista_caracteristica){
            switch(caract.tipo){
                case 0: //titulo
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
                case 1: //ejex
                    for(Valor val : caract.lista_valores){
                        if(val.tipo == 1 || val.tipo == 2 || val.tipo == 4){ 
                            this.ejex.add(val.valor.toString());
                        }else if(val.tipo == 3){ //id
                            for(Variables variable: Ventana.variables_FCA){
                                if(variable.getIdentificador().equalsIgnoreCase(val.valor.toString())){
                                    this.ejex.add(variable.getValor().toString());
                                }
                            }
                        }
                    }
                    break;
                case 2: //valores
                    for(Valor val : caract.lista_valores){
                        if(val.tipo == 1 || val.tipo == 2 || val.tipo == 4){ 
                            this.valores.add(val.valor);
                        }else if(val.tipo == 3){ //id
                            for(Variables variable: Ventana.variables_FCA){
                                if(variable.getIdentificador().equalsIgnoreCase(val.valor.toString())){
                                    this.valores.add(variable.getValor());
                                }
                            }
                        }
                    }
                    break;
                case 3: //titulox
                    if(caract.valor.tipo == 4){
                        etiqueta_eje_X = caract.valor.valor.toString();
                    }
                    break;
                case 4: //tituloy
                    if(caract.valor.valor instanceof String){
                        etiqueta_eje_Y = caract.valor.valor.toString();
                    }
                    break;
            }
        }
    }
    
    public void generar_graficaBarras() throws IOException{
            DefaultCategoryDataset dataset = new DefaultCategoryDataset(); //se utilizo la libreria jfreechart-1.5.3
            JFreeChart BarrasG= ChartFactory.createBarChart(
                    titulo_grafica,      
                    etiqueta_eje_X,
                    etiqueta_eje_Y,
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,  
                    false,  
                    false
           );
            Ventana.jTextArea2.append("Generando Grafica de Barras: "+titulo_grafica+"\n");
            for(int i=0; i < this.ejex.size(); i++){
               if(this.valores.get(i) instanceof Integer){
                    dataset.setValue((int)this.valores.get(i), this.ejex.get(i), this.ejex.get(i));
               }else if(this.valores.get(i) instanceof Double){
                   dataset.setValue((double)this.valores.get(i), this.ejex.get(i), this.ejex.get(i));
               }
           }
            ChartFrame frame = new ChartFrame("Grafica de Barras", BarrasG);
            frame.pack();
            frame.setVisible(true);
        int ancho = 1000;
        int alto = 750;
        File f = new File ("Grafica de Barras "+titulo_grafica+".png");
        ChartUtilities.saveChartAsPNG(f,BarrasG,ancho,alto);
        DirImagen ubi = new DirImagen("Grafica de Barras "+titulo_grafica);
        Ventana.listaImagen.add(ubi);
    }
}
