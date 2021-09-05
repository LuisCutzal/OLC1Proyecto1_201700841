/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto1_2021s2;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Domingo
 */
public class Archivo {
    String nombre_archivo = "";
    LinkedList<String> variables;
    LinkedList<String> comentarios;
    LinkedList<error> lista_errores;
    
    public Archivo(String nombre_archivo, LinkedList<String> variables, LinkedList<String> comentarios, LinkedList<error>lista_errores){
        this.nombre_archivo = nombre_archivo;
        this.variables = variables;
        this.comentarios = comentarios;
        this.lista_errores = lista_errores;
    }
    public String getNombreArchivo(){
        return this.nombre_archivo;
    }
    
    public LinkedList<String> getListaVariables(){
        return this.variables;
    }
    
    public LinkedList<String> getListaComentarios(){
        return this.comentarios;
    }
    
    public LinkedList<error> getListaErrores(){
        return this.lista_errores;
    }
}
