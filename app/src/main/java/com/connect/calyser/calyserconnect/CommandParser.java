package com.connect.calyser.calyserconnect;

import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by martinvondermey on 21.09.2016.
 */

public class CommandParser {
    //
    private JSONObject jsonObject;
    //
    public String ParseCommand(String Command){
        //
        if (Command.equals("GetTopDirectoryListing")) {
            //
            File file = new File(Environment.getExternalStorageDirectory().getPath());
            //
            System.out.println("Get Top Directory Listing");
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
                //
            }
            //
            jsonObject = new JSONObject();
            //
            try {
             jsonObject.put("Files",jsonArray);
            } catch (JSONException e)
            {
             e.printStackTrace();
            }
            //
            System.out.println("Files to send "+jsonObject.toString());
            //
        }

        //
        return jsonObject.toString();
    }

}
