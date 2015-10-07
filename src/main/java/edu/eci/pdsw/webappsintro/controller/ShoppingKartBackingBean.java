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
package edu.eci.pdsw.webappsintro.controller;

import edu.eci.pdsw.samples.entities.DetallePedido;
import edu.eci.pdsw.samples.entities.Pedido;
import edu.eci.pdsw.samples.entities.Producto;
import edu.eci.pdsw.webappsintro.model.ServicesFacade;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.component.log.Log;

/**
 *
 * @author hcadavid
 */
@ManagedBean
@SessionScoped
public class ShoppingKartBackingBean {
    private static final Logger LOG = Logger.getLogger(ShoppingKartBackingBean.class.getName());
    private Producto producto = new Producto(31,"IMAC",6000000);
    private int cantidad =1;
    private DetallePedido item;
    private int idPedido;
    private Pedido p = new Pedido();
    private Set<DetallePedido> productos= p.getDetallesPedido();

    public Set<DetallePedido> getCarrito() {
        productos=p.getDetallesPedido();
        return productos;
    }
    
    public int getCodigoPedido(){
        System.out.println(p.getCodigo());
        return p.getCodigo();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
        System.out.println(idPedido);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public DetallePedido getItem() {
        return item;
    }

    public void setItem(DetallePedido item) {
        this.item = item;
        borrar();
    }
    
    public void setProductos(Set<DetallePedido> productos) {
        this.productos = productos;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void agregarProducto(){
        productos.add(new DetallePedido(cantidad, producto));
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        agregarProducto();
    }
    
    public List<Producto> getProductos(){
        return ServicesFacade.getInstance().getProductos();
    }
    
    public void borrar(){
        productos.remove(item);
    }
    public int calcularCosto(){
        int resp=0;
        for (DetallePedido p1 : productos) {
            resp+=p1.getCantidad()*p1.getProducto().getPrecio();
        }
        return resp;
    }
    
    public void savePedido(){
        p.setFecha(new Timestamp(new Date().getTime()));
        ServicesFacade.getInstance().SavePedido(p);
    }
    
    public void setDetallesPedido(){
        p=ServicesFacade.getInstance().loadPedido(idPedido);
        productos = p.getDetallesPedido();
    }
}
