/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto1_2021s2;

import Analizadores.Analizador_Lexico;
import Analizadores.Sintactico;
import AnalizadoresJS.ALexico;
import AnalizadoresJS.SintacticoJS;
import Reportes.GraficaBarras;
import java.awt.Panel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 *
 * @author Domingo
 */
public class Ventana extends javax.swing.JFrame {
    public static ArrayList<error> listaErrores = new ArrayList<error>();
    LinkedList<Archivo> datos_archivos = new LinkedList<>();
    static LinkedList<Object> instrucciones = new LinkedList<Object>();
    public static LinkedList<String> listacomentarios = new LinkedList<>();
    public int cont_variables_repetidas = 0;
    public int cont_comment_repetido = 0;
    public static LinkedList<Variables> variables_FCA = new LinkedList<>();
    public static LinkedList<Puntajes> lista_puntajesEspecificos = new LinkedList<>();
    JFileChooser seleccionar = new JFileChooser();

    public Ventana() {
        initComponents();
    }
       
        
        File archivo;
        FileInputStream entrada;
        FileOutputStream salida;
    
    public String Abr (File archivo){
        String documento ="";
        try {
            entrada= new FileInputStream(archivo);
            int contador;
            while((contador=entrada.read())!=-1){
                char caracter=(char)contador;
                documento+=caracter;
            }
        } catch (Exception e) {
            }
        return  documento;
        }
        public String Guardar(File archivo,String documento){
        String mensaje=null;
        try {
            salida=new FileOutputStream(archivo);
            byte[] bit=documento.getBytes();
            salida.write(bit);
            mensaje="Archivo Guardado";
        } catch (Exception e) {
        }
        return mensaje;
        }
    
    

        
        
        
        
    /**
     * Metodo para generar el reporte de errores
     */
    ArrayList<String> variables = new ArrayList<String>();//funciona
    ArrayList<String> clases = new ArrayList<String>();//funciona
    ArrayList<String> importaciones = new ArrayList<String>();//funciona
    ArrayList<String> metodos = new ArrayList<String>();//funciona
    ArrayList<String> asignaciones = new ArrayList<String>();//funciona
    ArrayList<String> sentenciasIf = new ArrayList<String>();//funciona pero aun tengo que ver validaciones
    //ArrayList<String> elses = new ArrayList<String>();//aun no se si tengo que agregar una lista para else, else if
    ArrayList<String> sentenciasFor = new ArrayList<String>();//funciona pero aun tengo q ver validaciones
    ArrayList<String> sentenciasWhile = new ArrayList<String>();//funciona pero aun tengo q ver validaciones
    ArrayList<String> sentenciasDo = new ArrayList<String>();//funciona pero aun tengo q ver validaciones
    ArrayList<String> sentenciasSwhitch = new ArrayList<String>();//funciona pero aun tengo q ver validaciones de los casos y el default
    ArrayList<String> consolas = new ArrayList<String>();//funciona pero aun tengo q ver validaciones
    ArrayList<String> breaks = new ArrayList<String>();//funciona pero aun tengo q ver validaciones
    ArrayList<String> llamadas = new ArrayList<String>();//funciona
    ArrayList<String> contadores = new ArrayList<String>();
    ArrayList<String> parametros = new ArrayList<String>();
    ArrayList<String> expresiones = new ArrayList<String>();
    
    public void encontrar(Nodo nodo,ArrayList<String> variables){
        int lineaM1=0,lineaM2=0;
        for(Nodo instruccion : nodo.hijos){
            //System.out.println("Nodos "+ instruccion.lexema);
            if (instruccion.token=="CLASE") {
                for (Nodo clase : instruccion.hijos) {
                    if (clase.token=="id") {//si encuentra la clase con el nombre que se le da, ejemplo class hola(){}  aparece el nombre de la clase
                        clases.add(clase.lexema);
                    }
                    if(clase.token=="llavecierra"){
                        System.out.println("llavecierra: " + clase.linea );//para saber cuantas lineas tiene el archivo js
                    }
                }
            }
            if(instruccion.token=="IMPORT"){
                for(Nodo importacion : instruccion.hijos){
                    if(importacion.token == "id"){
                        importaciones.add(importacion.lexema);
                    }
                }
            }
            if(instruccion.token == "DECLARACIONVARIABLES"){
                for(Nodo declaracion : instruccion.hijos){
                    if(declaracion.token == "id"){
                        variables.add(declaracion.lexema);
                    }
                }
            }
            
            if(instruccion.token == "METODO"){
                for(Nodo metodo : instruccion.hijos){
                    if(metodo.token == "id"){
                        metodos.add(metodo.lexema);
                    }
                    if(metodo.token=="llaveabre"){
                        //System.out.println("linea inicio metodo: "+metodo.linea);
                        lineaM1=metodo.linea;
                    }
                    if(metodo.token=="llavecierra"){
                        //System.out.println("linea fin metodo:" +metodo.linea);
                        lineaM2=metodo.linea;
                    }
                }
                    int fin=lineaM2-lineaM1;
                    System.out.println("total de Lineas Metodo: " + (fin+1));
            }
            
            if(instruccion.token == "ASIGNACIONVARIABLES"){
                for(Nodo asignacion : instruccion.hijos){
                    if(asignacion.token == "id"){
                        asignaciones.add(asignacion.lexema);
                    }
                }
            }
            
            if(instruccion.token == "SENTENCIAIF"){//en esta parte tengo dudas respecto a el if, else if y else ver despues, por el momento funciona
                for(Nodo sentenciaif : instruccion.hijos){
                    if(sentenciaif.token == "if_js"){
                        sentenciasIf.add(sentenciaif.lexema);
                    }
                }
            }
            
            if(instruccion.token == "SENTENCIAFOR"){
                for(Nodo sentenciafor : instruccion.hijos){
                    if(sentenciafor.token == "for_js"){
                        sentenciasFor.add(sentenciafor.lexema);
                    }
                }
            }
            
            if(instruccion.token == "SENTENCIAWHILE"){
                for(Nodo sentenciawhile : instruccion.hijos){
                    if(sentenciawhile.token == "while_js"){
                        sentenciasWhile.add(sentenciawhile.lexema);
                    }
                }
            }
            
            if(instruccion.token == "SENTENCIADOWHILE"){
                for(Nodo sentenciaDowhile : instruccion.hijos){
                    if(sentenciaDowhile.token == "do_js"){
                        sentenciasDo.add(sentenciaDowhile.lexema);
                    }
                }
            }
            
            if(instruccion.token == "SENTENCIASWITCH"){
                for(Nodo sentenciaSwitch : instruccion.hijos){
                    if(sentenciaSwitch.token == "switch_js"){
                        sentenciasSwhitch.add(sentenciaSwitch.lexema);
                    }
                }
            }
            if(instruccion.token == "CONSOLA"){
                for(Nodo consola : instruccion.hijos){
                    if(consola.token == "console_js"){
                        consolas.add(consola.lexema);
                    }
                }
            }
            
            if(instruccion.token == "PARAMETROS"){//queda pendiente
                Nodo prueba=null;
                for(Nodo breacks : instruccion.hijos){
                    prueba=breacks;
                }
                if(prueba.lexema==""){
                    encontrar(prueba,variables);
                }
            }
            if(instruccion.token == "LLAMADA"){
                for(Nodo llamada : instruccion.hijos){
                    if(llamada.token == "id"){
                        llamadas.add(llamada.lexema);
                    }
                }
            }
            if(instruccion.token == "CONTADOR"){
                for(Nodo contador : instruccion.hijos){
                    if(contador.token == "id"){
                        contadores.add(contador.lexema);
                    }
                }
            }
            if(instruccion.token=="EXPRESION"){
                for(Nodo exp : instruccion.hijos){
                    if (exp.token=="id") {
                        expresiones.add(exp.lexema);
                    }
                    else if(exp.token=="true_js"){
                        expresiones.add(exp.lexema);
                    }
                    else if(exp.token=="false_js"){
                        expresiones.add(exp.lexema);
                    }
                    else if(exp.token=="numero"){
                        expresiones.add(exp.lexema);
                    }
                    else if(exp.token=="decimal"){
                        expresiones.add(exp.lexema);
                    }
                    else if(exp.token=="cadena"){
                        expresiones.add(exp.lexema);
                    }
                }
            }
            if(instruccion.lexema == "" ){
                encontrar(instruccion,variables);
            }
        }
    }
    
    public void imprimir(){
        for (String cla:clases){
            //jTextArea2.append("Clase encontrada --> " + cla + "\n");
            System.out.println("Clase encontrada --> " + cla + "\n");
        }
        for(String importacion:importaciones){
            //jTextArea2.append("Import encontrada --> " + importacion + "\n");
            System.out.println("Import encontrada --> " + importacion + "\n");
        }
        for(String metodo:metodos){
            //jTextArea2.append("Metodo encontrado --> " + metodo + "\n");
            System.out.println("Metodo encontrado --> " + metodo + "\n");

        }
        for( String id: variables ){
            //jTextArea2.append("Declaracion encontrada --> " + id + "\n");
            System.out.println("Declaracion encontrada --> " + id + "\n");
        }
        for( String asignacion: asignaciones ){
            //jTextArea2.append("Asignacion encontrada --> " + asignacion + "\n");
            System.out.println("Asignacion encontrada --> " + asignacion + "\n");
        }
        for( String sentenciaif: sentenciasIf ){ //tengo mis dudas respecto al if, else if y else, ver despues
            //jTextArea2.append("Sentencia IF encontrada --> " + sentenciaif + "\n");
            System.out.println("Sentencia IF encontrada --> " + sentenciaif + "\n");
        }
        /*
        for( String el: elses ){
            jTextArea2.append("Sentencia ELSE encontrada --> " + el + "\n");
        }
        */
        
        for( String senFor: sentenciasFor ){
            //jTextArea2.append("Sentencia For encontrada --> " + senFor + "\n");
            System.out.println("Sentencia For encontrada --> " + senFor + "\n");
        }
        
        for( String senwhile: sentenciasWhile ){
            //jTextArea2.append("Sentencia WHILE encontrada --> " + senwhile + "\n");
            System.out.println("Sentencia WHILE encontrada --> " + senwhile + "\n");
        }
        for( String senDowhile: sentenciasDo ){
            //jTextArea2.append("Sentencia DO-WHILE encontrada --> " + senDowhile + "\n");
            System.out.println("Sentencia DO-WHILE encontrada --> " + senDowhile + "\n");
        }
        for( String senSwhitch: sentenciasSwhitch ){
            //jTextArea2.append("Sentencia SWITHC encontrada --> " + senSwhitch + "\n");
            System.out.println("Sentencia SWITHC encontrada --> " + senSwhitch + "\n");
        }
        
        for( String consola: consolas ){
           //jTextArea2.append("Consola encontrada --> " + consola + "\n");
            System.out.println("Consola encontrada --> " + consola + "\n");
        }
        
        for( String params: parametros ){
            //jTextArea2.append("Parametro encontrado --> " + params + "\n");
            System.out.println("Parametro encontrado --> " + params + "\n");
        }
        
        for(String exp:expresiones){
            //jTextArea2.append("Valor --> " + exp + "\n");
            System.out.println("Valor --> " + exp + "\n");
        }
        
        for( String llamada: llamadas ){
            //jTextArea2.append("La Llamada es --> " + llamada + "\n");
            System.out.println("La Llamada es --> " + llamada + "\n");
        }
        
        for( String contador: contadores ){
            //jTextArea2.append("El contador es --> " + contador + "\n");
            System.out.println("El contador es --> " + contador + "\n");
        }
        
    }
    /**
     * Metodo para verificar los archivos dentro de una carpeta 
     * @param ruta_proy1 indica la ruta donde se encuentra la carpeta del proyecto 1
     * @param ruta_proy2 indica la ruta donde se encuentra la carpeta del proyecto 2
     */
    
    
    public void archivos_carpetas(String ruta_proy1, String ruta_proy2){
        try{
            DirectoryStream<Path> stream_p1 = Files.newDirectoryStream(Paths.get(ruta_proy1), "*.js");
            
           //Recorremos los archivos del proyecto 1
            for (Path file_p1: stream_p1) {
                DirectoryStream<Path> stream_p2 = Files.newDirectoryStream(Paths.get(ruta_proy2), "*.js");
                
                String nombre_archivo1 = file_p1.getFileName().toString(); 
                File archivo = new File (file_p1.toString());
                FileReader fr = new FileReader (archivo);
                Archivo nuevo_archivo1 = null;
                Archivo nuevo_archivo2 = null;
                //Vamos a comparar el archivo del proyecto1 con los archivos de la carpeta2 para saber si se llaman igual 
                for(Path file_p2 : stream_p2){
                    String nombre_archivo2 = file_p2.getFileName().toString();
                    File archivo2 = new File (file_p2.toString());
                    FileReader fr2 = new FileReader (archivo2);
                    //Si se llaman igual se comienza el proceso para analizar copias
                    if(nombre_archivo1.equals(nombre_archivo2)){
                        System.out.println("Los nombres son iguales, vamos a comparar --> " + file_p1.getFileName());
                        //--> 1ero vamos a analizar el archivo1 del proyecto 1
                        try{
                            
                            System.out.println("----------- " + nombre_archivo1 + " ----------- ");
                            Nodo raiz = null;
                            //Mandamos a analizar el archivo del proyecto 1 
                            SintacticoJS parse=new SintacticoJS(new ALexico(new BufferedReader(fr)));
                            parse.parse();

                            raiz = parse.getRaiz();
                            if(raiz == null){
                                System.out.println("No se genero bien el arbol");
                            }else{
                                
                                nuevo_archivo1 = new Archivo(nombre_archivo1, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                                //--> vamos a guardar las variables encontradas en el archivo
                                encontrar(raiz,nuevo_archivo1.variables ); 
                                //-->agregamos los comentarios encontrados (la lista se lleno en el archivo A_Lexico_FCA.jflex)
                                for(String comment : listacomentarios){
                                    nuevo_archivo1.comentarios.add(comment);
                                }
                                //-->agregamos los errores encontrados (la lista se lleno en los archivos A_Lexico_FCA.jflex y A_sintacticos_FCA.cup)
                                for(error errores : listaErrores){
                                    //--> guardamos los errore indicando el nombre del archivo
                                    error nuevo_error = new error(errores.tipo, errores.valor, nuevo_archivo1.nombre_archivo, errores.fila, errores.columna);
                                    nuevo_archivo1.lista_errores.add(nuevo_error);
                                }
                                //Arbol arbol = new Arbol(raiz);
                                //arbol.GraficarSintactico();
                                
                                //-->guardamos el archivo en una lista
                                this.datos_archivos.add(nuevo_archivo1);
                                //-->limpiamos variables
                                listaErrores.clear();
                                listacomentarios.clear();
                                
                            }
                        }catch(Exception ex){
                            System.out.println("Error en analizar el archivo del proyecto.");
                            System.out.println("Causa: "+ex.getCause());
                        }
                        //--> 2do vamos a analizar el archivo2
                        try{
                            
                            System.out.println("----------- " + nombre_archivo2 + " en PROYECTO 2----------- ");
                            Nodo raiz = null;
                            //Mandamos a analizar el archivo del proyecto 2
                            SintacticoJS parse=new SintacticoJS(new ALexico(new BufferedReader(fr2)));
                            parse.parse();

                            raiz = parse.getRaiz();
                            if(raiz == null){
                                System.out.println("No se genero bien el arbol");
                            }else{
                                
                                nuevo_archivo2 = new Archivo(nombre_archivo2, new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
                                //--> vamos a guardar las variables encontradas en el archivo
                                encontrar(raiz, nuevo_archivo2.variables); 
                                //-->agregamos los comentarios encontrados (la lista se lleno en el archivo A_Lexico_FCA.jflex)
                                for(String comment : listacomentarios){
                                    nuevo_archivo2.comentarios.add(comment);
                                }
                                //-->agregamos los errores encontrados (la lista se lleno en los archivos A_Lexico_FCA.jflex y A_sintacticos_FCA.cup)
                                for(error errores : listaErrores){
                                    //--> guardamos los errore indicando el nombre del archivo
                                    error nuevo_error = new error(errores.tipo, errores.valor, nuevo_archivo2.nombre_archivo, errores.fila, errores.columna);
                                    nuevo_archivo2.lista_errores.add(nuevo_error);
                                }
                                //Arbol arbol = new Arbol(raiz);
                                //arbol.GraficarSintactico();
                                //-->guardamos el archivo en una lista
                                this.datos_archivos.add(nuevo_archivo2);
                                //-->limpiamos variables
                                listaErrores.clear();
                                listacomentarios.clear();
                                
                            }
                        }catch(Exception ex){
                            System.out.println("Error en analizar el archivo del proyecto.");
                            System.out.println("Causa: "+ex.getCause());
                        }
                        
                        //--> detectamos copias entre estos dos archivos 
                        if(nuevo_archivo1 != null && nuevo_archivo2 != null){
                            variables_repetidas(nuevo_archivo1, nuevo_archivo2);
                            comentariosrepetidos(nuevo_archivo1, nuevo_archivo2);
                        }
                    }
                }
            }
        } catch (IOException | DirectoryIteratorException ex) {
		    System.err.println(ex);
		}
    }    
    /**
     * Metodo para verificar las variables repetidas en ambos archivos
     * @param archivo1 recibe el archivo del proyecto 1 con toda su informacion
     * @param archivo2 recibe el archivo del proyecto 2 con toda su informacion
     */
    public void variables_repetidas(Archivo archivo1, Archivo archivo2){
        //dar el punteo. 
        for(String id_variable_arch1 : archivo1.variables){
            for(String id_variable_arch2 : archivo2.variables){
                if(id_variable_arch1.equals(id_variable_arch2)){
                    jTextArea2.append("Variable repetida \"" + id_variable_arch1 +"\" en archivos " + archivo1.nombre_archivo + "\n");
                    this.cont_variables_repetidas++;
                }
            }
        }
    }
    
    /**
     * Metodo para verificar los comentarios repetidos en ambos archivos
     * @param archivo1 recibe el archivo del proyecto 1 con toda su informacion
     * @param archivo2 recibe el archivo del proyecto 2 con toda su informacion
     */
    public void comentariosrepetidos(Archivo archivo1, Archivo archivo2){
        for(String comment : archivo1.comentarios){
            for(String comment2 : archivo2.comentarios){
                if(comment.equalsIgnoreCase(comment2)){
                    //jTextArea2.append("Comentario repetido: \"" + comment +"\" en archivos " + archivo2.nombre_archivo + "\n");
                    jTextArea2.append("Comentario repetido: " + comment +" en archivos " + archivo2.nombre_archivo+" y " +archivo1.nombre_archivo+ "\n");
                    this.cont_comment_repetido++;
                }
            }
        }
    }    
    public void ReporteErrores(){
        LinkedList<error> Reporte_errores = new LinkedList<>();
        for(Archivo archivo : datos_archivos){
            Reporte_errores.addAll(archivo.lista_errores);
        }
        FileWriter fichero = null;
        PrintWriter pw = null;
                try {
                    String path = "ReporteErrores.html";
                    fichero = new FileWriter(path);
                    pw = new PrintWriter(fichero); 
                String Html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//ES\">\n\t"
               + "<HTML>\n\t"
               + "<HEAD>\n\t"
               + "<TITLE>REPORTE DE ERRORES</TITLE>\n\t"
               + "<style>\n\t"
               + "body {\n\t"
               + "background:#AAAA;\n\t"
               +/* el fondo de todo el cuerpo*/ "padding: 20px;\n\t"
               + /*el espacio entre el borde y su contenido*/ "}\n\t"
               + "h2 {\n\t"
               + "color: #5D6D7E;\n\t"
               + "font-family: Calibri;\n\t"
               + /*tipo de fuente*/ "}\n\t"
               + ".articulo {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Calibri;\n\t"
               + "background: #7FB3D5;\n\t"
               + "border: 6px solid #2471A3;\n\t" //borde cuadro arriba
               + "color: #AAAAAFF;\n\t"
               + "padding: 13px;\n\t"
               + "}\n\t"
               + ".tabla {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Century Gothic;\n\t"
               + "background: #AAAAA;\n\t"
               + "border: 6px solid #5D6D7E;\n\t" //borde cuadro abajo
               + "color: #000000;\n\t"
               + "padding: 13px;\n\t"
               + "}\n\t"
               + ".fin {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Eras Light ITC;\n\t"
               + "background: #7FB3D5;\n\t"
               + "border: 6px solid #F74316;\n\t"
               + "color: #000000;\n\t" +//2939B5
               "padding: 13px;\n\t"
               + "}\n\t"
               + "</style>\n\t"
               + "</HEAD>\n\t"
               + "<BODY>\n\t"
               + "<div class=\"articulo\"><H3>Universidad de San Carlos de Guatemala<BR>Facultad de Ingenieria<BR>Escuela de Ciencias y Sistemas<BR>Nombre: Luis Cutzal<BR> Carné: 201700841</H3><CENTER><H2>Organizacion de Lenguajes y Compiladores 1<BR>PROYECTO 1<BR>REPORTE DE ERRORES</H2></CENTER></div>\n"
               + "<div class=\"tabla\"><UL>\n" +//No. Errores: 
               "<table style=\"margin:0 auto; \"border=3>\n\t"
               + "<tr align=\"center\" bottom=\"middle\">\n\t"
               + "<td>\n\t"
               + "<table style =\"border: 1px solid black;\">\n\t"
               + "<tr align=\"center\" bottom=\"middle\">\n\t"
               + "<td><b>Tipo</b></td>\n\t"
               + "<td><b>Descripcion</b></td>\n\t"
               + "<td><b>Fila</b></td>\n\t"
               +  "<td><b>Columna</b></td>\n\t"
               +  "<td><b>Archivo</b></td>\n\t"
               + "</tr>\n\t";
               
                for(error error : Reporte_errores){
                    Html += "<tr align=\"center\" bottom=\"middle\">\n\t"
                    + "<td>" + error.tipo + "</td>"
                    + "<td>" + error.valor + "</td>"
                    + "<td>" + error.fila  + "</td>"
                    +  "<td>" + error.columna + "</td>"
                    +  "<td>" + error.archivo + "</td>"
                    + "</tr>\n\t";
                }  
                Html += "</tr></table></tr></table></UL></div>\n\t"
                +"</BODY>\n\t"
                + "</HTML>";
                pw.print(Html);
                    
                } catch (Exception e) {
                }finally{
                    if(null!=fichero){
                        try {
                            fichero.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Reportes\\"+"ReporteErrores.html");
            //System.out.println("Final");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //fin reporte errores
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("EDITOR");

        jLabel2.setText("CONSOLA");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("GernerarReporteEstadistico{\n    compare(\"C:\\Users\\Domingo\\Desktop\\USAC\\Segundo Semestre 2021\\Compi1\\lab\\Poyecto1\\Entrada\\proyecto1\",\"C:\\Users\\Domingo\\Desktop\\USAC\\Segundo Semestre 2021\\Compi1\\lab\\Poyecto1\\Entrada\\proyecto2\");\n    DefinirGlobales{\n        string reporte1 = \"Probabilidades esperadas para Variables archivo archivo2vars.js\";\n        string reporte2 = \"Probabilidades obtenidos para Variables archivo archivo2vars.js\";\n\n        double pe1 = 0;\n        double pe2 = 1;\n        double pe3 = 0;\n        double pe4 = 1;\n        double pe5 = 0;\n        double pe6 = 1;\n        double pe7 = 1;\n\n        double po1 = ${PuntajeEspecifico,\"archivo2vars.js\",\"variable\",\"punteo\"};\n        double po2 = ${PuntajeEspecifico,\"archivo2vars.js\",\"variable\",\"validacion\"};\n        double po3 = ${PuntajeEspecifico,\"archivo2vars.js\",\"variable\",\"aritmetica\"};\n        double po4 = ${PuntajeEspecifico,\"archivo2vars.js\",\"variable\",\"punteo2\"};\n        double po5 = ${PuntajeEspecifico,\"variables.js\",\"variable\",\"A\"};\n        double po6 = ${PuntajeEspecifico,\"variables.js\",\"variable\",\"variable\"};\n        double po7 = ${PuntajeEspecifico,\"variables.js\",\"variable\",\"compi1\"};\n        \n    }\n\n    GraficaBarras{\n        Titulo: reporte1;\n        Ejex: [ \"punteo\", \"validacion\" , \"aritmetica\", \"punteo2\" ];\n        Valores: [ pe1, pe2, pe3, pe4 ];\n        Titulox: \"Nombre de las variables\";\n        Tituloy: \"Puntaje\";\n    }\n\n    GraficaBarras{\n        Titulo: reporte2;\n        Ejex: [ \"punteo\", \"validacion\" , \"aritmetica\", \"punteo2\" ];\n        Valores: [ po1, po2, po3, po4 ];\n        Titulox: \"Nombre de las variables\";\n        Tituloy: \"Puntaje\";\n    }\n}");
        jScrollPane1.setViewportView(jTextArea1);

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Abrir Archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Guardar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Guardar como...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Caracteristicas");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });

        jMenuItem4.setText("Crear Pestaña");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Eliminar Pestaña");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Herramientas");

        jMenuItem6.setText("Ejecutar");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reportes");

        jMenuItem8.setText("Reporte de Errores");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem9.setText("Reporte Estadístico");
        jMenu4.add(jMenuItem9);

        jMenuItem10.setText("Reporte de Tokens");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Reporte JSON");
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(273, 273, 273))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        /*JFileChooser VentText =new JFileChooser();
        VentText.showOpenDialog(null);
        File archivo= VentText.getSelectedFile();
        try{
            FileReader Abrir = new FileReader(archivo);
            BufferedReader leer= new BufferedReader(Abrir);
            String text="";
            String linea="";
            while(((linea=leer.readLine())!=null)){
                text+=linea+"\n";
            }
            jTextArea1.setText(text);
            JOptionPane.showMessageDialog(null,"Archivo cargado Correctamente");
        }
        catch(Exception e){}*/
        if(seleccionar.showDialog(null,"Abrir")==JFileChooser.APPROVE_OPTION){
            archivo=seleccionar.getSelectedFile();
            if(archivo.canRead()){
                if(archivo.getName().endsWith("js")){ //tengo que cambiar la extencion del archivo
                    String documento = Abr(archivo);
                    jTextArea1.setText(documento);
                }else{
                    JOptionPane.showMessageDialog(null,"Error");
                }
            }
        }
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        Nodo raiz = null;
        /*
        try {
            SintacticoJS sint=new SintacticoJS(new ALexico(new BufferedReader(new StringReader(jTextArea1.getText()))));
            
            sint.parse();
            raiz = sint.getRaiz();
            System.out.println("Lectura del archivo JS correcta");
             
        } catch (Exception e) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, e);
        }
        if(raiz != null){
            jTextArea2.setText("");
            variables.clear();
            clases.clear();
            importaciones.clear();
            metodos.clear();
            asignaciones.clear();
            sentenciasIf.clear();
            sentenciasFor.clear();
            sentenciasWhile.clear();
            sentenciasDo.clear();
            sentenciasSwhitch.clear();
            consolas.clear();
            breaks.clear();
            llamadas.clear();
            contadores.clear();
            expresiones.clear();
            encontrar(raiz,variables);
            imprimir();
        }
       
        JOptionPane.showMessageDialog(null, "Analizado con exito", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        */
        
       
        try {
            Sintactico sint=new Sintactico(new Analizador_Lexico(new BufferedReader(new StringReader(jTextArea1.getText()))));
            sint.parse();
            instrucciones=sint.instrucciones;
            for(Object ins : instrucciones){
                if(ins instanceof Comparar){
                    Comparar comp = (Comparar)ins;
                    archivos_carpetas(comp.getRuta1(), comp.getRuta2());
                    //this.ReporteErrores();
                }
            }
            //comienza el analizador del fca, las graficas
            //comienza grafica barras
            for(Object ins : instrucciones){
                 if(ins instanceof GraficaBarras){
                    GraficaBarras grafica_barras = (GraficaBarras)ins;
                    grafica_barras.valores();
                    grafica_barras.generar_graficaBarras();
                }else if(ins instanceof LinkedList){
                    //En este caso como lo trabajo se que sera una lista de variables 
                    this.variables_FCA = (LinkedList<Variables>)ins;
                }
            }
            //termina grafica barras
             
        } catch (Exception e) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, e);
        }
        
        
        
        
         
            
            
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
            
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        

        //****************GUARDAR***********
        String documento = jTextArea1.getText();
        String mensaje=Guardar(archivo, documento);    
        
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        

        //***************GUARDAR COMO*************
        if(seleccionar.showDialog(null, "Guardar")==JFileChooser.APPROVE_OPTION){
            archivo=seleccionar.getSelectedFile();
            if(archivo.getName().endsWith("txt")){
                String documento = jTextArea1.getText();
                String mensaje=Guardar(archivo, documento);
                if(mensaje!=null){
                    JOptionPane.showMessageDialog(null,mensaje);
                }else{
                    JOptionPane.showMessageDialog(null,"Archivo no compatible");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Guardar ");
            }
        }  
        
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        
        //*************REPORTE DE ERRORES************
        this.ReporteErrores();
        
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        
        //***************REPORTE DE TOKENS***********
        
    }//GEN-LAST:event_jMenuItem10ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
