package de.khiem.offsite.backup.service;

import de.khiem.offsite.backup.data.Backup;
import de.khiem.offsite.backup.data.Box;
import de.khiem.offsite.backup.data.Member;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kimyoung
 */

public interface BackupReader {
    Backup getBackup();
    
    void validateAndRead();
    
    Box findById(long id);
    
    List<Box> listByOwner(long uid);
    List<Box> listAll();
    Map<Member, List<Box>> groupByUser();
}
