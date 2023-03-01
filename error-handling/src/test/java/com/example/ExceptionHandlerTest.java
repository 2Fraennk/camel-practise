package com.example;

        import org.apache.camel.builder.NotifyBuilder;
        import org.apache.camel.component.mock.MockEndpoint;
        import org.apache.camel.test.junit4.CamelTestSupport;
        import org.junit.Test;

        import java.io.File;

public class ExceptionHandlerTest extends CamelTestSupport {
    @Test
    public void testContextIsStarted() throws Exception {
        assertTrue("Camel is not running",context.getStatus().isStarted());

    }
    @Test
    public void testContextIsStopped() throws Exception {
        context.stop();
        assertTrue("Camel is running",context.getStatus().isStopped());

    }
    @Test
    public void testIt() throws Exception {
        //MockEndpoint mock = getMockEndpoint("mock:output");
        //mock.expectedBodyReceived();

        //template.sendBodyAndHeader("file:out/text2.txt","test2","testhead","tested");

        //template.sendBody( "file://out/test3.txt", "file://in/test3.txt");  // not needed due to existing source file in /in

        //NotifyBuilder notify = new NotifyBuilder().


        assertFileExists("./out/test3.txt");       // different path?
//        assertStringContains("file:out/test3.txt","test3");
        //assertMockEndpointsSatisfied();
    }


}