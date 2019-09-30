package com.example.rlta.tracar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Tiago on 28/10/2015.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {
    private String TAG = SmsBroadcastReceiver.class.getSimpleName();

    public static final String SMS_CONTENT = "sms_content";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the data (SMS data) bound to intent
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String str = "";

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            // For every SMS message received
            for (int i=0; i < msgs.length; i++) {
                // Convert Object array
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // Sender's phone number
                if(i==0)
                    str += msgs[i].getOriginatingAddress() + " : ";
                // Fetch the text message
                str += msgs[i].getMessageBody().toString();
                // Newline :-)
                //str += "\n";
            }

            Intent fireActivityIntent = new Intent(context, MainActivity.class);
            fireActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            fireActivityIntent.putExtra(SMS_CONTENT, str);
            context.startActivity(fireActivityIntent);

            // Display the entire SMS Message
            Log.d(TAG, str);
        }
    }
}
