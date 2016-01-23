
package de.khiem.offsite.backup.io;

import de.khiem.offsite.backup.data.Backup;
import de.khiem.offsite.backup.data.BackupOwner;
import de.khiem.offsite.backup.data.Box;
import de.khiem.offsite.backup.data.BoxKey;
import de.khiem.offsite.backup.data.Member;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author kimyoung
 */
public class Main {

    public Main() {
    }
    public static void main(String[] args) {
        Main m=new Main();
        m.genData();
        
        String f= "/tmp/back.bin";
        try{
            new ReadWrite().write(m.bu, new FileOutputStream(f));
            Backup b= new ReadWrite().read(new FileInputStream(f));
            
            System.out.println("boxes:" + m.bu.getBoxes().size() + "=" + b.getBoxes().size());
            System.out.println("owner:" + new String(m.bu.getOwner().getUsername()) + "=" + new String(b.getOwner().getUsername()));
            
            
        }catch (Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    
    List<Long> uids=Arrays.asList(1l,2l,3l,4l,5l,6l,7l,8l,9l,100l);
    Backup bu;
    
    void genData(){
        bu= new Backup();
        long admin =uids.get(uids.size()-1);
        
        List<Box> boxes= genBox(admin);
        for (Box b: boxes){
            List<Member> mem = genMembers(admin, b.getOwner().getUid());
            b.setMembers(mem);
        }
        bu.setBoxes(boxes);
        bu.setCreated(9000l);
        BackupOwner o =new BackupOwner();
        o.setUid(admin);
        o.setUsername("adminname".getBytes());
        bu.setOwner(o);
        
        
    }
   
    
   
    
    List<Member> genMembers(long admin, long exc){
        Random r= new Random();
        List<Member> m = new ArrayList<>();
        for (int i=1;i<5;++i){
            int ind = r.nextInt(uids.size());
            long uid =uids.get(ind);
            
            if (exc==uid)
                continue;;
            
            Member mb=new Member();
            mb.setAdminId(admin);
            mb.setName(("user " + uid).getBytes());
            mb.setType(uid==admin ? Member.TYPE.ADMIN:Member.TYPE.EMP);
            mb.setPermission(Member.PERM.MEMBER);
            mb.setUid(i);
            m.add(mb);
        }
        return m;
    }
  
    List<Box> genBox(long admin){
        long created = 1000l;
         Random r= new Random();
        List<Box> boxes =new ArrayList<>();
        for (int i=1;i<=5;++i){
            Box b= new Box();
            int ind = r.nextInt(uids.size());
            long uid =uids.get(ind);
            
            b.setAuthor(("author " + uid).getBytes());
            b.setCreated(created);
            b.setId(i);
            b.setName(("box "+ i + " of " + uid).getBytes());
            BoxKey k=new BoxKey();
            k.setMetaKey("mk".getBytes());
            k.setMasterKey("stk".getBytes());
            b.setKey(k);
            boxes.add(b);
            created+=100l;
            
            Member owner = new Member();
            owner.setAdminId(admin);
            owner.setName(b.getAuthor());
            owner.setType(uid==admin ? Member.TYPE.ADMIN:Member.TYPE.EMP);
            owner.setUid(uid);
            b.setOwner(owner);
            
            boxes.add(b);
        }
        return boxes;
    }
    
}
