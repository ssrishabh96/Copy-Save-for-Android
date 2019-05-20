package dynamic.movies.rishabh.sliderwelcome;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by RISHABH on 10/9/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("copy.save")
                .schemaVersion(0)
                .build();

        Realm.setDefaultConfiguration(realmConfig);


    }
}
