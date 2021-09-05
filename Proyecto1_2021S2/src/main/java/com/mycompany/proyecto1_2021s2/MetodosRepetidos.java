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
public class MetodosRepetidos {
    public int linas;
    public int CantidadParametros;
    public String id;
    public MetodosRepetidos(int lineas, int parametros, String id){
        this.linas=lineas;
        this.CantidadParametros=parametros;
        this.id=id;
    }
    
    public int getLineas(){
        return this.linas;
    }
    public int getParametros(){
        return this.CantidadParametros;
    }    
    public String getIdMetodo(){
        return this.id;
    }
    
}
