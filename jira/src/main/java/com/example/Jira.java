package com.example;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointConfiguration;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jira.JIRAComponent;
import org.apache.camel.component.jira.JIRAEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;
import org.w3c.dom.Comment;

public class Jira {
    public static void main(String[] args) throws Exception {
        String uri = "jira.t-systems-mms.com?type=NEWCOMMENT&jql=project=MMSAPOLL";
//        String comment = "jira:NEWCOMMENT";


        Main main = new Main();                             // create camel main object < camel2 specific?
        CamelContext context = new DefaultCamelContext();   // init camel base context

        context.addRoutes(new RouteBuilder() {              // use RouteBuilder to create a new camel route
            @Override
            public void configure() throws Exception {

//                Jira Component
//                JIRAComponent jira = new JIRAComponent();
                
//                Jira Configuration
                EndpointConfiguration configuration = jira.createConfiguration("+uri+");
                
//                Endpoint
//              Object endpoint = new JIRAEndpoint(uri,jira);

//                jira.createConfiguration(uri);
//
//                getContext().addComponent("jira", jira);

//                from("jira://"+uri)             // research : why is the root-path used, not the sub-pom-path as expected?
//                        .log("file found ${body}")
////                        .to("file://file-mover/out/")
////                        .to("activemq:queue:testq1")
////                        .to("timer:t1")
//                        .to("file://jira/out/")
//                        ;
                from("jira://newComments?jql=RAW(project={{example.jira.project-key}} AND resolution = Unresolved)&delay=4000")
                        .process(exchange -> {
                            Comment comment = (Comment) exchange.getIn().getBody();
                            LOG.info("new jira comment id: {} - by: {}: {}", comment.getId(), comment.getAuthor().getDisplayName(),
                                    comment.getBody());
                        })
                        .log("")
                        .to("mock:result")
                ;

            }
        });

        context.start();                                    // start camel context
        main.getCamelContexts().add(context);               // attach camel context
        main.run();                                         // start camel main

    }
}