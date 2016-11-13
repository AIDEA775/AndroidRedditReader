package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements Backend.PostsIteratorListener {
    PostAdapter adapter;
    Backend backend;
    OnPostItemSelectedListener listener;

    public interface OnPostItemSelectedListener {
        void onPostItemPicked(PostModel post);
    }

    public NewsActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the listener interface. If not, it throws an exception
        try {
            listener = (OnPostItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnPostItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        backend = Backend.getInstance();
        adapter = new PostAdapter(getActivity(), R.layout.post_news);

        ListView listView = (ListView) v.findViewById(R.id.posts_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostModel post = (PostModel) adapterView.getItemAtPosition(i);
                listener.onPostItemPicked(post);
            }
        });

        listView.setAdapter(adapter);

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadNextData();
                return true;
            }
        });

        backend.getNextPosts(getActivity(), this);
        return v;
    }

    private void loadNextData() {
        backend.getNextPosts(getActivity(), this);
    }

    @Override
    public void nextPosts(List<PostModel> posts) {
        Log.i("NewsActivityFragment", String.format("Append %d posts to list", posts.size()));
        adapter.appendPosts(posts);
        adapter.notifyDataSetChanged();
    }
}
