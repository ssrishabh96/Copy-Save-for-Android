package dynamic.movies.rishabh.sliderwelcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;

/**
 * Created by RISHABH on 10/15/2017.
 */

public class EditRealmDialogFragment extends DialogFragment {

    private TextView mTextView;

    public EditRealmDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditRealmDialogFragment newInstance(Realm realm, String title) {
        EditRealmDialogFragment frag = new EditRealmDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_item_realm, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mTextView = (TextView) view.findViewById(R.id.showContents);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title");
        mTextView.setText(title);
        mTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

    }
}