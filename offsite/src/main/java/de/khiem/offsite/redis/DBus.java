/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.redis;

/**
 *
 * @author khiemnguyen
 */
public interface  DBus<T> {
    void publish(String topicPath, T evt);
    void addListener(String topicPath, EventHandler<T> listerner);
}
