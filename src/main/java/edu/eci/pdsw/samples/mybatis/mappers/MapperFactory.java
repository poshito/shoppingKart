/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.mybatis.mappers;

import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.factory.JDBCDaoFactory;
import edu.eci.pdsw.samples.persistence.factory.MyBatisMapperFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author usuario
 */
public abstract class MapperFactory {
    private static Properties prop=null;
    
    protected MapperFactory(){}
    
    private static final ThreadLocal<MapperFactory> perThreadInstance = new ThreadLocal<MapperFactory>() {
        @Override
        protected MapperFactory initialValue() {    
            return new MyBatisMapperFactory();
        }
    };
    
    public static MapperFactory getInstance(){          
        return perThreadInstance.get();
    }
    
    
    public abstract void beginSession() throws PersistenceException;
    
    public abstract ProductoMapper getProductoMapper();
    
    public abstract PedidoMapper getPedidoMapper();
    
    public abstract void commitTransaction() throws PersistenceException;
    
    public abstract void rollbackTransaction() throws PersistenceException;
    
    public abstract void endSession() throws PersistenceException;
    
}
