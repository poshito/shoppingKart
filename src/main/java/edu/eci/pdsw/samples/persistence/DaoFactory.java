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
package edu.eci.pdsw.samples.persistence;

import edu.eci.pdsw.samples.persistence.factory.JDBCDaoFactory;

/**
 *
 * @author hcadavid
 */
public abstract class DaoFactory {
    
    protected DaoFactory(){}
    
    private static final ThreadLocal<DaoFactory> perThreadInstance = new ThreadLocal<DaoFactory>() {
        @Override
        protected DaoFactory initialValue() {    
            return new JDBCDaoFactory();
        }
    };
    
    public static DaoFactory getInstance(){          
        return perThreadInstance.get();
    }


    /*
        //EL ESQUEMA ANTERIOR ES UNA ALTERNATIVA AL MECANISMO DE FÁBRICA
        //ABSTRACTA VISTO ANTERIORMENTE:
        
        private static final DaoFactory instance=null;
    
        public static DaoFactory getInstance(){          
            if (instance=null){
                instance= ...
            }
            return instance;
        }
        
        //EN PRINCIPIO FUNCIONAN IGUAL, PERO EL NUEVO MECANISMO
        //GARANTIZA CONSISTENCIA CUANDO LA FÁBRICA SEA UTILIZADA
        //CONCURRENTEMENTE.
    */
    
    
    
    
    public abstract void beginSession() throws PersistenceException;
    
    public abstract DaoProducto getDaoProducto();
    
    public abstract DaoPedido getDaoPedido();
    
    public abstract void commitTransaction() throws PersistenceException;
    
    public abstract void rollbackTransaction() throws PersistenceException;
    
    public abstract void endSession() throws PersistenceException;
}
