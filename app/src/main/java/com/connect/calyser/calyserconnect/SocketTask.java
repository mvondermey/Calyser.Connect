package com.connect.calyser.calyserconnect;

import android.content.Context;
import android.os.Debug;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

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
        try {
            //System.out.println("Calyser.ClientTask.Reading data");
            while (clientSocket.read(bufread) > 0) {
                //System.out.println("Calyser.ClientTask.Data received");
                bufread.flip();
                int limit = bufread.limit();
                while (limit > 0) {
                    System.out.print((char) bufread.get());
                    limit--;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            System.out.println("Calyser.SocketTask.Sending data");
            while (clientSocket.write(bufwrite)>0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        System.out.println("Calyser.SocketTask.Data sent");
    }
    //
    @Override
    public void run() {
        //
        System.out.println("Calyser.SocketTask.Got connected !");
        //
        String newData = "I-am-CSync-Android\n";
        //
        System.out.println("Calyser.SocketTask.Sent 1 "+newData);
        SendMessage(newData);
        //
        MessageJSON myJson = new MessageJSON(mContext);
        myJson.Message = "Here the JSON";
        System.out.println("Calyser.SocketTask.Sent 2"+myJson.GetJSON());
        SendMessage(myJson.GetJSON());
        //
        while (true) ReadMessage();
        //


    }
}
