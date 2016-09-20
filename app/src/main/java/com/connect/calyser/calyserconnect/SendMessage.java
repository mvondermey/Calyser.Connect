package com.connect.calyser.calyserconnect;

import android.content.Context;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import credentials.COMMANDS_DBHandler;

/**
 * Created by martinvondermey on 16.09.2016.
 */
class SendMessage implements Runnable {
    //
    private final SocketChannel clientSocket;
    private final Context mContext;
    private String message;
    private COMMANDS_DBHandler mCOMMAND_DbHandler;
    //
    public SendMessage (SocketChannel oclientSocket, Context oContext, String oMessage) {
        this.clientSocket = oclientSocket;
        this.mContext = oContext;
        this.message = oMessage;
        mCOMMAND_DbHandler = new COMMANDS_DBHandler(oContext);
    }
    @Override
    public void run() {
        //

        //
        System.out.println("********* Calyser.Sending "+message);
        //
        ByteBuffer bufwrite = ByteBuffer.allocate(1024);
        bufwrite.clear();
        bufwrite.put(message.getBytes());
        bufwrite.flip();
        //
        try {
            //System.out.println("Calyser.SocketTask.Sending data");
            while (clientSocket.write(bufwrite)>0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        // Store Message in DB
        //
        //mCOMMAND_DbHandler.StoreSent(message);
        //
    }
}
