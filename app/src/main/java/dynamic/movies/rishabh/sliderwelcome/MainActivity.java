package dynamic.movies.rishabh.sliderwelcome;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.style.StyleSpan;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private ViewPager viewPager;
    private InterstitialAd mInterstitialAd;

    private static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }
        setContentView(R.layout.activity_main);
        // MobileAds.initialize(this,"ca-app-pub-9816038748687358~4577598021");
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3940256099942544~3347511713");
        mAdView = (AdView) findViewById(R.id.mainActivity_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        stopService(new Intent(MainActivity.this,CBWatcherService.class));
        viewPager=(ViewPager) findViewById(R.id.pager);


        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new FragmentAdapter(fragmentManager));


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9816038748687358/6054331226");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        Toast.makeText(getBaseContext(),"Copy the data and check back here",Toast.LENGTH_SHORT).show();

    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        Intent intent;
//        switch (item.getItemId())
//        {
//            case R.id.about_US:
//                intent= new Intent(this,AboutUsActivity.class);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//                }
//                else
//                    startActivity(intent);
//                break;
//
//            case R.id.settings:
//                if(mInterstitialAd.isLoaded())
//                    mInterstitialAd.show();
//
//                intent= new Intent(this,SettingsActivity.class);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//                }
//                else
//                startActivity(intent);
//                break;
//
//            case R.id.rateOnPlayStore:
//
//                Uri uri = Uri.parse("market://details?id=" + getPackageName());
//                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
//                try {
//                    startActivity(myAppLinkToMarket);
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(this, "Google Play Store not found", Toast.LENGTH_LONG).show();
//                }
//                break;
//
//            case R.id.shareApp:
//
//                if(mInterstitialAd.isLoaded())
//                    mInterstitialAd.show();
//
//                Intent shareIntent= new Intent(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey, I am using this awesome "+
//                        getResources().getString(R.string.app_name)
//                        +" app. You can download this too market://details?id=" + getPackageName());
//                shareIntent.setType("text/plain");
//                startActivity(Intent.createChooser(shareIntent,"Share this message: "));
//                break;
//
//            case R.id.exit:
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    finishAndRemoveTask();
//                }
//                else { finish();}
//                break;
//        }
//
//        return true;
//    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: fired");
        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        else
            Log.i(TAG, "onPause: could not load ads");
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
