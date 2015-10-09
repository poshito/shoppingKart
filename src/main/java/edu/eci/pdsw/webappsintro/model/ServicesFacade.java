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

import edu.eci.pdsw.samples.entities.Pedido;
import edu.eci.pdsw.samples.entities.Producto;
import edu.eci.pdsw.samples.mybatis.mappers.ProductoMapper;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class ServicesFacade {
    
    private final static ServicesFacade instance=new ServicesFacade();
    private DaoFactory df=DaoFactory.getInstance();
    
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
        List<Producto> lista = new LinkedList<>();
        try {
            df.beginSession();
            DaoProducto dpr= df.getDaoProducto();
            lista=dpr.loadAll();            
            df.endSession();
            
            
        } catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public void SavePedido(Pedido p){
        try {
            df.beginSession();
            DaoPedido dp = df.getDaoPedido();
            dp.save(p);
            df.commitTransaction();
            df.endSession();
        } catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Pedido> loadAllPedidos(){
        List<Pedido> lista = new LinkedList<Pedido>();
        try {
            df.beginSession();
            DaoPedido dp = df.getDaoPedido();
            lista= dp.loadAll();
            df.endSession();
        } catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public Pedido loadPedido(int idPedido){
        Pedido p = new Pedido(idPedido, new Timestamp(new Date().getTime()));
        try{
            df.beginSession();
            DaoPedido dp = df.getDaoPedido();
            p = dp.load(idPedido);
            df.endSession();
        }catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
}
