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
public class  BoxNote<T> extends Node{
    T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
    
}
