package com.example.iev5;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Mellan on 2016/6/26.
 */
public class Executor {
    public void makeStopACSMS(String terminalPhoneNumber) {
        if(terminalPhoneNumber != null && terminalPhoneNumber.length() != 0) {
            this.makeTermianlReportSMS(terminalPhoneNumber, "bwr1050,stpac");
            return;
        }
    }
    public void makeAutoAirConditionTemperatureSMS(String terminalPhoneNumber,  int
            temperature) {
            if(temperature >= 0 && temperature <= 30) {
                this.makeTermianlReportSMS(terminalPhoneNumber, String.format("bwr1050,ac,%d",
                        Integer.valueOf(temperature)));
                return;
            }
    }
    public void makeChargingSMS(String terminalPhoneNumber) {
        if(terminalPhoneNumber != null && terminalPhoneNumber.length() != 0) {
            this.makeTermianlReportSMS( terminalPhoneNumber, "bwr1050,chg");
            return;
        }
    }
    public void makeStopChargeSMS(String terminalPhoneNumber) {
        if(terminalPhoneNumber != null && terminalPhoneNumber.length() != 0) {
            this.makeTermianlReportSMS(terminalPhoneNumber, "bwr1050,stpchg");
            return;
        }
    }
    public void makeTermianlReportSMS( String phoneNum, String smsContent) {
        Log.i("SMS","TEST");
        SmsManager v1 = SmsManager.getDefault();
        String v10 = this.getMaskSMS(smsContent);
        Log.i("SMS",v10);
        ArrayList v4 = v1.divideMessage(v10);
        v1.sendMultipartTextMessage(phoneNum, null, v4, null, null);
    }
    public static String convertMask(String unMaskStr, int random) {
        int v2;
        int v5;
        StringBuffer v3 = new StringBuffer();
        int v1;
        for(v1 = 0; v1 < unMaskStr.length(); ++v1) {
            char v0 = unMaskStr.charAt(v1);
            if((Character.isDigit(v0)) || v0 == 44) {
                v5 = random;
                v2 = new Integer(v0).intValue();
            }
            else {
                v5 = 16;
                v2 = v0 & 255;
            }

            v3.append(Integer.toHexString(v2 - v5));
        }

        return v3.toString();
    }
    public static String getMaskSMS(String orignalSMSContent) {
        int v1 = ((int)(System.currentTimeMillis() % 10));
        return new StringBuffer(StringUtils.getASCIIString("A:")).append(StringUtils.getASCIIString(
                v1)).append(StringUtils.getASCIIString(":")).append(StringUtils.getASCIIString(
                convertMask(StringUtils.getNoSplitCurrentTime(), v1))).append(StringUtils.getASCIIString(
                ":")).append(StringUtils.getASCIIString(convertMask(orignalSMSContent.toLowerCase(
                Locale.US), v1))).toString();
    }
}
