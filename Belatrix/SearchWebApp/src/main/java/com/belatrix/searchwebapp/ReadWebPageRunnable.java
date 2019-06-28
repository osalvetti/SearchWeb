/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belatrix.searchwebapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author oscarhsalvettig
 */
public class ReadWebPageRunnable implements Runnable {

    List<String> partes = new ArrayList<>();

    private final String name;

    public ReadWebPageRunnable(String name) {
        this.name = name;
    }
    String nombreCompleto;
    String pathFile;

    @Override
    public void run() {
        String patString = "Harris";     
        Pattern pattern = Pattern.compile(patString);
        Document doc;
        try {
            pathFile = Constants.PATH_FILE;
            String data = Constants.PARSE_OCCURRENCES + patString + "\r";
            String archivo = this.name.substring(7);
            String[] nomAr = archivo.split("\\.");
            nombreCompleto = nomAr[0] + nomAr[1] + ".txt";
            Files.write(Paths.get(pathFile + nombreCompleto), data.getBytes());
            doc = Jsoup.connect(this.name).get();
            Elements div = doc.select(Constants.SELECTION);
            for (Element ele : div) {
                String parte = ele.text();
                String[] subparte = parte.split(" ");
                partes.addAll(Arrays.asList(subparte));
            }
            List<String> result2 = partes.stream().filter(p -> pattern.matcher(p).find())
                    .collect(Collectors.<String>toList());

            for (String value : result2) {
                this.writeToFile(value);
                this.writeToFile("\r");

            }
            this.writeToFile(Constants.TOTAL);
            this.writeToFile(String.valueOf(result2.size()));

        } catch (IOException ex) {

            this.writeToFile(Constants.PARSE_ERROR);
            this.writeToFile(ex.getMessage());

        }

    }

    public void writeToFile(String text) {
        try {
            Files.write(
                    Paths.get(pathFile + nombreCompleto),
                    text.getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
            Logger.getLogger(ReadWebPageRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
