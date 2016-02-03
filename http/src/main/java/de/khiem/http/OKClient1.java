/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author khiemnguyen
 */
public class OKClient1 {
    
    public static void main(String[] args) {
        try {
            new OKClient1().testGet();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
    
    private void testGet() throws Exception{
        final OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url("http://www.heise.de")
                .header("Accept", "*/*").build();
        Response resp = client.newCall(req).execute();
        System.out.println("resp:" + resp.body().string());
    }    
}
