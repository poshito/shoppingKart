/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.webappsintro.model;

import edu.eci.pdsw.samples.entities.Producto;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hcadavid
 */
public class ServicesFacade {
    
    private final static ServicesFacade instance=new ServicesFacade();
    
    private ServicesFacade(){
        
    }
    
    public static ServicesFacade getInstance(){
        return instance;
    }
    
    /**
     * Retornar el listado de todos los productos disponibles
     * @return el listado de productos disponibles
     */
    public List<Producto> getProductos(){
        return new LinkedList<>();
    }
    
}
