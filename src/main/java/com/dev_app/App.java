/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package com.dev_app;

 import org.springframework.boot.builder.SpringApplicationBuilder;
 import org.springframework.context.ConfigurableApplicationContext;

import com.dev_app.Controlor.MainControlor;
 
 public class App {
     public static void main(String[] args) 
     {
        // REMARQUE : Le point d'entrée de l'application SPRING peut se faire que dans le main => Web application could not be started as there was no org.springframework.boot.web.servlet.server.ServletWebServerFactory bean defined in the context.
        // Le serveur Spring va être lancé
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .sources(MainControlor.class)
                .run(args);

        // Si je veut arréter le serveur web, utilise => context.close();
     }
 }