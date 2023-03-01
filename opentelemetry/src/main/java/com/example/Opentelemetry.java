package com.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;
import org.apache.camel.opentracing.OpenTracingTracer;

public class Opentelemetry {
    public static void main(String[] args) throws Exception {
        Main main = new Main();                             // create camel main object < camel2 specific?
        CamelContext context = new DefaultCamelContext();   // init camel base context

        OpenTracingTracer ottracer = new OpenTracingTracer();
        // By default it uses a Noop Tracer, but you can override it with a specific OpenTracing implementation.
//        ottracer.setTracer(...);
        // And then initialize the context
        ottracer.init(context);

        context.addRoutes(new RouteBuilder() {              // use RouteBuilder to create a new camel route
            @Override
            public void configure() throws Exception {
                from("file://opentelemetry/in?noop=true")             // research : why is the root-path used, not the sub-pom-path as expected?
                        .log("file found otel")
                        .to("ottracer")
                        .to("file://opentelemetry/out/");
            }
        });

        context.start();                                    // start camel context
        main.getCamelContexts().add(context);               // attach camel context
        main.run();                                         // start camel main

    }
}