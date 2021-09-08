/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto1_2021s2;

import java.util.LinkedList;

/**
 *
 * @author Domingo
 */
public class ClasesRepetidas {
    public int canLineas;
    public String id;
    public LinkedList<MetodosRepetidos> Metodos = new LinkedList<>();//se agrego una lista de tipo metodos y se inicializo, se hizo de esta forma para no afectar al constructor con otro parametro
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
