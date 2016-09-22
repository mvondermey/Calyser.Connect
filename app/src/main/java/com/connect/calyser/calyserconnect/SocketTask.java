package com.connect.calyser.calyserconnect;

import android.content.Context;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DBs.COMMANDS_DBHandler;

/**
 */
 // Created by martin on 18.03.2016.
public class SocketTask implements Runnable {
    //
    private final SocketChannel clientSocket;
    private final Context mContext;
    private COMMANDS_DBHandler mCOMMAND_DbHandler;
    //
    final static ExecutorService ReadProcessingPool = Executors.newFixedThreadPool(1);
    final static ExecutorService SendProcessingPool = Executors.newFixedThreadPool(10);
    //
    public SocketTask(SocketChannel oclientSocket, Context oContext) {
        this.clientSocket = oclientSocket;
        this.mContext = oContext;
        mCOMMAND_DbHandler = new COMMANDS_DBHandler(oContext);
    }
    //
    @Override
    public void run() {
        //
        System.out.println("Calyser.SocketTask.Got connected !");
        //
        // Say Hi
        //
        String newData = "Calyser.I-am-CSync-Android";
        System.out.println("Message send");
        mCOMMAND_DbHandler.WriteOneMessageToSend(newData);
        //
        System.out.println("Message sent");
        //
        MessageJSON myJson = new MessageJSON(mContext,"Here is Android");
        new SendMessage(this.clientSocket,this.mContext,myJson.GetJSON()).run();
        mCOMMAND_DbHandler.WriteOneMessageToSend(myJson.GetJSON());
        //
        int count = 0;
        //
        // Read DB for data to send
        //
        while (true) {
            //
            count++;
            //
            if (count < 10 ) {
                String MessageToSend = mCOMMAND_DbHandler.ReadOneMessageToSend();
                if (MessageToSend != "" )
                    SendProcessingPool.submit(new SendMessage(this.clientSocket, this.mContext, MessageToSend));
            }
            //ReadProcessingPool.submit(new ReadMessage(this.clientSocket,this.mContext));
            //
        }
        //
    }
}
