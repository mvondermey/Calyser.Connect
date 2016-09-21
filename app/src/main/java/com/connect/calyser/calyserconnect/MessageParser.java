package com.connect.calyser.calyserconnect;

import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by martinvondermey on 13.09.2016.
 */
public class MessageParser {
    //
    //
    public  void ParseMessage(String MessageToParse){
        try {
            //
            MessageToParse = MessageToParse.trim();
            MessageToParse = MessageToParse.replaceAll("<BOF>","");
            MessageToParse = MessageToParse.replaceAll("<EOF>","");
            MessageToParse = MessageToParse.replaceAll(" ","");
            //
            System.out.println("Message to parse "+MessageToParse);
            //
            JSONObject reader = new JSONObject(MessageToParse);
            String Command = reader.getString("Command");
            System.out.println(" Command = "+Command);
            //
            System.out.println((new CommandParser()).ParseCommand(Command));
            //
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
