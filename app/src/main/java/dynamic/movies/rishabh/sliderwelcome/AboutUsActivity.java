package dynamic.movies.rishabh.sliderwelcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUsActivity extends AppCompatActivity {

    private Intent intent;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    private static final String TAG= AboutUsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = (AdView) findViewById(R.id.aboutActivity_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        StringBuilder contents = new StringBuilder(getResources().getString(R.string.app_desc));

        contents.append("I thank you for trying out my work in this application. I would also love" +
                " to hear what you have to say about this app. Please rate us on PlayStore and consider " +
                "reporting bugs or any errors if encountered for the enhanced community experience. " +
                "Please use the following platforms to connect with me.");


        AboutView view  = AboutBuilder.with(this)
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .setPhoto(R.mipmap.developer)
                .setCover(R.mipmap.background)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName("Rishabh Agrawal")
                .setSubTitle("Android App Developer")
                .setLinksColumnsCount(4)
                .setBrief("Independent Android app developer, who follow his instincts not the herd." +
                        " Love coding, gaming, son of Quora and Reddit.")
                .addGooglePlayStoreLink("04328648587658402187")
                .addGitHubLink("ssrishabh96")
                .addTwitterLink("www.twitter.com/ssrishabh96")
                .addGooglePlusLink("+RishabhAgrawal96")
                .addYoutubeChannelLink("UCGCq6dmpP3sqRhfg8Dk_5XQ")
                .addDribbbleLink("https://dribbble.com/rishabh96")
                .addLinkedInLink("rishabh-agrawal-085541113")
                .addEmailLink("rishabh0148@gmail.com")
                .addSkypeLink("http://join.skype.com/LcHxJf9HlpuB")
                .addGoogleLink("https://plus.google.com/u/0/+RishabhAgrawal96")
                .addAndroidLink("https://play.google.com/store/apps/developer?id=Rishabh+Agrawal")
                .addWebsiteLink("http://www.googlelle.com")
                .addFiveStarsAction()
                .addMoreFromMeAction("Rishabh+Agrawal")
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addUpdateAction()
                .setActionsColumnsCount(2)
                .addFeedbackAction("rishabh0148@gmail.com")
                .addDonateAction(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.me/rishabh96")))
                .setWrapScrollView(true)
                .setShowAsCard(true)
                .build();


//        aboutPage = new AboutPage(this)
//                .isRTL(false)
//                .setDescription(getString(R.string.app_desc))
//                .addItem(new Element().setTitle("Version 2.0"))
//                .addGroup("Connect with us")
//                .addEmail("rishabh0148@gmail.com")
//                .addWebsite("http://googlelle.com")
//                .addTwitter("ssrishabh96")
//                .addYoutube("UCGCq6dmpP3sqRhfg8Dk_5XQ")
//                .addPlayStore(getApplication().getPackageName())
//                .addGitHub("ssrishabh96","My Repository")
//                .create();

        FrameLayout frameLayout=(FrameLayout) findViewById(R.id.aboutActivityContainer);
        frameLayout.addView(view);
        //MobileAds.initialize(this,"ca-app-pub-9816038748687358~4577598021");
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.myadID));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();

        if(mInterstitialAd.isLoaded())
            mInterstitialAd.show();

    }

    public void showAllApps(View view) {
        Log.i(TAG, "showAllApps: fired");
        intent=new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=rishabh agrawal"));
        startActivity(Intent.createChooser(intent,"Play Store"));
    }

    public void showPhysics(View view) {
        Log.i(TAG, "showPhysics: fired");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=inspireme.rishabh.physicssiunitdimensions"));
        startActivity(Intent.createChooser(intent,"Play Store"));

    }

    public void showSplits(View view) {
        Log.i(TAG, "showSplits: fired");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=tipcalc.rishabh.mytipplease"));
        startActivity(Intent.createChooser(intent,"Play Store"));

    }

    public void linkedin(View view) {
        Log.i(TAG, "linkedin: fired");
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/rishabh-agrawal-085541113/"));

        startActivity(Intent.createChooser(intent, "Connect through Linkedin"));
    }

    public void email(View view) {
        Log.i(TAG, "email: fired");

        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "rishabh0148@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "From ClipBoard App: ");
        intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    public void twitter(View view) {
        Log.i(TAG, "twitter: fired");
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=ssrishabh96"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ssrishabh96"));
        }
        startActivity(Intent.createChooser(intent,"Twitter"));

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    public void googlelle(View view) {
        Log.i(TAG, "googlelle: fired");
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://googlelle.com"));
        startActivity(Intent.createChooser(intent,"Web Broswer"));
    }
    }