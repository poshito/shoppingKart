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
package edu.eci.pdsw.samples.persistence.factory;

import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoPedido;
import edu.eci.pdsw.samples.persistence.jdbcimpl.JDBCDaoProducto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoFactory extends DaoFactory {

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
