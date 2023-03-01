package com.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;

public class PropertiesComponentExample {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        CamelContext context = new DefaultCamelContext();

        PropertiesComponent properties = context.getComponent("properties", PropertiesComponent.class);   // attach prop-component to camel context
        properties.setLocation("classpath:PropertiesComponentExample.properties");                              // set path for input file

        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from ("timer://mytimer")                // timer is like cron
                        .log("{{message}}");                // use message-key from property-file
            }
        });

        context.start();                                    // start camel context
        main.getCamelContexts().add(context);               // attach camel context
        main.run();                                         // start camel main

    }
}