package io.gentrack.platformnotificationdemo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.taplytics.sdk.TLGcmBroadcastReceiver;

/**
 * Created by danz on 21/02/2018.
 */

public class MyTLBroadcastReceiver extends TLGcmBroadcastReceiver {
    @Override
    public void pushOpened(Context context, Intent intent) {

        //A user clicked on the notification!
        //Open billready with payload
        Intent billReadyIntent = new Intent(context, BillReadyActivity.class);
        billReadyIntent.putExtras(intent);
        context.startActivity(billReadyIntent);
    }

    @Override
    public void pushDismissed(Context context, Intent intent) {
        //The push has been dismissed :(
    }

    @Override
    public void pushReceived(Context context, Intent intent) {
        //The push was received, but not opened yet!
        /*
        If you add the custom data of tl_silent = true to the push notification,
        there will be no push notification presented to the user. However, this will
        still be triggered, meaning you can use this to remotely trigger something
        within the application!
         */
    }
}
