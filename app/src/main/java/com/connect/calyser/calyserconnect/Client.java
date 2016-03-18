package com.connect.calyser.calyserconnect;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
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
        final WebSocketClient[] mWebSocketClient = {null};
        //

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
                cFileWriter.writeToFile("Calyser.Client.StartClient\n");
                System.out.println("Calyser.Client.StartClient.run");
                //
                //while (true) {
                    //
                    //System.out.println("Connecting to clients");
                    //
                    Iterator itr = mListConnections.iterator();
                    SocketChannel clientSocket = null;
                    //
                    while (itr.hasNext()) {
                        Pair<String, Integer> connection = (Pair<String, Integer>) itr.next();
                        //
                        String ip = connection.first;
                        int port = connection.second;
                        //
                        System.out.println("Calyser.Client.Connect Connecting to IP   " + ip);
                        System.out.println("Calyser.Client.Connect Connecting to port " + port);
                        //
                        cFileWriter.writeToFile("Calyser.Client.Connect Connecting to IP   " + ip);
                        cFileWriter.writeToFile("Calyser.Client.Connect Connecting to port " + port);
                        //
                        URI uri;
                        try {
                            //
                            uri = new URI("ws://echo.websocket.org:8080");
                            System.out.println("Calyser.Client.Connected to Websocket Server");
                            //
                            mWebSocketClient[0] = new WebSocketClient(uri) {
                                @Override
                                public void onOpen(ServerHandshake serverHandshake) {
                                    System.out.println("Calyser.Client.Websocket Opened");
                                    mWebSocketClient[0].send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                                }

                                @Override
                                public void onMessage(String s) {
                                    final String message = s;
                                    SIngletonCalyser.SocketProcessingPool.submit(new Runnable() {
                                        @Override
                                        public void run() {
                                            //
                                            System.out.println("Calyser.Client.Got message "+message);
                                            //
                                        }
                                    });
                                }

                                @Override
                                public void onClose(int i, String s, boolean b) {
                                    System.out.println("Calyser.Client.Websocket Closed " + s);
                                }

                                @Override
                                public void onError(Exception e) {
                                    System.out.println("Calyser.Client.Websocket Error " + e.getMessage());
                                }
                            };
                            System.out.println("Calyser.Client.Websocket Connect");
                            mWebSocketClient[0].connect();
                            //
                        }catch(URISyntaxException e){
                            //
                            System.out.println("Calyser.Client.Unable to connect to Websocket Server");
                            e.printStackTrace();
                            //
                        }
                        //
                        try {
                            //
                            System.out.println("Calyser.Client.Create Socket");
                            //clientSocket = new Socket(ip, port);
                            clientSocket = SocketChannel.open();
                            clientSocket.configureBlocking(false);
                           //
                            clientSocket.connect(new InetSocketAddress(ip, port));
                            while(! clientSocket.finishConnect() ){
                                //wait, or do something else...
                            }
                            //
                            System.out.println("Calyser.Client.Submit pool");
                            //
                            SIngletonCalyser.SocketProcessingPool.submit(new SocketTask(clientSocket));
                            //
                        } catch (IOException e ) {
                            System.out.println("Calyser.Client.Unable to open socket "+ip+" "+port);
                            e.printStackTrace();
                        }
                    }

                }
            //}
        };
        //
        Thread clientThread = new Thread(clientTask);
        clientThread.start();
            //
    }
//


    //
}
