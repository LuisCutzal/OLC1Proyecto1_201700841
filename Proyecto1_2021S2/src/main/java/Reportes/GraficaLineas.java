
package Reportes;
import com.mycompany.proyecto1_2021s2.Archivo;
import com.mycompany.proyecto1_2021s2.ClasesRepetidas;
import com.mycompany.proyecto1_2021s2.DirImagen;
import com.mycompany.proyecto1_2021s2.MetodosRepetidos;
import com.mycompany.proyecto1_2021s2.Variables;
import java.util.LinkedList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import com.mycompany.proyecto1_2021s2.Ventana;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.category.DefaultCategoryDataset;


public class GraficaLineas {
    public LinkedList<Caracteristica> entrada;
    public LinkedList<Object> valores = new LinkedList<>();
    String nombreArchivo;
    String tituloGrafica;
    public GraficaLineas(LinkedList<Caracteristica> listaL){
        this.entrada=listaL;
    }
    public void Valores(){
    for(Caracteristica caracteristicas: this.entrada){
        switch(caracteristicas.tipo){
            case 0:// el titulo
                if(caracteristicas.valor.tipo==4){
                    tituloGrafica=caracteristicas.valor.valor.toString();
                }else if(caracteristicas.valor.tipo == 3){
                        for(Variables var : Ventana.variables_FCA){
                            if(var.getIdentificador().equalsIgnoreCase(caracteristicas.valor.valor.toString())){
                                Valor val = (Valor)var.valor;
                                tituloGrafica = val.valor.toString();
                            }
                        }
                    }
            break;
            case 2: //valores
                for(Valor val : caracteristicas.lista_valores){
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
            case 5: // nombre del archivo
                if(caracteristicas.valor.tipo==4){
                    this.nombreArchivo=caracteristicas.valor.valor.toString();
                }
            break;
            }
        }
    }
    public void generar_graficaLineas() throws IOException{
        int contadorMetodos=0,contadorMetodos2=0;
        DefaultCategoryDataset dataset= new DefaultCategoryDataset();
        JFreeChart LineasG = ChartFactory.createLineChart(
                tituloGrafica,
                nombreArchivo,
                "Puntajes",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        Ventana.jTextArea2.append("Generando Grafica de Lineas: "+tituloGrafica+"\n");
        for(Archivo ObtenerDatos: Ventana.datos_archivos){
            if(ObtenerDatos.getUbi()=="A" && ObtenerDatos.getNombreArchivo().equals(this.nombreArchivo)){
                //valor, filas, columnas
                dataset.addValue(ObtenerDatos.getListaVariables().size(),"Proyecto 1","Variables");
                for (int i = 0; i < ObtenerDatos.getListaClases().size(); i++){//tenemos que recorrer la lista de clases para obtener la cantidad de metodos
                    contadorMetodos=contadorMetodos+ObtenerDatos.getListaClases().get(i).Metodos.size();
                }
                dataset.addValue(contadorMetodos, "Proyecto 1", "Metodos");
                dataset.addValue(ObtenerDatos.getListaClases().size(),"Proyecto 1","Clases");
                dataset.addValue(ObtenerDatos.getListaComentarios().size(), "Proyecto 1", "Comentarios");
           }
           if(ObtenerDatos.getUbi()=="B" && ObtenerDatos.getNombreArchivo().equals(this.nombreArchivo)){
                dataset.addValue(ObtenerDatos.getListaVariables().size(),"Proyecto 2","Variables");
                for (int i = 0; i<ObtenerDatos.getListaClases().size() ; i++){//tenemos que recorrer la lista de clases para obtener la cantidad de metodos
                   contadorMetodos2=contadorMetodos2+ObtenerDatos.getListaClases().get(i).Metodos.size();
               }
                dataset.addValue(contadorMetodos2,"Proyecto 2","Metodos");
                dataset.addValue(ObtenerDatos.getListaClases().size(),"Proyecto 2","Clases");
                dataset.addValue(ObtenerDatos.getListaComentarios().size(), "Proyecto 2", "Comentarios");
           }
        }
        ChartFrame frame = new ChartFrame("Grafica de Lineas", LineasG);
        frame.pack();
        frame.setVisible(true);
        int ancho = 1000;
        int alto = 750;
        File f = new File ("Grafica Lineas "+this.nombreArchivo+".png");
        ChartUtilities.saveChartAsPNG(f,LineasG,ancho,alto);
        DirImagen ubi = new DirImagen("Grafica Lineas "+this.nombreArchivo);
        Ventana.listaImagen.add(ubi);
    }
}
