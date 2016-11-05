package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.model.PostAdapter;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements Backend.BackendListener{
    PostAdapter adapter;

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        Backend mBackend = Backend.getInstance();
        adapter = new PostAdapter(getActivity(), R.layout.post_news);

        ListView listView = (ListView) v.findViewById(R.id.posts_list);
        listView.setAdapter(adapter);

        mBackend.getTopPosts(getActivity(), this);

        return v;
    }

    @Override
    public void onReceivePostsUI(List<PostModel> postModelsList) {
        adapter.swapList(postModelsList);
        adapter.notifyDataSetChanged();
    }
}
