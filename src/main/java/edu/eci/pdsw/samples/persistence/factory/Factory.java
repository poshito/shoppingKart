/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence.factory;

import edu.eci.pdsw.samples.persistence.DaoPedido;
import edu.eci.pdsw.samples.persistence.DaoProducto;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public abstract class Factory {
    private static Factory instance=null;
    
    public static Factory getInstance(){
        if (instance==null){
            Properties prop = new Properties();
            InputStream input = null;
            
            try {
               
                input = ClassLoader.getSystemResourceAsStream("config.properties");
                prop.load(input);
                
                if (prop.getProperty("persistence").equals("mybatis")){
                    instance = new MyBatisMapperFactory();
                }
                else if (prop.getProperty("persistence").equals("jdbc")){
                    instance=new JDBCDaoFactory();
                }
                else{
                    throw new RuntimeException("Invalid factory configuration.");
                }
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Factory.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Invalid factory configuration.",ex);
            } catch (IOException ex) {
                Logger.getLogger(Factory.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Invalid factory configuration.",ex);
            }
        }
        
        return instance;
    }
    
    public abstract void beginSession() throws PersistenceException;
    
    public abstract DaoProducto getDaoProducto();
    
    public abstract DaoPedido getDaoPedido();
    
    public abstract void commitTransaction() throws PersistenceException;
    
    public abstract void rollbackTransaction() throws PersistenceException;
    
    public abstract void endSession() throws PersistenceException;
}
