package com.nemisolv.ex10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySmsReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        processReceive(context, intent);
    }

    private void processReceive(Context context, Intent intent) {
        Bundle extras =     intent.getExtras();
        String message = "";
        String body = "";
        String address =   "";
        if(extras != null) {
            Object[] pdus = (Object[]) extras.get("pdus");
            for(int i = 0;i < pdus.length; i++)  {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);
                body = sms.getMessageBody();
                address = sms.getOriginatingAddress();
                message += "SMS from " + address + " : " + body + "\n";
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}