package org.academia.webServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client implements Runnable{

    private String host = "localhost";
    private BufferedWriter outputBuffered;
    private Socket socket;



    @Override
    public void run() {

            try {

                socket = new Socket(host, 8080);
                outputBuffered = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.println(Thread.currentThread().getName());

            } catch (IOException e) {

                e.printStackTrace();
            }
        //}

    }


}
