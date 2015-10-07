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
package edu.eci.pdsw.samples.mybatis.mappers;

import edu.eci.pdsw.samples.entities.DetallePedido;
import edu.eci.pdsw.samples.entities.Pedido;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 *
 * @author hcadavid
 */
public interface PedidoMapper{
    
    
    @Select("SELECT codigo, fecha_radicacion from ORD_PEDIDOS where codigo=#{codpedido}")
    @Results(
        value={            
            @Result(column = "codigo", property = "codigo"),
            @Result(column = "fecha_radicacion", property = "fecha"),
            @Result(property = "detallesPedido",
                    many =@Many(select="getDetallePedido"),
                    column = "codigo"
            )
        }
    )
    Pedido getPedido(int codpedido);
    
    
    
    @Select("SELECT cantidad, pedido_fk, producto_fk FROM ORD_DETALLES_PEDIDO where pedido_fk=#{codped}")
    @Results(
        value={
            @Result(column="cantidad", property = "cantidad"),
            @Result(property = "producto", 
                    one=@One(select="edu.eci.pdsw.samples.mybatis.mappers.ProductoMapper.selectProduct"),
                    column="producto_fk"
            )
        }
    )
    Set<DetallePedido> getDetallePedido(int codped);
    
    
    @Insert("INSERT INTO ORD_PEDIDOS values (#{p.fecha})")
    @Options(useGeneratedKeys = true, keyProperty = "codigo")
    void insertPedido(@Param(value="p") Pedido p);
    
    
    @Insert("INSERT INTO ORD_DETALLES_PEDIDO(cantidad,pedido_fk,producto_fk) values(xxxx,yyyyy,zzzzz)")
    void insertDetallePedido(int codpedido,DetallePedido dp);
    
    
}
