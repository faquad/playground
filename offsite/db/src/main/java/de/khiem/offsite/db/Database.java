
package de.khiem.offsite.db;


public interface Database {
     TransactionContext transContext();
    
     <T extends Entity> QueryResult findById (Long id, Class<T> cls);
     
     <T> QueryResult<T> query(DbQuery query); 
     
     void save(TransactionContext ctx); 
     
     void delete(TransactionContext  ctx);
     
     <T extends Dao> T daoInstance(Class<T> cls);
}
