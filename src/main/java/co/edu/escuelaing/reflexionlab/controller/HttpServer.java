package co.edu.escuelaing.reflexionlab.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.Map;

/**
 * Clase principal servidor HTTP buscador de películas.
 */
public class HttpServer {

    /**
     * Método principal para iniciar el servidor HTTP.
     *
     * @throws IOException Si ocurre un error al configurar o aceptar conexiones.
     */
    public static void up(Map<String, Method> methods) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        while (!serverSocket.isClosed()) {
            try {
                System.out.println("Operando ARQUITECTURAS DE SERVIDORES DE APLICACIONES, META PROTOCOLOS DE OBJETOS, PATRÓN IOC, REFLEXIÓN...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firstLine = true;
            String uriString = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    uriString = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
            }
            System.out.println("URI: " + uriString);
            String responseBody = "";
            for (String uri : methods.keySet()) {
                Method m = methods.get(uri);
                System.out.println("uri: " + uri + ", Metodo: " + m);
                try {
                    responseBody = (String) m.invoke(null);
                    System.out.println(responseBody);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

            outputLine = "HTTP/1.1 200 OK \r\n"
                    + "Content-Type: text/html \r\n"
                    + "\r\n"
                    + "\n"
                    + responseBody;
            out.println(outputLine);
            out.close();
            in.close();
        }
        clientSocket.close();
        serverSocket.close();
    }

    /**
     * Retorna una página HTML.
     *
     * @return Respuesta HTTP con la página de inicio.
     */
    public static String getIndexResponse() {
        String response = "HTTP/1.1 200 OK"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>ARQUITECTURAS DE SERVIDORES DE APLICACIONES, META PROTOCOLOS DE OBJETOS, PATRÓN IOC, REFLEXIÓN</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>ARQUITECTURAS DE SERVIDORES DE APLICACIONES, META PROTOCOLOS DE OBJETOS, PATRÓN IOC, REFLEXIÓN</h1>\n" +
                "    </body>\n" +
                "</html>";
        return response;
    }
}

