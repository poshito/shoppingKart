/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.DetallePedido;
import edu.eci.pdsw.samples.entities.Pedido;
import edu.eci.pdsw.samples.entities.Producto;
import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author usuario
 */
public class JDBCDaoPedido implements DaoPedido{
    
    Connection con;
    
    public JDBCDaoPedido(Connection con){
        this.con=con;
    }
    
    @Override
    public void save(Pedido p) throws PersistenceException{
        PreparedStatement ps;
        PreparedStatement pss;
        try{
            ps=con.prepareStatement("INSERT INTO ORD_PEDIDOS (codigo, fecha_radicacion) VALUES (null,?);", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, p.getFecha());
            ps.executeUpdate();
            ResultSet i = ps.getGeneratedKeys();
            int clave=0;
            while(i.next()){
                clave=i.getInt(1);
            }
            p.setCodigo(clave);
            for (DetallePedido dp : p.getDetallesPedido()) {
                pss=con.prepareStatement("INSERT INTO ORD_DETALLES_PEDIDO (pedido_fk, producto_fk, cantidad) VALUES (?,?,?);");  
                pss.setInt(1, p.getCodigo());
                pss.setInt(2, dp.getProducto().getCodigo());
                pss.setInt(3, dp.getCantidad());
                pss.execute();
            }
            
            
        }catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while save a order.",ex);
        }
    }

    @Override
    public Pedido load(int id) throws PersistenceException{
        PreparedStatement ps;
        PreparedStatement pss;
        try{
            ps=con.prepareStatement("SELECT codigo, fecha_radicacion as fecha FROM ORD_PEDIDOS WHERE codigo=?");
            pss=con.prepareStatement("SELECT ORD_PRODUCTOS.codigo as cod, nombre as nom, precio, cantidad FROM ORD_PRODUCTOS INNER JOIN ORD_DETALLES_PEDIDO ON ORD_PRODUCTOS.codigo=producto_fk INNER JOIN ORD_PEDIDOS ON pedido_fk=ORD_PEDIDOS.codigo WHERE ORD_PEDIDOS.codigo=?");
            ps.setInt(1, id);
            pss.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            ResultSet rss= pss.executeQuery();
            if(rs.next()){
                Pedido ped = new Pedido(id, rs.getTimestamp(2));
                while(rss.next()){
                    Producto prod = new Producto(rss.getInt(1),rss.getString(2),rss.getInt(3));
                    DetallePedido detalle =new DetallePedido(rss.getInt(4), prod);
                    ped.addDetalle(detalle);
                }
                return ped;
                
            }else{
                throw new PersistenceException("No row with the given id:"+id);
            }
        }catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while save a order.",ex);
        }
    }

    @Override
    public void update(Pedido p) throws PersistenceException{
        PreparedStatement ps;
        try{
            ps=con.prepareStatement("UPDATE ORD_PEDIDOS SET fecha_radicacion VALUE ? WHERE codigo=?");
            ps.setTimestamp(2, p.getFecha());
            ps.setInt(3, p.getCodigo());
        }catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while save a order.",ex);
        }
    }

    @Override
    public List<Pedido> loadAll() throws PersistenceException {
        PreparedStatement ps;
        List<Pedido> lista = new LinkedList<Pedido>();
        try{
            ps=con.prepareStatement("SELECT * FROM ORD_PEDIDOS");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Pedido p = new Pedido(rs.getInt(1), rs.getTimestamp(2));
                lista.add(p);
            }
        }catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while save a order.",ex);
        }
        
        return lista;
    }
    
}
