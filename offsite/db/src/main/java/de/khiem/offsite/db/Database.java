
package de.khiem.offsite.db;


public interface Database {
     <T> QueryResult<T>  findEntityById(Long id);
     <T> QueryResult<T> query(DbQuery query); 
     
     void save(TransactionContext ctx); 
     void delete(TransactionContext  ctx);
}
