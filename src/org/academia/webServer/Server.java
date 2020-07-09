package org.academia.webServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class Server {

    int portNumber = 8080;
    String directory = "";
    String[] request;
    byte[] dest;
    byte[] notFileFound;
    DataOutputStream out;
    BufferedReader in;
    //byte[] fileNotFound;

    public static void main(String[] args) {

        Server server = new Server();

        try{

            ServerSocket serverSocket = new ServerSocket(server.portNumber);
            System.out.println("Waiting for new client to establish connection...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established to: " + "address = " + clientSocket.getLocalSocketAddress() + " localport = " + clientSocket.getPort());

            server.out = new DataOutputStream(clientSocket.getOutputStream());
            server.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            server.request = server.in.readLine().split(" ");
            server.directory = "www" + server.request[1];

            server.fileCheck(server.directory);


            server.in.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException exception) {

            exception.printStackTrace();

        }

    }

    public void fileCheck (String path){

        try {
            System.out.println(path);
            if(path.equals("www/index.html") || path.equals("www/")){

                dest = Files.readAllBytes(new File("www/index.html").toPath());
                String pathFile = new String(dest);

                out.writeBytes("HTTP/1.0 200 Document Follows\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n" +
                        "Content-Length: " + dest.length + "\r\n\r\n");
                out.writeBytes(pathFile);
                out.flush();
                return;

            } else if (path.equals("www/transferir.jpg")){

                FileInputStream input = new FileInputStream("www/transferir.jpg");

                byte[] buffer = new byte[2048];

                String extension = path.substring(15);

                File resource = new File(path);

                out.writeBytes("HTTP/1.0 200 Document Follows\r\n" +
                        "Content-Type: image/" + extension +"\r\n" +
                        "Content-Length: " + resource.length() + "\r\n\r\n");

                while ((input.read(buffer)) != -1) {

                    out.write(buffer);

                }

                return;

            }

            System.out.println("Enter");
            notFileFound = Files.readAllBytes(new File("www/notFound.html").toPath());
            String notFoundError = new String(notFileFound);

            out.writeBytes("HTTP/1.0 404 Not Found\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n" +
                    "Content-Length: " + notFileFound.length + "\r\n\r\n");
            out.writeBytes(notFoundError);
            out.flush();



        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
