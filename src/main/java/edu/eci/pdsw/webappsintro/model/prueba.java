/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.webappsintro.model;

import edu.eci.pdsw.samples.entities.Producto;
import java.util.List;

/**
 *
 * @author usuario
 */
public class prueba {
    
    public static void main(String args[]){
        ServicesFacade sf = ServicesFacade.getInstance();
        List<Producto> lista;
        lista=sf.getProductos();
        for (Producto p : lista) {
            System.out.println(p.getNombre());
        }
    }
}
