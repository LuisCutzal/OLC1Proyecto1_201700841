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
import Reportes.GraficaLineas;
import Reportes.GraficaPie;
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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/**
 *
 * @author Domingo
 */
public class Ventana extends javax.swing.JFrame {
    public static LinkedList<error> listaErrores = new LinkedList<error>();
    public static LinkedList<Token> listaTokens = new LinkedList<Token>();
    public static LinkedList<Archivo> datos_archivos = new LinkedList<>();
    static LinkedList<Object> instrucciones = new LinkedList<Object>();
    public static LinkedList<String> listacomentarios = new LinkedList<>();
    public int cont_variables_repetidas = 0;
    public int cont_comment_repetido = 0;
    public int cont_clases_repetidas=0;
    public int cont_metodos_repetidos=0;
    public static LinkedList<Variables> variables_FCA = new LinkedList<>();
    public static LinkedList<Puntajes> lista_puntajesEspecificos = new LinkedList<>();
    JFileChooser seleccionar = new JFileChooser();

    public static LinkedList<DirImagen> listaImagen = new LinkedList<>();
    public static LinkedList<Resumen> listaresumenV1 = new LinkedList<Resumen>();
    public static LinkedList<Resumen> listaresumenC = new LinkedList<>();
    public static LinkedList<Resumen> listaresumenCla = new LinkedList<>();
    public static LinkedList<Resumen> listaresumenMet = new LinkedList<>();
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
           if(mensaje == null){
               JOptionPane.showMessageDialog(null,"Error, No se ha abierto ningun archivo y por esa razon no se guardara nada en algun lugar");
               //System.out.println("Error, No se ha abierto ningun archivo y por esa razon no se guardara nada en algun lugar");
           }
        }
        return mensaje;
    }
    
    LinkedList<String> expresiones = new LinkedList<String>();
    LinkedList<MetodosRepetidos> metodos = new LinkedList<>();
    int tamañoClase=0;
    int tamañoMetodo=0;
    int tamañoParametros=0;
    //para saber cuantos metodos hay en los archivos
    int totalmetodos=0;
    String nombreMetodo="",nombreClase="",nombreMetodoClase="";    
    public void encontrar(Nodo nodo,LinkedList<String> variables,LinkedList<ClasesRepetidas> clases){
        int lineaM1=0,lineaM2=0;
        for(Nodo instruccion : nodo.hijos){
            //System.out.println("Nodos "+ instruccion.lexema);
            if (instruccion.token=="CLASE") {
                for (Nodo clase : instruccion.hijos) {
                    if (clase.token=="id") {//si encuentra la clase con el nombre que se le da, ejemplo class hola(){}  aparece el nombre de la clase
                        //clases.add(clase.lexema);
                        nombreClase=clase.lexema;
                    }
                    if(clase.token=="llavecierra"){
                        tamañoClase=clase.linea;
                       //System.out.println("llavecierra: " + tamañoClase );//para saber cuantas lineas tiene el archivo js
                        clases.add(new ClasesRepetidas(tamañoClase,nombreClase));//no es clasesRepetidas es solo clases
                    }
                }
            }
            
            //ReporteEstadistico repoe = new ReporteEstadistico("Total Variables", id_variable_arch1, cont_variables_repetidas);
            if(instruccion.token == "METODO"){
                for(Nodo metodo : instruccion.hijos){
                    if(metodo.token == "id"){
                        //metodos.add(metodo.lexema);
                        nombreMetodo=metodo.lexema;
                        totalmetodos++;
                    }
                    if(metodo.token=="llaveabre"){
                        //System.out.println("linea inicio metodo: "+metodo.linea);
                        lineaM1=metodo.linea;
                    }
                    if(metodo.token=="llavecierra"){
                        //System.out.println("linea fin metodo:" +metodo.linea);
                        lineaM2=metodo.linea;
                    }
                    if(metodo.token=="PARAMETROS"){
                        tamañoParametros=instruccion.hijos.get(2).hijos.size();
                    }
                }
                    tamañoMetodo=lineaM2-lineaM1;
                    //System.out.println("total de Lineas Metodo: " + (tamañoMetodo+1));
                    clases.getLast().Metodos.add(new MetodosRepetidos(tamañoMetodo,tamañoParametros,nombreMetodo));
                    //metodos.add(new MetodosRepetidos(tamañoMetodo,tamañoParametros,nombreMetodo));//no es metodosRepetidos es solo metodos
            }

            if(instruccion.token == "DECLARACIONVARIABLES"){
                for(Nodo declaracion : instruccion.hijos){
                    if(declaracion.token == "id"){
                        variables.add(declaracion.lexema);
                    }
                }
            }
            if(instruccion.token == "SENTENCIAFOR"){
                for(Nodo sentenciafor : instruccion.hijos){
                    if(sentenciafor.token=="DECLARACIONFOR"){
                        if(sentenciafor.hijos.get(0).token.equals("TIPO") && sentenciafor.hijos.get(1).token.equals("id") ){
                            variables.add(sentenciafor.hijos.get(1).lexema);
                        }
                    }
                }
            }
            if(instruccion.token == "PARAMETROS"){//queda pendiente
                Nodo prueba=null;
                for(Nodo breacks : instruccion.hijos){
                    prueba=breacks;
                }
                if(prueba.lexema==""){
                    encontrar(prueba,variables,clases);
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
                encontrar(instruccion,variables,clases);
            }
        }
        
    }
    public void Comparamos(String ruta_proy1, String ruta_proy2){
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
                        System.out.println("El nombre de los archivos son iguales, se procede a comparar --> " + file_p1.getFileName());
                        //--> 1ero vamos a analizar el archivo1 del proyecto 1
                        try{
                            System.out.println("----------- " + nombre_archivo1 + " en PROYECTO 1 ----------- ");
                            Nodo raiz = null;
                            //Mandamos a analizar el archivo del proyecto 1 
                            SintacticoJS parse=new SintacticoJS(new ALexico(new BufferedReader(fr)));
                            parse.parse();

                            raiz = parse.getRaiz();
                            /*if(raiz == null){
                                System.out.println("No se genero bien el arbol");
                            }*/
                            if (raiz !=null){
                                
                                nuevo_archivo1 = new Archivo(nombre_archivo1, new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
                                encontrar(raiz,nuevo_archivo1.variables,nuevo_archivo1.clases);
                                nuevo_archivo1.setUbi("A");
                                for(String comment : listacomentarios){
                                    nuevo_archivo1.comentarios.add(comment);
                                }
                                for(error errors : listaErrores){
                                    error nuevo_error = new error(errors.tipo, errors.valor, nuevo_archivo1.nombre_archivo, errors.fila, errors.columna);
                                    nuevo_archivo1.lista_errores.add(nuevo_error);
                                }
                                for(Token tk:listaTokens){
                                    Token nuevotk = new Token(tk.tipo,tk.valor,ruta_proy1+"/"+nuevo_archivo1.nombre_archivo,tk.fila,tk.columna);
                                    nuevo_archivo1.lista_tokens.add(nuevotk);
                                }
                                
                                
                                this.datos_archivos.add(nuevo_archivo1);
                                //-->limpiamos variables
                                listaErrores.clear();
                                listacomentarios.clear();
                                listaTokens.clear();
                            }
                        }catch(Exception ex){
                            System.out.println("Error en analizar el archivo del proyecto.");
                            System.out.println("Causa: "+ex.getCause());
                        }
                        try{
                            
                            System.out.println("----------- " + nombre_archivo2 + " en PROYECTO 2----------- ");
                            Nodo raiz = null;
                            SintacticoJS parse = new SintacticoJS(new ALexico(new BufferedReader(fr2)));
                            parse.parse();

                            raiz = parse.getRaiz();
                            /*if(raiz == null){
                                System.out.println("No se genero bien el arbol");
                            }*/
                            if(raiz!=null){
                                nuevo_archivo2 = new Archivo(nombre_archivo2, new LinkedList<>(), new LinkedList<>(),new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
                                encontrar(raiz, nuevo_archivo2.variables,nuevo_archivo2.clases);
                                nuevo_archivo2.setUbi("B");
                                for(String comment : listacomentarios){
                                    nuevo_archivo2.comentarios.add(comment);
                                }
                                for(error errors : listaErrores){
                                    error nuevo_error = new error(errors.tipo, errors.valor, nuevo_archivo2.nombre_archivo, errors.fila, errors.columna);
                                    nuevo_archivo2.lista_errores.add(nuevo_error);
                                }
                                for(Token tk : listaTokens){
                                    Token nuevotk = new Token(tk.tipo,tk.valor,ruta_proy2+"/"+nuevo_archivo2.nombre_archivo,tk.fila,tk.columna);
                                    nuevo_archivo2.lista_tokens.add(nuevotk);
                                }
                                                               
                                this.datos_archivos.add(nuevo_archivo2);
                                listaErrores.clear();
                                listacomentarios.clear();
                                listaTokens.clear();
                            }
                        }catch(Exception ex){
                            System.out.println("Error en analizar el archivo del proyecto.");
                            System.out.println("Causa: "+ex.getCause());
                        }
                        if(nuevo_archivo1 != null && nuevo_archivo2 != null){
                            variables_repetidas(nuevo_archivo1, nuevo_archivo2);
                            comentariosrepetidos(nuevo_archivo1, nuevo_archivo2);
                            ClasesRepetidas(nuevo_archivo1, nuevo_archivo2);
                            MetodosRepetidos(nuevo_archivo1, nuevo_archivo2);
                            
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
        //dar el punteo
        int vueltaA=0,vueltaB=0;
        for(String id_variable_arch1 : archivo1.variables){
            for(String id_variable_arch2 : archivo2.variables){
                if(id_variable_arch1.equals(id_variable_arch2)){
                    jTextArea2.append("Variable repetida \"" + id_variable_arch1 +"\" en archivos " + archivo1.nombre_archivo + "\n");
                    this.cont_variables_repetidas++;
                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(), "variable",id_variable_arch1,  1));
                    //this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(), "variable",id_variable_arch2,  1));
                }
            }
        }
        
        for (int i = 0; i < archivo1.variables.size(); i++) {
            vueltaA++;
        }
        for (int i = 0; i < archivo2.variables.size(); i++) {
            vueltaB++;
        }
        int total=vueltaA+vueltaB;
        TV(total);
    }
    public void TV(int A){
        Resumen rv = new Resumen(A);
        listaresumenV1.add(rv);
    }
    
    public void comentariosrepetidos(Archivo archivo1, Archivo archivo2){
        int comA=0, comB=0;
        for(String comment : archivo1.comentarios){
            for(String comment2 : archivo2.comentarios){
                if(comment.equalsIgnoreCase(comment2)){//para comentarios repetidos
                    jTextArea2.append("Comentario repetido: " + comment +" en archivos " + archivo2.nombre_archivo+" y " +archivo1.nombre_archivo+ "\n");
                    this.cont_comment_repetido++;
                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(), "comentario",comment,  1));
                    this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(), "comentario",comment,  1));
                }
            }
        }
        for (int i = 0; i < archivo1.comentarios.size(); i++) {
            comA++;
        }
        for (int i = 0; i < archivo2.comentarios.size(); i++) {
            comB++;
        }
        int total=comA+comB;
        TC(total);
    }
    public void TC(int B){
        Resumen rc = new Resumen(B);
        listaresumenC.add(rc);
    }
    
    public void ClasesRepetidas(Archivo archivo1,Archivo archivo2){
        int reCla1=0,reCla2=0;
        for(ClasesRepetidas clases : archivo1.clases){
            for(ClasesRepetidas clases2: archivo2.clases){
                //ahora vamos con los metodos
                for(MetodosRepetidos metodos : clases.Metodos){
                    for(MetodosRepetidos metodos2:clases2.Metodos){
                        if(clases.id.equals(clases2.id)){
                            if(metodos.id.equals(metodos2.id) && metodos.CantidadParametros == metodos2.CantidadParametros && metodos.linas == metodos2.linas){
                                //v v v
                                if(clases.getLineas()==clases2.getLineas()){
                                    jTextArea2.append("Clase repetido: " + archivo1.clases +" en archivos " + archivo1.nombre_archivo+ "\n");
                                    this.cont_clases_repetidas++;
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"clase",clases.getId(),1));
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"clase",clases2.getId(),1));
                                }
                                //v v f
                                else if(clases.getLineas()!= clases2.getLineas()){
                                    
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"clase",clases.getId(),0.6));
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"clase",clases.getId(),0.6));
                                }
                            }else if(metodos.id != metodos2.id && metodos.CantidadParametros != metodos2.CantidadParametros && metodos.linas != metodos2.linas){
                                //v f v
                                if(clases.getLineas()==clases2.getLineas()){
                                    
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"clase",clases.getId(),0.6));
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"clase",clases2.getId(),0.6));
                                }//v f f
                                else if(clases.getLineas()!= clases2.getLineas()){
                                    
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"clase",clases.getId(),0.2));
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"clase",clases.getId(),0.2));
                                }
                            }
                        }//f v v
                        else if(clases.id != clases2.id){
                            if(metodos.id.equals(metodos2.id) && metodos.CantidadParametros == metodos2.CantidadParametros && metodos.linas == metodos2.linas){
                                if(clases.getLineas()==clases2.getLineas()){
                                    this.cont_clases_repetidas++;
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"clase",clases.getId(),0.8));
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"clase",clases2.getId(),0.8));
                                }//f v f
                                else if(clases.getLineas()!= clases2.getLineas()){
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"clase",clases.getId(),0.4));
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"clase",clases.getId(),0.4));
                                }
                            }//f f v
                            else if(metodos.id != metodos2.id && metodos.CantidadParametros != metodos2.CantidadParametros && metodos.linas != metodos2.linas){
                                if(clases.getLineas()==clases2.getLineas()){
                                    
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"clase",clases.getId(),0.4));
                                    this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"clase",clases2.getId(),0.4));
                                }
                            }
                        }
                    }
                }
                
            }
            
        }
        for (int i = 0; i <archivo1.clases.size(); i++) {
            reCla1++;
        }
        for (int i = 0; i <archivo2.clases.size(); i++) {
            reCla2++;
        }
        int toCla=reCla1+reCla2;
        RCla(toCla);
    }
    public void RCla(int CC){
        Resumen rCla = new Resumen(CC);
        listaresumenCla.add(rCla);
    }
    
    
    public void MetodosRepetidos(Archivo archivo1,Archivo archivo2){
        int met1=0,met2=0;
        for(ClasesRepetidas clases : archivo1.clases){
            for(ClasesRepetidas clases2: archivo2.clases){
                for(MetodosRepetidos metodos : clases.Metodos){
                    for(MetodosRepetidos metodos2: clases2.Metodos){
                        //v v v
                        if(metodos.id.equals(metodos2.id) && metodos.CantidadParametros == metodos2.CantidadParametros && metodos.linas == metodos2.linas){
                            jTextArea2.append("Metodo repetido \"" + metodos.getIdMetodo() +"\" en archivos " + archivo1.nombre_archivo + "\n");
                            this.cont_metodos_repetidos++;
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"metodo",metodos.getIdMetodo(),1));
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"metodo",metodos2.getIdMetodo(),1));
                        }//v v f
                        else if(metodos.id.equals(metodos2.id) && metodos.getParametros() == metodos2.getParametros() && metodos.getLineas() != metodos2.getLineas()){
                            //jTextArea2.append("Metodo repetido \"" + metodos.getIdMetodo() +"\" en archivos " + archivo1.nombre_archivo + "\n");
                            this.cont_metodos_repetidos++;
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"metodo",metodos.getIdMetodo(),0.7));
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"metodo",metodos2.getIdMetodo(),0.7));
                        }//v f v
                        else if(metodos.id.equals(metodos2.id) && metodos.getParametros() != metodos2.getParametros() && metodos.getLineas() == metodos2.getLineas()){
                            //jTextArea2.append("Metodo repetido \"" + metodos.getIdMetodo() +"\" en archivos " + archivo1.nombre_archivo + "\n");
                            this.cont_metodos_repetidos++;
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"metodo",metodos.getIdMetodo(),0.7));
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"metodo",metodos2.getIdMetodo(),0.7));
                        }//v f f
                        else if(metodos.id.equals(metodos2.id) && metodos.getParametros() != metodos2.getParametros() && metodos.getLineas() != metodos2.getLineas()){
                            //jTextArea2.append("Metodo repetido \"" + metodos.getIdMetodo() +"\" en archivos " + archivo1.nombre_archivo + "\n");
                            //this.cont_metodos_repetidos++;
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"metodo",metodos.getIdMetodo(),0.4));
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"metodo",metodos2.getIdMetodo(),0.4));
                        }//f v v
                        else if(metodos.id != metodos2.id && metodos.getParametros() == metodos2.getParametros() && metodos.getLineas() == metodos2.getLineas()){
                            //jTextArea2.append("Metodo repetido \"" + metodos.getIdMetodo() +"\" en archivos " + archivo1.nombre_archivo + "\n");
                            //this.cont_metodos_repetidos++;
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"metodo",metodos.getIdMetodo(),0.6));
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"metodo",metodos2.getIdMetodo(),0.6));
                        }//f v f
                        else if(metodos.id != metodos2.id && metodos.getParametros() == metodos2.getParametros() && metodos.getLineas() != metodos2.getLineas()){
                            //jTextArea2.append("Metodo repetido \"" + metodos.getIdMetodo() +"\" en archivos " + archivo1.nombre_archivo + "\n");
                            //this.cont_metodos_repetidos++;
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"metodo",metodos.getIdMetodo(),0.3));
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"metodo",metodos2.getIdMetodo(),0.3));
                        }//f f v
                        else if(metodos.id != metodos2.id && metodos.getParametros() != metodos2.getParametros() && metodos.getLineas() == metodos2.getLineas()){
                            //jTextArea2.append("Metodo repetido \"" + metodos.getIdMetodo() +"\" en archivos " + archivo1.nombre_archivo + "\n");
                            //this.cont_metodos_repetidos++;
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo1.getNombreArchivo(),"metodo",metodos.getIdMetodo(),0.3));
                            this.lista_puntajesEspecificos.add(new Puntajes(archivo2.getNombreArchivo(),"metodo",metodos2.getIdMetodo(),0.3));
                        }
                    }
                }
                for (int i = 0; i < clases.Metodos.size(); i++) {
                    met1++;
                }
                for (int i = 0; i < clases2.Metodos.size(); i++) {
                    met2++;
                }
                int total = met1+met2;
                TM(total);
            }
        }
    }
    
    public void TM(int M){
        Resumen rm = new Resumen(M);
        listaresumenMet.add(rm);
    }
    
    //inicio de reporte de errores
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
               + "padding: 20px;\n\t"
               +  "}\n\t"
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
               + "<div class=\"articulo\"><H3>Universidad de San Carlos de Guatemala<BR>Facultad de Ingenieria<BR>Escuela de Ciencias y Sistemas<BR>Nombre: Luis Cutzal<BR> Carne: 201700841</H3><CENTER><H2>Organizacion de Lenguajes y Compiladores 1<BR>PROYECTO 1<BR>REPORTE DE ERRORES</H2></CENTER></div>\n"
               + "<div class=\"tabla\"><UL>\n" +//No. Errores: 
               "<table style=\"margin:0 auto; \"border=3>\n\t"
               + "<tr align=\"center\" bottom=\"middle\">\n\t"
               + "<td>\n\t"
               + "<table style =\"border: 1px solid black;\">\n\t"
               + "<tr align=\"center\" bottom=\"middle\">\n\t"
               + "<td><b>Lexema</b></td>\n\t"
               + "<td><b>Tipo</b></td>\n\t"
               + "<td><b>Fila</b></td>\n\t"
               +  "<td><b>Columna</b></td>\n\t"
               +  "<td><b>Archivo</b></td>\n\t"
               + "</tr>\n\t";
               
                for(error error : Reporte_errores){
                    Html += "<tr align=\"center\" bottom=\"middle\">\n\t"
                    + "<td>" + error.valor + "</td>"
                    + "<td>" + error.tipo + "</td>"
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
            jTextArea2.append("Generar Reporte de Errores \n");
            //System.out.println("Final");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //fin reporte errores

    //inicio de reporte de tokens
    
        public void ReporteTokens(){
        LinkedList<Token> Reporte_tokens = new LinkedList<>();
        for(Archivo archivo : datos_archivos){
            Reporte_tokens.addAll(archivo.lista_tokens);
        }
        FileWriter fichero = null;
        PrintWriter pw = null;
                try {
                    String path = "ReporteTokens.html";
                    fichero = new FileWriter(path);
                    pw = new PrintWriter(fichero); 
                String Html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//ES\">\n\t"
               + "<HTML>\n\t"
               + "<HEAD>\n\t"
               + "<TITLE>REPORTE DE TOKENS</TITLE>\n\t"
               + "<style>\n\t"
               + "body {\n\t"
               + "background:#0496FF;\n\t"
               +"padding: 20px;\n\t"
               + "}\n\t"
               + "h2 {\n\t"
               + "color: #FFF;\n\t"
               + "font-family: Courier New;\n\t"
               + /*tipo de fuente*/ "}\n\t"
               + ".articulo {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Courier New;\n\t"
               + "background: #FFBC42;\n\t"
               + "border: 6px solid #0496FF;\n\t" //borde cuadro arriba
               + "color: #AAAAAFF;\n\t"
               + "padding: 13px;\n\t"
               + "}\n\t"
               + ".tabla {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Cooper Black;\n\t"
               + "background: #AAAAA;\n\t"
               + "border: 6px solid #0496FF;\n\t" //borde cuadro abajo
               + "color: #000000;\n\t"
               + "padding: 13px;\n\t"
               + "}\n\t"
               + ".fin {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Elephant Pro;\n\t"
               + "background: #7FB3D5;\n\t"
               + "border: 6px solid #F74316;\n\t"
               + "color: #000000;\n\t" +//2939B5
               "padding: 13px;\n\t"
               + "}\n\t"
               + "</style>\n\t"
               + "</HEAD>\n\t"
               + "<BODY>\n\t"
               + "<div class=\"articulo\"><H3>Universidad de San Carlos de Guatemala<BR>Facultad de Ingenieria<BR>Escuela de Ciencias y Sistemas<BR>Nombre: Luis Cutzal<BR> Carne: 201700841</H3><CENTER><H2>Organizacion de Lenguajes y Compiladores 1<BR>PROYECTO 1<BR>REPORTE DE TOKENS</H2></CENTER></div>\n"
               + "<div class=\"tabla\"><UL>\n" +//No. Errores: 
               "<table style=\"margin:0 auto; \"border=3>\n\t"
               + "<tr align=\"center\" bottom=\"middle\">\n\t"
               + "<td>\n\t"
               + "<table style =\"border: 1px solid black;\">\n\t"
               + "<tr align=\"center\" bottom=\"middle\">\n\t"
               + "<td><b>Lexema</b></td>\n\t"
               + "<td><b>Tipo</b></td>\n\t"
               + "<td><b>Fila</b></td>\n\t"
               +  "<td><b>Columna</b></td>\n\t"
               +  "<td><b>Archivo</b></td>\n\t"
               + "</tr>\n\t";
               
                for(Token tk : Reporte_tokens){
                    Html += "<tr align=\"center\" bottom=\"middle\">\n\t"
                    + "<td>" + tk.valor + "</td>"
                    + "<td>" + tk.tipo + "</td>"
                    + "<td>" + tk.fila  + "</td>"
                    +  "<td>" + tk.columna + "</td>"
                    +  "<td>" + tk.archivo + "</td>"
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
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Reportes\\"+"ReporteTokens.html");
            jTextArea2.append("Generar Reporte de Tokens \n");
            //System.out.println("Final");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    //****FIN DEL REPORTE DE TOKENS
    
    public void todo(){
        LinkedList<error> RepEr = new LinkedList<>();
        jTextArea2.append("Iniciando Analisis \n");
        jTextArea2.append("Fin Analisis \n");
        try {
            Sintactico sint=new Sintactico(new Analizador_Lexico(new BufferedReader(new StringReader(jTextArea1.getText()))));
            sint.parse();
            instrucciones=sint.instrucciones;
            for(Object ins : instrucciones){
                if(ins instanceof Comparar){
                    Comparar comp = (Comparar)ins;
                    Comparamos(comp.getRuta1(), comp.getRuta2());
                }
            }
            graficas(instrucciones);
            for(Archivo arch : datos_archivos){
                RepEr.addAll(arch.lista_errores);
            }
            for(error err:RepEr){
                jTextArea2.append(err.tipo+err.valor +" no reconocido \n");
            }
            
        } catch (Exception e) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void graficas(LinkedList<Object> instrucci) throws IOException{
        
        for(Object ins : instrucci){
           //grafica barras
            if(ins instanceof GraficaBarras){
                GraficaBarras grafica_barras = (GraficaBarras)ins;
                grafica_barras.valores();
                grafica_barras.generar_graficaBarras();
            }else if(ins instanceof LinkedList){
                //En este caso como lo trabajo se que sera una lista de variables 
                this.variables_FCA = (LinkedList<Variables>)ins;
            }
            //grafica lineas
            if(ins instanceof GraficaLineas){
                GraficaLineas grafica_lineas = (GraficaLineas)ins;
                grafica_lineas.Valores();
                grafica_lineas.generar_graficaLineas();
            }else if(ins instanceof LinkedList){
                this.variables_FCA = (LinkedList<Variables>)ins;
            }
            //grafica pie
            if(ins instanceof GraficaPie){
                GraficaPie grafica_pie = (GraficaPie)ins;
                grafica_pie.valores();
                grafica_pie.generar_graficaPie();
            }else if(ins instanceof LinkedList){
                this.variables_FCA = (LinkedList<Variables>)ins;
            }
        }
    }
        
//Reporte estadistico
        
    public void ReporteEstadistico(){
        /*LinkedList<Resumen> todo = new LinkedList<>();
        for(Resumen archivo : listaresumen){
            archivo.addAll(listaImagen);
        }*/
        FileWriter fichero = null;
        PrintWriter pw = null;
                try {
                    String path = "ReporteEstadistico.html";
                    fichero = new FileWriter(path);
                    pw = new PrintWriter(fichero); 
                String Html = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//ES\">\n\t"
               + "<HTML>\n\t"
               + "<HEAD>\n\t"
               + "<TITLE>REPORTE ESTADISTICO</TITLE>\n\t"
               + "<style>\n\t"
               + "body {\n\t"
               + "background:#0496FF;\n\t"
               +"padding: 20px;\n\t"
               + "}\n\t"
               + "h2 {\n\t"
               + "color: #FFF;\n\t"
               + "font-family: Courier New;\n\t"
               + /*tipo de fuente*/ "}\n\t"
               + ".articulo {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Courier New;\n\t"
               + "background: #FFBC42;\n\t"
               + "border: 6px solid #0496FF;\n\t" //borde cuadro arriba
               + "color: #AAAAAFF;\n\t"
               + "padding: 13px;\n\t"
               + "}\n\t"
               + ".tabla {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Cooper Black;\n\t"
               + "background: #AAAAA;\n\t"
               + "border: 6px solid #0496FF;\n\t" //borde cuadro abajo
               + "color: #000000;\n\t"
               + "padding: 13px;\n\t"
               + "}\n\t"
               + ".fin {\n\t"
               + "font-size: 14px;\n\t"
               + "font-family: Elephant Pro;\n\t"
               + "background: #7FB3D5;\n\t"
               + "border: 6px solid #F74316;\n\t"
               + "color: #000000;\n\t" +//2939B5
               "padding: 13px;\n\t"
               + "}\n\t"
               + "</style>\n\t"
               + "</HEAD>\n\t"
               + "<BODY>\n\t"
               + "<div class=\"articulo\"><H3>Universidad de San Carlos de Guatemala<BR>Facultad de Ingenieria<BR>Escuela de Ciencias y Sistemas<BR>Nombre: Luis Cutzal<BR> Carne: 201700841</H3><CENTER><H2>Organizacion de Lenguajes y Compiladores 1<BR>PROYECTO 1<BR>REPORTE ESTADISTICO</H2></CENTER></div>\n"
               + "<div class=\"tabla\"><UL>\n" +//No. Errores: 
               "<table style=\"margin:0 auto; \"border=3>\n\t"
               + "<tr align=\"center\" bottom=\"middle\">\n\t"
               + "<td>\n\t"
               + "<table style =\"border: 1px solid black;\">\n\t"
               + "<tr align=\"center\" bottom=\"middle\">\n\t"
               + "<td><b>Tipo</b></td>\n\t"
               + "<td><b>ProyectoA</b></td>\n\t"
               +  "<td><b>ProyectoB</b></td>\n\t"
               + "</tr>\n\t";
                    for (int i = 0; i < listaresumenV1.size(); i++) {//variables
                        if(i==0){
                        Html += "<tr align=\"center\" bottom=\"middle\">\n\t"
                        + "<td>" + "Total Variables"+ "</td>" //tipo
                        + "<td>" + listaresumenV1.get(1).getA() + "</td>" //proyecto A
                        + "<td>" + listaresumenV1.get(0).getA() + "</td>"//proyecto B
                        + "</tr>\n\t";
                        }
                    }
                    for (int a = 0; a < listaresumenMet.size(); a++) {//metodos
                        if(a==0){
                        Html += "<tr align=\"center\" bottom=\"middle\">\n\t"
                        + "<td>" + "Total Metodos"+ "</td>" //tipo
                        + "<td>" + listaresumenMet.get(1).getA() + "</td>" //proyecto A
                        + "<td>" + listaresumenMet.get(0).getA() + "</td>"//proyecto B
                        + "</tr>\n\t";
                        }
                    }
                    for (int b = 0; b < listaresumenCla.size(); b++) {//clases
                        if(b==0){
                        Html += "<tr align=\"center\" bottom=\"middle\">\n\t"
                        + "<td>" + "Total Clases"+ "</td>" //tipo
                        + "<td>" + listaresumenCla.get(1).getA() + "</td>" //proyecto A
                        + "<td>" + listaresumenCla.get(0).getA() + "</td>"//proyecto B
                        + "</tr>\n\t";
                        }
                    }
                    for (int c = 0; c < listaresumenC.size(); c++) {//comentarios
                        if(c==0){
                        Html += "<tr align=\"center\" bottom=\"middle\">\n\t"
                        + "<td>" + "Total Comentarios"+ "</td>" //tipo
                        + "<td>" + listaresumenC.get(1).getA() + "</td>" //proyecto A
                        + "<td>" + listaresumenC.get(0).getA() + "</td>"//proyecto B
                        + "</tr>\n\t";
                        }
                    }
                Html += "</tr></table></tr></table></UL></div>\n\t";
                for(DirImagen error : listaImagen){//barras
                    Html+="<img src="+"\"" + error.ubicacion +".png"+"\""+
                            "width=" +"\""+800 +"\""+ "height="+"\""+750 +"\""+">" 
                            + "<p> </p>" ;
                }
                Html+="\n\t"
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
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Reportes\\"+"ReporteEstadistico.html");
            jTextArea2.append("Generar Reporte Estadistico \n");
            //System.out.println("Final");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     //fin reporte estadistico
    
        public void Json(){
            
        }
        
    
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
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem10.setText("Reporte de Tokens");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Reporte JSON");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(205, 205, 205))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        

        //*************ABRIR ARCHIVO************
        if(seleccionar.showDialog(null,"Abrir")==JFileChooser.APPROVE_OPTION){
            archivo=seleccionar.getSelectedFile();
            if(archivo.canRead()){
                if(archivo.getName().endsWith("fca")){ //extencion del archivo
                    String documento = Abr(archivo);
                    jTextArea1.setText(documento);
                }else{
                    JOptionPane.showMessageDialog(null,"Error, archivo invalido, no cuenta con la extension .FCA");
                }
            }
        }
        
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        //comienza el metodo para analizar todo
        jTextArea2.setText("");
        todo();
        
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
            
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        

        //****************GUARDAR***********
        try {
            String documento = jTextArea1.getText();
            String mensaje=Guardar(archivo, documento); 
        } catch (Exception e) {
        }
           
        
        
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
         
        //***************REPORTE DE TOKENS***********
        this.ReporteTokens();
        
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        this.ReporteEstadistico();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        this.Json();
    }//GEN-LAST:event_jMenuItem11ActionPerformed
    
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
    public static javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    public static javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
