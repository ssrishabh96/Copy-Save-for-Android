package dynamic.movies.rishabh.sliderwelcome;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Fade());
        }
        setContentView(R.layout.activity_settings);
    }


    public static class MY_PREFERENCES extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            Preference minMagnitude;

            minMagnitude=findPreference(getString(R.string.username_key));
            bindPreferenceSummaryToValue(minMagnitude);


        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
//
            //   if (preference instanceof ListPreference) {
            //       ListPreference listPreference = (ListPreference) preference;
            //       int prefIndex = listPreference.findIndexOfValue(stringValue);
            //       if (prefIndex >= 0) {
            //           CharSequence[] labels = listPreference.getEntries();
            //           preference.setSummary(labels[prefIndex]);
            //       }
            //   } else {
            preference.setSummary(stringValue);
            //   }
//
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {

            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);

        }


    }

}
