/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.backup.data;

import java.util.List;

/**
 *
 * @author kimyoung
 */
public class Backup {
  long created;
  
  BackupOwner owner;
  List<Box> boxes;
        
}
