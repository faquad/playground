
package de.khiem.offsite.db;

/**
 *
 * @author kimyoung
 */
public  interface  QueryResult <T>{
    T result();
    TransactionContext transaction();
}
