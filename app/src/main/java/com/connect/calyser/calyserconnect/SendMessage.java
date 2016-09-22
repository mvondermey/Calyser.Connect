package com.connect.calyser.calyserconnect;

import android.content.Context;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import DBs.COMMANDS_DBHandler;

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
        String OrigMessage = message;
        //
        message = "<BOF>"+message+"<EOF>";
        //
        System.out.println("********* Calyser.Sending "+message);
        //
        ByteBuffer bufwrite = ByteBuffer.allocate(message.length());
        bufwrite.clear();
        bufwrite.put(message.getBytes());
        bufwrite.flip();
        //
        try {
            //
            while (clientSocket.write(bufwrite)>0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //
        System.out.println("Sent message");
        mCOMMAND_DbHandler.SetMessageToSent(OrigMessage);
        //
    }
}
