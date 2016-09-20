package com.connect.calyser.calyserconnect;

import android.content.Context;
import android.os.Debug;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import credentials.COMMANDS_DBHandler;
import credentials.DATADBHandler;
import credentials.DBHandler;

/**
 */
 // Created by martin on 18.03.2016.
public class SocketTask implements Runnable {
    //
    private final SocketChannel clientSocket;
    private final Context mContext;
    private COMMANDS_DBHandler mCOMMAND_DbHandler;
    //
    final static ExecutorService ReadProcessingPool = Executors.newFixedThreadPool(10);
    final static ExecutorService SendProcessingPool = Executors.newFixedThreadPool(10);
    //
    public SocketTask(SocketChannel oclientSocket, Context oContext) {
        this.clientSocket = oclientSocket;
        this.mContext = oContext;
        mCOMMAND_DbHandler = new COMMANDS_DBHandler(oContext);
    }
    //

    //

    //
    @Override
    public void run() {
        //
        System.out.println("Calyser.SocketTask.Got connected !");
        //
        // Say Hi
        //
        String newData = "Calyser.I-am-CSync-Android\n";
        System.out.println("Message send");
        new SendMessage(this.clientSocket,this.mContext,newData).run();
        //
        System.out.println("Message sent");
        //
        MessageJSON myJson = new MessageJSON(mContext,"Here is Android");
        new SendMessage(this.clientSocket,this.mContext,myJson.GetJSON()).run();
        //
        int count = 0;
        //
        while (count < 30) {
            count++;
            //System.out.println("********* Calyser.Sending "+myJson.GetJSON());
            SendProcessingPool.submit(new SendMessage(this.clientSocket,this.mContext,myJson.GetJSON()));
            ReadProcessingPool.submit(new ReadMessage(this.clientSocket,this.mContext));
        }
        //
    }
}
