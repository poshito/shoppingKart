/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence.mybatis.mappers;

import edu.eci.pdsw.samples.mybatis.mappers.MapperFactory;
import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoPedido;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoProducto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author usuario
 */
public class MyBatisMapperFactory extends MapperFactory {
    
    Connection con;
    
    private Connection openConnection() throws PersistenceException{
        String url="jdbc:mysql://desarrollo.is.escuelaing.edu.co:3306/bdprueba";
            String driver="com.mysql.jdbc.Driver";
            String user="bdprueba";
            String pwd="bdprueba";
                        
        try {
            Class.forName(driver);
            Connection _con=DriverManager.getConnection(url,user,pwd);
            _con.setAutoCommit(false);
            return _con;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new PersistenceException("Error on connection opening.",ex);
        }
    }
    
    @Override
    public void beginSession() throws PersistenceException {
        try {
            if (con==null || con.isClosed()){            
                con=openConnection();
            }
            else{
                throw new PersistenceException("Session was already opened.");
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while opening a JDBC connection.",ex);
        }
        
    }

    @Override
    public DaoProducto getDaoProducto() {        
        return new JDBCDaoProducto(con);
    }

    @Override
    public DaoPedido getDaoPedido() {
        return new JDBCDaoPedido(con);
    }

    @Override
    public void endSession() throws PersistenceException {
        try {
            if (con==null || con.isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                con.close();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        try {
            if (con==null || con.isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                con.commit();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }        
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
                try {
            if (con==null || con.isClosed()){
                throw new PersistenceException("Conection is null or is already closed.");
            }
            else{
                con.rollback();
            }            
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.",ex);
        }
    }
    
}


