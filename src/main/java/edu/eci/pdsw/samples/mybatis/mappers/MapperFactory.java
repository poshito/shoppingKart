/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.mybatis.mappers;

import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.mybatis.mappers.MyBatisMapperFactory;
import java.util.Properties;

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
    
    
    public static MyBatisMapperFactory getInstance(){          
        return (MyBatisMapperFactory) perThreadInstance.get();
    }
    
     public abstract void beginSession() throws PersistenceException;
    
    public abstract DaoProducto getDaoProducto();
    
    public abstract DaoPedido getDaoPedido();
    
    public abstract void commitTransaction() throws PersistenceException;
    
    public abstract void rollbackTransaction() throws PersistenceException;
    
    public abstract void endSession() throws PersistenceException;
}
