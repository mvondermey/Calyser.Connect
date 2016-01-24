package com.connect.calyser.calyserconnect;

import java.net.InetAddress;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by martin on 21.01.2016.
 */
public class Discovery {
//
    private int PORT = 8001;
    private int DISCOVERY_PORT = 8002;
    //
    public void Print(Context mContext) {
        //try {
            InetAddress BroadcastAddress;
            BroadcastAddress = getBroadcastAddress(mContext);
            System.out.println("Connect.Hostname     = "+BroadcastAddress.getHostName());
            System.out.println("Connect.Host Address = " + BroadcastAddress.getHostAddress());
        //}catch () {
          //  System.out.print("Connect.Exception ");
        //}
        //
    }
    //
    public void SayHi(final Context mContext) {
        //
        Runnable discoveryTask = new Runnable() {
            //
            @Override
            public void run() {
                //
                DatagramSocket socket = null;
                //
                try {
                    socket = new DatagramSocket(PORT);
                    socket.setBroadcast(true);
                } catch (SocketException e) {
                    System.out.println("Socket Exception " + e.toString());
                }
                //
                JSONObject SayHi = new JSONObject();
                //
                try {
                    SayHi.put("Owner", "Calyser.Connect");
                    SayHi.put("Message", "Hi!");
                } catch (JSONException e) {
                    System.out.println("JSON Exception");
                    e.printStackTrace();
                }
            /*  */
                String data = SayHi.toString();
                DatagramPacket Spacket = new DatagramPacket(data.getBytes(), data.length(),
                        getBroadcastAddress(mContext), DISCOVERY_PORT);
                //
                while (true) {
                    //
                    System.out.println("Calyser.Connect Looping SayHi");
                    //
                    try {
                        socket.send(Spacket);
                    } catch (IOException e) {
                        System.out.println("IO Exception " + e.toString());
                    }
                    //
                    byte[] buf = new byte[1024];
                    DatagramPacket Rpacket = new DatagramPacket(buf, buf.length);
                    try {
                        socket.receive(Rpacket);
                    } catch (IOException e) {
                        System.out.println("IO Exception 2");
                        e.printStackTrace();
                    }
                    //
                }
            }
        };
        //
        Thread serverThread = new Thread(discoveryTask);
        serverThread.start();
        //
    }
    //
    public InetAddress getBroadcastAddress(Context mContext) {
        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow
        InetAddress Raddress = null;
        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        try {
            Raddress = InetAddress.getByAddress(quads);
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException");
            e.printStackTrace();
        }
    //
        return Raddress;
    }

}
