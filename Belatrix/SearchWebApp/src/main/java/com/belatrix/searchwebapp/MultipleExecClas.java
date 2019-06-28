/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belatrix.searchwebapp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscarhsalvettig
 */
public class MultipleExecClas {
    
     
    
    
    public static void main(String[] args) {
        List<String> lineas = Collections.emptyList();
        try { 
            for (String s : args)
            System.out.println("arg -> " + s);
            lineas = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
            lineas.forEach(el -> System.out.println("archivo: " + el));
        } catch (IOException ex) {
            Logger.getLogger(MultipleExecClas.class.getName()).log(Level.SEVERE, null, ex);
        }
        ExecutorService es = Executors.newCachedThreadPool();
        lineas.forEach(el -> es.execute(new ReadWebPageRunnable(el)));
        es.shutdown();
    }
    
}
