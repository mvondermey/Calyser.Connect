package com.connect.calyser.calyserconnect;

import android.app.AlertDialog;
import android.os.StrictMode;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by martin on 18.01.2016.
 */
public class Server extends AppCompatActivity {
    //
    public void startServer(final Context mContext) {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);
        //
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8000);
                    //
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        System.out.println("Before accept...");
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("After accept...");
                        //
                        clientProcessingPool.submit(new ClientTask(clientSocket));
                        //
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        //
        Thread serverThread = new Thread(serverTask);
        serverThread.start();

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

            System.out.println("Got a client !");

            // Do whatever required to process the client's request
            // Create character streams for the socket.
            //
            try {
                System.out.println("Reading buffer !");
                in = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.print(in.readLine());
            } catch (IOException e){
                e.printStackTrace();
            }
            //
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //
        }
    }

}
