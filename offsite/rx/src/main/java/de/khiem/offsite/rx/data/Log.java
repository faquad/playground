/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.rx.data;

import java.util.Date;
import java.util.Optional;
import org.immutables.value.Value;

/**
 *
 * @author kimyoung
 */
@Value.Immutable
public interface Log {
    long getUid();
    Date getTs();
    int getOpt();
    int getResult();
    Optional<String> getMsg();
}

