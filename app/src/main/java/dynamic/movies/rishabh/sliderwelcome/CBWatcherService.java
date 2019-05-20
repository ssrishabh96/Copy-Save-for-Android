package dynamic.movies.rishabh.sliderwelcome;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import dynamic.movies.rishabh.sliderwelcome.notification.NotificationIntentService;
import io.realm.Realm;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

/**
 * Created by RISHABH on 5/13/2016.
 */
public class CBWatcherService extends Service {
    private static final String TAG = CBWatcherService.class.getSimpleName();
    private Toast toast;
    private Realm realm;

    public static final String MyPREFERENCES = "MyPrefs";
    private static int counter = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public ClipboardManager.OnPrimaryClipChangedListener listener = new ClipboardManager.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: fired");
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);
        pref = getBaseContext().getSharedPreferences(MyPREFERENCES, 0);
        counter = pref.getInt("counter", 0);
        showNotification();
    }

    private void showNotification() {
        Log.i(TAG, "showNotification: fired ");

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_movie)
                        .setContentTitle("Application is running");

        mBuilder.setContentIntent(resultPendingIntent)
                .setAutoCancel(true);



        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());


    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onDestroy: fired ");

        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
            }
        });
        pref = getBaseContext().getSharedPreferences(MyPREFERENCES, 0);
        editor = pref.edit();
        editor.putInt("counter", counter);
        editor.commit();
        if (toast != null) toast = null;
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: fired ");
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
            }
        });
        pref = getBaseContext().getSharedPreferences(MyPREFERENCES, 0);
        editor = pref.edit();
        editor.putInt("counter", counter);
        editor.commit();

        if (toast != null) toast = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: fired");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: fired");
        return null;
    }

    private void performClipboardCheck() {

        realm = Realm.getDefaultInstance();
        pref = getBaseContext().getSharedPreferences(MyPREFERENCES, 0);
        editor = pref.edit();
        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if (clipboardManager.hasPrimaryClip()) {
            if (clipboardManager.getPrimaryClip().getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) ||
                    clipboardManager.getPrimaryClip().getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)) {

                final ClipData clipData = clipboardManager.getPrimaryClip();

                if (clipData.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            MyPojos temp = new MyPojos();
                            Log.i(TAG, "execute: clipdata:  " + clipData);
                            temp.setData(clipData.getItemAt(0).getText().toString());
                            temp.setId(temp.getData().hashCode() + ""); //UUID.randomUUID().toString());
                            realm.copyToRealmOrUpdate(temp);
                            counter++;
                            editor.putInt("counter", counter);
                            editor.commit();

                            if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
                                toast = Toast.makeText(getBaseContext(), "Got it!", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                }
            } else {
                Log.i(TAG, "performClipboardCheck: fired can't work with image or incompatible data");
            }
        } // performClipboadCheck() ends here
    }
}