package com.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;
public class ExceptionHandler {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                onException(IllegalArgumentException.class)
                        .handled(true)
                        .to("file://error-handling/exception/");
                onException(NullPointerException.class)
                        .continued(true);

                from("file://error-handling/in?noop=true")             // research : why is the root-path used, not the sub-pom-path as expected?
                        .log("file found")
//                        .process("Exception")
                        .to("file://error-handling/out/");
            }
        });

        context.start();                                    // start camel context
        main.getCamelContexts().add(context);               // attach camel context
        main.run();                                         // start camel main

    }
}