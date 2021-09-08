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
public class Resumen {
    //solo contiene el tipo, puntaje 1 y puntaje 2
    
    public int puntajeA;
    //Constructor para agregar Tokens desde los archivos jflex y cup 

    public Resumen(int puntajeA) {
        this.puntajeA = puntajeA;
    }
    public int getA(){
        return this.puntajeA;
    }
    
}
