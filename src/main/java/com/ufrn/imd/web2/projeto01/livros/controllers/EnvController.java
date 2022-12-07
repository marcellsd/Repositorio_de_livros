package com.ufrn.imd.web2.projeto01.livros.controllers;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/env")
public class EnvController {
    


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void changeEnv(@RequestBody String env) {
        try{    
            ClassLoader classLoader = getClass().getClassLoader();
            //InputStream inputStream = classLoader.getResourceAsStream("application.properties");
            URL filePath = classLoader.getResource("application.properties");
            File f1 = new File(filePath.getPath());
            FileWriter fw = new FileWriter(f1);
            BufferedWriter out = new BufferedWriter(fw);
            out.write("spring.profiles.active="+env);
            out.flush();
            out.close();
        }
            
            
             catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
