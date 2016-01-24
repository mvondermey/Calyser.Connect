package com.connect.calyser.calyserconnect;

import java.util.ArrayList;

/**
 * Created by martin on 21.01.2016.
 */
public class SIngletonCalyser {

    private static ArrayList mSListPorts = new ArrayList();;

    private static SIngletonCalyser ourInstance = new SIngletonCalyser();

    public static SIngletonCalyser getInstance() {
        return ourInstance;
    }

    private SIngletonCalyser() {
        //empty
    }

    public static ArrayList getPorts() {
        return mSListPorts;
    }

    public static void addPort(int port) {
        mSListPorts.add(port);
    }

}
