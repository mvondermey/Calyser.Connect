package com.connect.calyser.calyserconnect;

import android.content.Context;
import android.telephony.TelephonyManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by martin on 27.03.2016.
 */
public class MessageJSON {
    //
    private String  mMessage;
    private Context mContext = null;
    //
    public MessageJSON(Context oContext, String oMessage){
        mMessage = oMessage;
        mContext = oContext;
    };
    //
    public String GetJSON ( ) {
        //
        JSONObject jsonObj = new JSONObject();
        //
        String ts = Context.TELEPHONY_SERVICE;
        TelephonyManager mTelephonyMgr = (TelephonyManager) mContext.getSystemService(ts);
        String imsi = mTelephonyMgr.getSubscriberId();
        String imei = mTelephonyMgr.getDeviceId();
        String AndroidID = android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        //
        try {
            jsonObj.put("Message",mMessage);
            jsonObj.put("IMSI",imsi);
            jsonObj.put("IMEI",imei);
            jsonObj.put("ANDROID_ID",AndroidID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        return jsonObj.toString();
        //
    }
    //
    private String FileListing(){
        //
        JSONObject jsonFileListing = new JSONObject();
        //
        return jsonFileListing.toString();
        //
    }
}
