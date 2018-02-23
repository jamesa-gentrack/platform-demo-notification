package io.gentrack.platformnotificationdemo;

import android.app.Application;

import com.taplytics.sdk.Taplytics;
import com.taplytics.sdk.TaplyticsPushTokenListener;

/**
 * Created by danz on 19/02/2018.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Taplytics.startTaplytics(this, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        Taplytics.setTaplyticsPushTokenListener(new TaplyticsPushTokenListener() {
            @Override
            public void pushTokenReceived(String token) {
                System.out.println("Received " + token);
            }
        });
    }
}
