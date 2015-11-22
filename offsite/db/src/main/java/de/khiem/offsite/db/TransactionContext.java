
package de.khiem.offsite.db;

/**
 *
 * @author kimyoung
 */
public interface TransactionContext{
    /**
     * starts an transaction
    */
    void begin();
    /**
     * commit transaction, will close & not reusable 
    */
    void commit();
}
