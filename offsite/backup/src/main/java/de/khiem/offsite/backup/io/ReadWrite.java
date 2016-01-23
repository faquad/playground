/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.backup.io;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.khiem.offsite.backup.data.Backup;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author kimyoung
 */
public class ReadWrite {

    public ReadWrite() {
    
    }
        
    public void write(Backup backup, OutputStream out) throws IOException{
        Kryo k= new Kryo();
        Output op =new Output(out);
        k.writeObject(op, backup);
        op.close();
    }
    
    public Backup read(InputStream in) throws IOException{
        Kryo k= new Kryo();
        Input i = new Input(in);
        Backup bu = k.readObject(i, Backup.class);
        i.close();
        return bu;
    }
}
