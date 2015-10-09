/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence.factory;

import edu.eci.pdsw.samples.mybatis.mappers.PedidoMapper;
import edu.eci.pdsw.samples.mybatis.mappers.ProductoMapper;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoPedido;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoProducto;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author usuario
 */
public class MyBatisMapperFactory extends DaoFactory {
    private static SqlSession sqlss;
    
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }
    
    @Override
    public void beginSession() throws PersistenceException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        sqlss = sessionfact.openSession();
    }

    @Override
    public void endSession() throws PersistenceException {
        sqlss.close();
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        sqlss.commit();     
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
        sqlss.rollback();
    }
    
    @Override
    public DaoProducto getDaoProducto() {
        ProductoMapper promp=sqlss.getMapper(ProductoMapper.class);
        return (DaoProducto)promp;
    }

    @Override
    public DaoPedido getDaoPedido() {
        PedidoMapper pedmp=sqlss.getMapper(PedidoMapper.class);
        return (DaoPedido)pedmp;
    }
    
}


