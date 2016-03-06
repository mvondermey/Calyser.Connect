package com.connect.calyser.calyserconnect;

import android.content.Context;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
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
        final ArrayList mListConnections =  SIngletonCalyser.getConnections();
        //
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);
        System.out.println("Client.StartClient");
        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //
        Runnable clientTask = new Runnable() {
            @Override
            public void run() {
                //
                CalyserFileWriter cFileWriter = null;
                cFileWriter = new CalyserFileWriter().GetFileWriter(mContext);
                cFileWriter.writeToFile("Client.StartClient\n");
                System.out.println("Client.StartClient.run");
                //
                while (true) {
                    //
                    //System.out.println("Connecting to clients");
                    Iterator itr = mListConnections.iterator();
                    Socket clientSocket;
                    while (itr.hasNext()) {
                        Pair<String, Integer> connection = (Pair<String, Integer>) itr.next();
                        //
                        String ip = connection.first;
                        int port = connection.second;
                        //
                        System.out.println("Calyser.Connect Connecting to IP   " + ip);
                        System.out.println("Calyser.Connect Connecting to port " + port);
                        //
                        cFileWriter.writeToFile("Calyser.Connect Connecting to IP   " + ip);
                        cFileWriter.writeToFile("Calyser.Connect Connecting to port " + port);
                        //
                        try {
                            clientSocket = new Socket(ip, port);
                            clientSocket.connect(new InetSocketAddress(ip, port), 1000);
                            clientProcessingPool.submit(new ClientTask(clientSocket));
                        } catch (IOException e) {
                            System.out.println("Unable to process client request");
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        //
        //while(true) {
            //
            Thread clientThread = new Thread(clientTask);
            clientThread.start();
            //
        //}
        //
    }
//
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
