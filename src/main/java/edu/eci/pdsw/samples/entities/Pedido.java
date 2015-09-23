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
package edu.eci.pdsw.samples.entities;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author hcadavid
 */
public class Pedido {
    
    private int codigo;
    private Timestamp fecha;
    private Set<DetallePedido> detallesPedido;

    public Pedido(int codigo, Timestamp fecha) {
        this.codigo = codigo;
        this.fecha = fecha;
        detallesPedido=new LinkedHashSet<>();
    }

    public Pedido() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Set<DetallePedido> getDetallesPedido() {
        return detallesPedido;
    }

    public void setDetallesPedido(Set<DetallePedido> detallesPedido) {
        this.detallesPedido = detallesPedido;
    }

    @Override
    public String toString() {
        return "["+codigo+","+fecha+"]"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
