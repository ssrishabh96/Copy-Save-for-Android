package dynamic.movies.rishabh.sliderwelcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by RISHABH on 5/14/2016.
 */
public class FragmentB extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_b,container,false);

        WebView webView=(WebView) view.findViewById(R.id.webView_fragB);
        webView.loadUrl("file:///android_asset/index.htm");

        return view;
    }
}
