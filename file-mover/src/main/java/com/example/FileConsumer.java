package com.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;

public class FileConsumer {
    public static void main(String[] args) throws Exception {
        Main main = new Main();                             // create camel main object < camel2 specific?
        CamelContext context = new DefaultCamelContext();   // init camel base context

        context.addRoutes(new RouteBuilder() {              // use RouteBuilder to create a new camel route
            @Override
            public void configure() throws Exception {
                from("file://file-mover/in?noop=true")             // research : why is the root-path used, not the sub-pom-path as expected?
                        .log("file found")
                        .to("file://file-mover/out/");
            }
        });

        context.start();                                    // start camel context
        main.getCamelContexts().add(context);               // attach camel context
        main.run();                                         // start camel main

    }
}