/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;
import com.mycompany.proyecto1_2021s2.DirImagen;
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import com.mycompany.proyecto1_2021s2.Ventana;
import com.mycompany.proyecto1_2021s2.Variables;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart; 
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Domingo
 */
public class GraficaPie {
    public LinkedList<Caracteristica> lista_caracteristica;
    String titulo_grafica ;
    public LinkedList<String> ejex = new LinkedList<>();
    public LinkedList<Object> valores = new LinkedList<>();
    
    public GraficaPie(LinkedList<Caracteristica> lista_caracteristica){
        this.lista_caracteristica = lista_caracteristica;
    }
    public void valores(){
        for(Caracteristica caract : this.lista_caracteristica){
            switch(caract.tipo){
                case 0: //titulo
                    if(caract.valor.tipo == 4){
                        this.titulo_grafica = caract.valor.valor.toString();
                    }else if(caract.valor.tipo == 3){
                        for(Variables var : Ventana.variables_FCA){
                            if(var.getIdentificador().equalsIgnoreCase(caract.valor.valor.toString())){
                                Valor val = (Valor)var.valor;
                                this.titulo_grafica = val.valor.toString();
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
                        }else if(val.tipo==5){//puntaje especifico
                            for (int i = 0; i < Ventana.lista_puntajesEspecificos.size(); i++) {
                                if(Ventana.lista_puntajesEspecificos.get(i).getNombreArchivo().equalsIgnoreCase(val.nombrearchivo)){
                                    if(Ventana.lista_puntajesEspecificos.get(i).getCaracteristica().equalsIgnoreCase(val.caracteristica)){
                                        if(Ventana.lista_puntajesEspecificos.get(i).getId().equalsIgnoreCase(val.identificador)){
                                            this.valores.add(val.getValor());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }
    
    public void generar_graficaPie() throws IOException{
        DefaultPieDataset datos= new DefaultPieDataset();
        JFreeChart graficoPie= ChartFactory.createPieChart(
            titulo_grafica,//nombre grafico
            datos,//datos
            true,
            true,
            false
        );
        Ventana.jTextArea2.append("Generando Grafica Pie: "+titulo_grafica+"\n");
        for(int i=0; i<this.ejex.size(); i++){
            if(this.valores.get(i) instanceof Integer){
                datos.setValue(this.ejex.get(i), ((int) this.valores.get(i)));
            }else if(this.valores.get(i) instanceof Double){
                datos.setValue(this.ejex.get(i),((double) this.valores.get(i)));
            }
        }
        ChartFrame frame = new ChartFrame("Grafica de Pie", graficoPie);
        frame.pack();
        frame.setVisible(true);
        int ancho = 1000;
        int alto = 750;
        File f = new File ("Grafica Pie "+this.titulo_grafica+".png");
        ChartUtilities.saveChartAsPNG(f,graficoPie,ancho,alto);
        DirImagen ubi = new DirImagen("Grafica Pie "+this.titulo_grafica);
        Ventana.listaImagen.add(ubi);
    }    
}
