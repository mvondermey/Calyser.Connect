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

/**
 * Created by martin on 18.03.2016.
 */
public class SocketTask implements Runnable {
    //
    private final SocketChannel clientSocket;
    private final Context mContext;
    //
    public SocketTask(SocketChannel oclientSocket, Context oContext) {
        this.clientSocket = oclientSocket;
        this.mContext = oContext;
    }
    //
    private void ReadMessage(){
        //
        ByteBuffer bufread = ByteBuffer.allocate(1024);
        //
        bufread.clear();
        //
        String MessageTransmitted = "";
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
                MessageTransmitted += charBuffer.toString();
                //
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        System.out.print("Message = "+MessageTransmitted);
        //
        (new MessageParser()).ParseMessage(MessageTransmitted);
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
        String newData = "I-am-CSync-Android\n";
        SendMessage(newData);
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
