package com.connect.calyser.calyserconnect;

import android.content.Context;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by martin on 19.01.2016.
 */
public class Client {
    //
    public void startClient(final Context mContext) {
        //
        final ArrayList mListPorts =  SIngletonCalyser.getPorts();
        //
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);
        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //
        //
        Runnable clientTask = new Runnable() {
            @Override
            public void run() {

//
                System.out.println("Connecting to clients");
                Iterator itr = mListPorts.iterator();
                Socket clientSocket;
                while (itr.hasNext()) {
                    int port = (int) itr.next();
                    System.out.println("Connecting to port " + port);
                    try {
                        clientSocket = new Socket("10.0.2.2", port);
                        clientProcessingPool.submit(new ClientTask(clientSocket));
                    } catch (IOException e) {
                        System.out.println("Unable to process client request");
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread clientThread = new Thread(clientTask);
        clientThread.start();

    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {

            BufferedReader in;
            PrintWriter out;

            System.out.println("Got a Connection !");

            // Do whatever required to process the client's request
            // Create character streams for the socket.
            //
            //
            try {
                in = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.print(in.readLine());
                System.out.print("Sending message");
                out.print("I am here *************** !!!");
            } catch (IOException e){
                e.printStackTrace();
            }
            //
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //
}
