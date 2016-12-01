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


public class NewsActivityFragment extends Fragment implements Backend.PostsIteratorListener,
        PostAdapter.OnPostButtonSelectedListener {

    private static final String ARG_CATEGORY = "section_number";
    private PostAdapter adapter;
    private Backend backend;
    private OnPostItemSelectedListener listener;
    private String category;

    public interface OnPostItemSelectedListener {
        void onPostItemPicked(PostModel post);
        void onPostButtonBrowserPicked(String url);
    }

    public NewsActivityFragment() {
    }

    public static NewsActivityFragment newInstance(String category) {
        NewsActivityFragment fragment = new NewsActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
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

        category = getArguments().getString(ARG_CATEGORY);

        backend = Backend.getInstance();
        adapter = new PostAdapter(getContext(), R.layout.post_news, this);

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

        loadNextData();
        return v;
    }

    private void loadNextData() {
        backend.getNextPosts(getActivity(), this, this.category);
    }

    @Override
    public void nextPosts(List<PostModel> posts) {
        Log.i("NewsActivityFragment", String.format("Append %d posts to list", posts.size()));
        adapter.appendPosts(posts);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPostButtonBrowserPicked(String url) {
        listener.onPostButtonBrowserPicked(url);
    }
}
