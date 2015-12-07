/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.backup.data;

/**
 *
 * @author kimyoung
 */
public class HbaseLocation implements FileLocation{
    byte[] table;
    byte[] row;
    byte[] family;
    byte[] column;

    public byte[] getTable() {
        return table;
    }

    public void setTable(byte[] table) {
        this.table = table;
    }

    public byte[] getRow() {
        return row;
    }

    public void setRow(byte[] row) {
        this.row = row;
    }

    public byte[] getFamily() {
        return family;
    }

    public void setFamily(byte[] family) {
        this.family = family;
    }

    public byte[] getColumn() {
        return column;
    }

    public void setColumn(byte[] column) {
        this.column = column;
    }
    
    
}
