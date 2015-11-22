
package de.khiem.offsite.db;

import javax.persistence.EntityManager;

/**
 *
 * @author kimyoung
 */
public class TransactionBuilder {
   
    
    class TransCtx implements TransactionContext{
        private final EntityManager em;
        TransCtx(EntityManager em) {
            this.em = em;
        }
        
        @Override
        public void begin() {
            em.getTransaction().begin();
        }

        @Override
        public void commit() {
            em.getTransaction().commit();
            em.close();
        }
    }
    
    class NamedDBQuery implements DbQuery{
        private final String name;
        private DbQueryPapam param;

        public NamedDBQuery(String name) {
            this.name = name;
        }

        public NamedDBQuery(String name, DbQueryPapam param) {
            this.name = name;
            this.param = param;
        }
        
        @Override
        public String name() {
            return this.name;
        }

        @Override
        public DbQueryPapam param() {
            return  param;
        }
    
    }
    
    
}
