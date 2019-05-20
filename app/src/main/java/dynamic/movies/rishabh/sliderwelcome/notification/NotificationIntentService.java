package dynamic.movies.rishabh.sliderwelcome.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import dynamic.movies.rishabh.sliderwelcome.CBWatcherService;
import dynamic.movies.rishabh.sliderwelcome.MainActivity;

/**
 * Created by RISHABH on 10/10/2017.
 */
public class NotificationIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public NotificationIntentService() {
        super("notificationIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        switch (intent.getAction()) {
            case "pause":
                Handler leftHandler = new Handler(Looper.getMainLooper());
                leftHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stopService(new Intent(getBaseContext(), CBWatcherService.class));

                    }
                });
                break;
            case "stop":
                Handler rightHandler = new Handler(Looper.getMainLooper());
                rightHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        stopService(new Intent(getBaseContext(), CBWatcherService.class));
                        // Store some notification id as `nId`
                        NotificationManager mNotificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(001);
                    }
                });
                break;
        }
    }
}