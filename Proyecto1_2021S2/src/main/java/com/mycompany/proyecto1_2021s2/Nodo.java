/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto1_2021s2;

import java.util.ArrayList;

/**
 *
 * @author Domingo
 */
public class Nodo {
    
    /*
    *Un token es un par que consiste en un nombre de token y un valor de atributo opcional.
    *Un lexema es una secuencia de caracteres en el programa fuente, que coinciden con el patrón para un token y que el analizador léxico identifica como una instancia de este tóken.
    *Un patrón es una descripción de la forma que pueden tomar los lexemas de un token.
    */
    public String token; //identificar el token que seria el simbolo que es, lo que representa, si es digito o letras
    public String lexema;//lo que viene, si es ID que lexema lo representa
    public int linea;
    public int column;

    public ArrayList<Nodo> hijos = new ArrayList<Nodo>(); //sirve para el nodo
    
    //constructor para la clase nodo, se puede extender con mas componentes que necesitemos para el proyecto
    public Nodo(String token, String lexema, int linea, int column){
        this.token = token;
        this.lexema = lexema;
        this.linea = linea;
        this.column = column; 
       
        this.hijos = new ArrayList<Nodo>();
    }
    
    public void AddHijo(Nodo nuevo){
        this.hijos.add(nuevo);
    }
    public String getTkn(){
        return this.token;
    }
    public String getLex(){
        return this.lexema;
    }    
    public int getLine(){
        return this.linea;
    }  
    public int getCol(){
        return this.column;
    }    
}