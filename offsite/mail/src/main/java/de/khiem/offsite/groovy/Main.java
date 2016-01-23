/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.offsite.groovy;

import groovy.lang.GroovyShell;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

/**
 *
 * @author kimyoung
 */
public class Main {

    public static void main(String[] args) {
        //new Main().listScriptEngine();
        //new Main().evalScript();
        Main m= new Main();
        //m.evalMe();
        m.evaluateShell();
    }

    void listScriptEngine() {
        List<ScriptEngineFactory> ef = new ScriptEngineManager().getEngineFactories();
        ef.forEach(e -> {
            System.out.println("e:" + e.getEngineName() + ":" + e.getEngineVersion() + ",v:" + e.getLanguageVersion());
        });

    }

    void evalScript() {
        try {
            ScriptEngine en = new ScriptEngineManager().getEngineByName("groovy");
            en.eval("println 'hello its groovy!'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    void evalMe(){
        groovy.util.Eval.me("println 'OK!'");
        System.out.println(groovy.util.Eval.x(1, "100 + x"));
        System.out.println(groovy.util.Eval.xy(1, 2, "x + y"));
        System.out.println(groovy.util.Eval.xyz(3,4,5, "x*y*z"));
    }
    
    void evaluateShell(){
        GroovyShell shell = new GroovyShell();
        System.out.println(shell.evaluate("7*7"));
        
    }
    
    void j2g(){
        
    }
}
