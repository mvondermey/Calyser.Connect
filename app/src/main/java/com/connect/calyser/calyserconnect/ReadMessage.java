package com.connect.calyser.calyserconnect;

import android.content.Context;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import credentials.COMMANDS_DBHandler;

/**
 * Created by martinvondermey on 16.09.2016.
 */
public class ReadMessage implements Runnable {
//
    private final SocketChannel clientSocket;
    private final Context mContext;
    private String message;
    private COMMANDS_DBHandler mCOMMAND_DbHandler;
    //
    public ReadMessage (SocketChannel oclientSocket, Context oContext) {
        this.clientSocket = oclientSocket;
        this.mContext = oContext;
        mCOMMAND_DbHandler = new COMMANDS_DBHandler(oContext);
    }
    //
    public void run(){
            //
            ByteBuffer bufread = ByteBuffer.allocate(1024);
            //
            bufread.clear();
            //
            String MessageReceived = "";
            //
            try {
                //System.out.println("Calyser.ClientTask.Reading data");
                while (clientSocket.read(bufread) > 0) {
                //
                    bufread.flip();
                    int limit = bufread.limit();
                    System.out.println(" Limit "+limit);
                    //
                    Charset charset = Charset.forName("ISO-8859-1");
                    CharsetDecoder decoder = charset.newDecoder();
                    CharBuffer charBuffer = decoder.decode(bufread);
                    //
                    MessageReceived += charBuffer.toString();
                    //
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //
            System.out.println("Message Received = "+MessageReceived);
            //
            // Store Message in DB
            //
            //mCOMMAND_DbHandler.StoreReceived(MessageReceived);
            //
            (new MessageParser()).ParseMessage(MessageReceived);
            //

            //
        }
}
