
package dynamic.movies.rishabh.sliderwelcome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    private PrefsManager prefsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }

        prefsManager = new PrefsManager(this);
        if (!prefsManager.isFirstTimeLaunch()){
            launchHomeScreen();
            finish();
        }
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
        setContentView(R.layout.activity_welcome);

        PaperOnboardingPage scr1 = new PaperOnboardingPage("Safe and Secure",
                "Provides real time data encyption for better Privacy. " +
                        "Prevent unauthorized data access by any other applications.",
                Color.parseColor("#678FB4"), R.mipmap.computer, R.drawable.security_key);

        PaperOnboardingPage scr2 = new PaperOnboardingPage("Stores Indefinitely",
                "Store and manage large amount of data easily with high performance algorithms",
                Color.parseColor("#65B0B4"), R.drawable.store, R.drawable.store_key);

        PaperOnboardingPage scr3 = new PaperOnboardingPage("Features",
                "Intuitive Design with features such as Swipe To Delete, One Tap Search and much more",
                Color.parseColor("#F48FB1"), R.drawable.features, R.drawable.features_key);

        PaperOnboardingPage scr4 = new PaperOnboardingPage(getString(R.string.app_name),
                getString(R.string.app_desc),
                Color.parseColor("#80CBC4"), R.mipmap.poster, R.mipmap.favorites);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr4);
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);

        PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.user_onboarding_fragment_container, onBoardingFragment);
        fragmentTransaction.commit();


        onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {

                launchHomeScreen();
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void launchHomeScreen() {
        prefsManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }


}
