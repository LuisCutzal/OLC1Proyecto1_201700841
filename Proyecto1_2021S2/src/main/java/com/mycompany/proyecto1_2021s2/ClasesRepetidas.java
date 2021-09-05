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
public class ClasesRepetidas {
    public int canLineas;
    public String id;
    
    public ClasesRepetidas(int Lineas, String id){
        this.canLineas=Lineas;
        this.id=id;
    }
    public int getLineas(){
        return this.canLineas;
    }
    public String getId(){
        return this.id;
    }
}
