package com.connect.calyser.calyserconnect;

import android.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by martin on 21.01.2016.
 */
public class SIngletonCalyser {

    private static ArrayList mSListPorts = new ArrayList<Integer>();

    private static ArrayList<Pair<String,Integer>> mSListConnections = new ArrayList<Pair<String,Integer>>();

    private static SIngletonCalyser ourInstance = new SIngletonCalyser();

    public static SIngletonCalyser getInstance() {
        return ourInstance;
    }

    private SIngletonCalyser() {
        //empty
    }

    final static ExecutorService SocketProcessingPool = Executors.newFixedThreadPool(10);

    public static ArrayList getPorts() {
        return mSListPorts;
    }

    public static ArrayList getConnections() {
        return mSListConnections;
    }

    public static void addPort(int port) {
        mSListPorts.add(port);
    }

    public static void addConnections(String ip, int port){
        //
        Pair<String, Integer> item = new Pair<String, Integer>(ip, port);
        //
        mSListConnections.add(item);
        //
    }

}
