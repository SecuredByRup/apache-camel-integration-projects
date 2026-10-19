package com.example.camel;

import org.apache.camel.main.Main;

public class ErrorHandlingApplication {

    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.configure().addRoutesBuilder(new RetryRouteBuilder());
        main.configure().addRoutesBuilder(new DeadLetterQueueRouteBuilder());
        main.configure().addRoutesBuilder(new FallbackRouteBuilder());
        main.configure().addRoutesBuilder(new AdvancedErrorHandlingRouteBuilder());

        main.configure().setShutdownTimeout(30);

        System.out.println("Starting Camel Error Handling Application...");
        System.out.println("Press Ctrl+C to stop the application");

        main.run(args);
    }
}
