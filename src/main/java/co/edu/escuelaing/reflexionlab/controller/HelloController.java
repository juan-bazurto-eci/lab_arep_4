package co.edu.escuelaing.reflexionlab.controller;

public class HelloController {

    @ResquestMapping("/")
    public static String index() {
        return "Greetings from Spring Boot!";
    }
}
