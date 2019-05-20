package dynamic.movies.rishabh.sliderwelcome;

import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import dynamic.movies.rishabh.sliderwelcome.customviews.MyRecyclerView;
import dynamic.movies.rishabh.sliderwelcome.helper.RecyclerItemTouchHelper;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by RISHABH on 5/14/2016.
 */
public class FragmentA extends Fragment implements MyRealmRecyclerViewAdapter.onNewItemCopiedListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private static final String TAG=FragmentA.class.getSimpleName();

    private static final int[] COLORS = new int[] {
            Color.argb(255, 28, 160, 170),
            Color.argb(255, 99, 161, 247),
            Color.argb(255, 13, 79, 139),
            Color.argb(255, 89, 113, 173),
            Color.argb(255, 200, 213, 219),
            Color.argb(255, 99, 214, 74),
            Color.argb(255, 205, 92, 92),
            Color.argb(255, 105, 5, 98)
    };

    private Context context;
    private MyRealmRecyclerViewAdapter toDoRealmAdapter;
    private MyRecyclerView recyclerView;
    private SharedPreferences preferences;
    private final String tag = "MyPrefs";
    private InterstitialAd mInterstitialAd;

    // Realm Data Fields
    private Realm realm;
    private RealmResults<MyPojos> toDoItems;

    private int counter=-1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_a,container,false);
        Log.i(TAG, "onCreateView: fired ");

        TextView playButton = (TextView) view.findViewById(R.id.appTitle1);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/satisfy.ttf");
        playButton.setTypeface(tf);


        Toolbar toolbar=(Toolbar) view.findViewById(R.id.toolbar2);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("");
        realm=Realm.getDefaultInstance();

        toDoItems = realm
                .where(MyPojos.class)
                .findAllAsync()
                .sort("date", Sort.DESCENDING,"time",Sort.DESCENDING);

        recyclerView= view.findViewById(R.id.my_recycler_view_fragA);

        toDoRealmAdapter = new MyRealmRecyclerViewAdapter(context, toDoItems,realm);


        preferences=getContext().getSharedPreferences(tag, MODE_PRIVATE);

        counter=preferences.getInt("counter", 0);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(toDoRealmAdapter);

        View empty=view.findViewById(R.id.emptyView);
        recyclerView.setEmptyView(empty);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        setHasOptionsMenu(true);
        // Setup Interstitial Ads
//        mInterstitialAd = new InterstitialAd(getContext());
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.myadID));
//        requestNewInterstitial();
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                requestNewInterstitial();
//            }
//        });
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }

        return view;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
//        ((AppCompatActivity) getActivity()).setC().setTitle("Your Fragment");

        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Customize searchview text and hint colors
        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = (EditText) searchView.findViewById(searchEditId);
        et.setTextColor(Color.BLACK);
        et.setHintTextColor(Color.YELLOW);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                // perform query here
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
//            toDoItems= realm.where(MyPojos.class).contains("data", query).findAll().sort("date", Sort.DESCENDING);
//            toDoRealmAdapter.notifyDataSetChanged();
//                toDoRealmAdapter.filterResults(query);
//                searchView.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if(toDoRealmAdapter!=null) toDoRealmAdapter.filterResults(newText );
                return true;
            }

        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toDoRealmAdapter.filterResults("");
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Intent intent;
        switch (item.getItemId())
        {

            case R.id.show_intro:

                PrefsManager prefsManager = new PrefsManager(context);
                prefsManager.setFirstTimeLaunch(true);

                intent=new Intent(context,WelcomeActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;

            case R.id.about_US:
                intent= new Intent(context,AboutUsActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
                else
                    startActivity(intent);
                break;

            case R.id.settings:


                intent= new Intent(context,SettingsActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
                else
                startActivity(intent);
                break;

            case R.id.rateOnPlayStore:

                Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Google Play Store not found", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.shareApp:

                Intent shareIntent= new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey, I am using this awesome "+
                        getResources().getString(R.string.app_name)
                        +" app. You can download this too market://details?id=" + context.getPackageName());
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent,"Share this message: "));
                break;

            case R.id.exit:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().finishAndRemoveTask();
                }
                else { getActivity().finish();}
                break;
        }

        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: fired ");

        toDoRealmAdapter=null;
        recyclerView=null;
        context.startService(new Intent(context,CBWatcherService.class));
        new MyAsyncTaskPausedNotification().execute("start");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: fired ");
        context.stopService(new Intent(context,CBWatcherService.class));

        new MyAsyncTaskPausedNotification().execute("stop");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    @Override
    public void onNewListItemCopied(int position) {
    }

    public class MyAsyncTaskPausedNotification extends AsyncTask<String,Void,Void>{


        @Override
        protected Void doInBackground(String... strings) {

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_movie)
                            .setContentTitle("Application is running in background")

                    .setNumber(0);

            String message= strings[0].contentEquals("start")?"Application is running":"Application is Paused";
            mBuilder.setContentTitle(message);


            int mNotificationId = 001;
// Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());

            return null;
        }
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {


        if (viewHolder instanceof MyRealmRecyclerViewAdapter.MyViewHolder) {

            // backup of removed item for undo purpose
            final MyPojos deletedItem = toDoItems.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            toDoRealmAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(recyclerView, "Entry removed", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                   // toDoRealmAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }




    }
}
