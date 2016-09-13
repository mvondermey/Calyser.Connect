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

    public  void ParseMessage(String MessageToParse){
        try {
            JSONObject reader = new JSONObject(MessageToParse);
            String Command = reader.getString("Command");
            System.out.println(" Command = "+Command);
            //
            if (Command.equals("GetDirectoryListing")) {
                //
                File file = new File(Environment.getExternalStorageDirectory().getPath());
                //
                System.out.println("Get Directory Listing");
                //
                File[] files = file.listFiles();
                //
                System.out.println("Number of files "+files.length);
                //
                JSONArray jsonArray = new JSONArray();
                //
                for(File myFile : files) {
                    //
                    jsonArray.put(myFile.getName());
                    //System.out.println(myFile.getName());
                    //
                }
                //
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Files",jsonArray);
                //
                System.out.println(jsonObject.toString());
                //
            }

            //
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
