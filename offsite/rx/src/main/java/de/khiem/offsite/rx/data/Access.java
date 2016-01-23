/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.rx.data;

import java.util.Date;
import org.immutables.value.Value;

/**
 *
 * @author kimyoung
 */
@Value.Immutable
public interface Access {
    long getUid();
    long getBid();
    Date getTs();
}
