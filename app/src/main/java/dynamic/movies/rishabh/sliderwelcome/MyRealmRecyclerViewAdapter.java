package dynamic.movies.rishabh.sliderwelcome;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by RISHABH on 5/14/2016.
 */
public class MyRealmRecyclerViewAdapter extends
        RealmRecyclerViewAdapter<MyPojos,MyRealmRecyclerViewAdapter.MyViewHolder>
        implements Filterable{

    private Context context;
    private Realm realm;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRealmRecyclerViewAdapter(Context context,
                                      OrderedRealmCollection<MyPojos> realmResults, Realm realm) {
        super(realmResults,true);
        this.context=context;
        this.realm=realm;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

            final MyPojos data = getData().get(position);
            holder.data_tv.setText(data.getData());
            holder.date_tv.setText(data.getDate());
            holder.time_tv.setText(data.getTime());

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shareBody = data.getData();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share this text as you like");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = data.getData();
                    try {
                        setClipboard(context, text);
                        ((onNewItemCopiedListener) context).onNewListItemCopied(position);
                        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                    } catch (ClassCastException cce) {
                    }
                }
            });

        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                EditRealmDialogFragment editNameDialogFragment = EditRealmDialogFragment.newInstance(realm,data.getData());
                editNameDialogFragment.show(fm, "fragment_edit_name");

            }
        });

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView data_tv,time_tv,date_tv;
        public Button btn,share, show;
        public RelativeLayout viewBackground, viewForeground;

        MyViewHolder(View view) {
            super(view);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            data_tv = (TextView) view.findViewById(R.id.copied_text_display_tv);
            time_tv = (TextView) view.findViewById(R.id.time_tv);
            date_tv = (TextView) view.findViewById(R.id.date_tv);
            btn     = (Button)   view.findViewById(R.id.copy_button);
            share   = (Button)   view.findViewById(R.id.share_button);
            show    = (Button)   view.findViewById(R.id.edit_button);
        }
    }

    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            notifyDataSetChanged();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);

        }
    }

    public void removeItem(final int position) {
        Realm realm=Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults realmResults=realm.where(MyPojos.class).findAll();
                realmResults.deleteFromRealm(position);
                // notify the item removed by position
                // to perform recycler view delete animations
                // NOTE: don't call notifyDataSetChanged()
                notifyItemRemoved(position);
            }
        });

    }

    public void restoreItem(MyPojos item, final int position) {

    }

    public interface onNewItemCopiedListener{
        public void onNewListItemCopied(int position);
    }

    public Filter getFilter() {

        MyDataFilter myDataFilter = new MyDataFilter(this);
        return myDataFilter;

    }


    public void filterResults(String text) {
        text = text == null ? null : text.toLowerCase().trim();
        if(text == null || "".equals(text)) {
            updateData(realm.where(MyPojos.class).findAllAsync().sort("date", Sort.DESCENDING,"time",Sort.DESCENDING));
        } else {
            updateData(realm.where(MyPojos.class)
                    .contains("data", text, Case.INSENSITIVE) // TODO: change field
                    .findAllAsync());
        }
    }

    private class MyDataFilter
            extends Filter {
        private final MyRealmRecyclerViewAdapter adapter;

        private MyDataFilter(MyRealmRecyclerViewAdapter adapter) {
            super();
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filterResults(constraint.toString());
        }
    }

}


