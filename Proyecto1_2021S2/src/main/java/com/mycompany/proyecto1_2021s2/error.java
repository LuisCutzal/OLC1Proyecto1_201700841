/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto1_2021s2;

/**
 *
 * @author Domingo
 */
public class error {
    public String tipo;
    public String valor;
    public String archivo;
    public int fila;
    public int columna;

     //Constructor para agregar errores desde los archivos jflex y cup 
    
    public error(String tipo, String valor, int fila, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }
    
     //Constructor para agregar errores encontrados en analisis indicando el archivo

    public error(String tipo, String valor, String archivo, int fila, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.archivo = archivo;
        this.fila = fila;
        this.columna = columna;
    }
    
}

