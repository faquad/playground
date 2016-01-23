
package de.khiem.offsite.rx.data;

import java.util.Date;

/**
 *
 * @author kimyoung
 */
public class DataFactory {
    public static void main(String[] args) {
        Access access = ImmutableAccess.builder()
                .bid(1).ts(new Date()).uid(2l).build();
        System.out.println("access:" + access);
        BEvent evt = ImmutableBEvent.builder()
                .ts(new Date())
                .boxId(1l).boxOp(0).build();
        System.out.println("be:" + evt);
    }
}
