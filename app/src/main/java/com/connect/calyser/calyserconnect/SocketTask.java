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

import credentials.COMMANDS_DBHandler;
import credentials.DATADBHandler;
import credentials.DBHandler;

/**
 * Created by martin on 18.03.2016.
 */
public class SocketTask implements Runnable {
    //
    private final SocketChannel clientSocket;
    private final Context mContext;
    private COMMANDS_DBHandler mCOMMAND_DbHandler;
    //
    public SocketTask(SocketChannel oclientSocket, Context oContext) {
        this.clientSocket = oclientSocket;
        this.mContext = oContext;
        mCOMMAND_DbHandler = new COMMANDS_DBHandler(oContext);
    }
    //
    private void ReadMessage(){
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
                //System.out.println("Calyser.ClientTask.Data received");
                bufread.flip();
                int limit = bufread.limit();
                System.out.println(" Limit "+limit);
                //
                Charset charset = Charset.forName("ISO-8859-1");
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer charBuffer = decoder.decode(bufread);
                //
                System.out.print(" -*- ");
                System.out.print(charBuffer.toString());
                //
                MessageReceived += charBuffer.toString();
                //
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        System.out.print("Message Received = "+MessageReceived);
        //
        // Store Message in DB
        //
        mCOMMAND_DbHandler.StoreReceived(MessageReceived);
        //
        (new MessageParser()).ParseMessage(MessageReceived);
        //
    }
    //
    private void SendMessage(String message){
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
    }
    //
    @Override
    public void run() {
        //
        System.out.println("Calyser.SocketTask.Got connected !");
        //
        String newData = "Calyser.I-am-CSync-Android\n";
        System.out.println("Message send");
        SendMessage(newData);
        System.out.println("Message sent");
        //
        ReadMessage();
        //
        MessageJSON myJson = new MessageJSON(mContext,"Here is Android");
        SendMessage(myJson.GetJSON());
        //
        while (true) {
            SendMessage(myJson.GetJSON()+"\n");
            ReadMessage();
        }

        //


    }
}
