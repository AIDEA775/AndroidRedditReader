package ar.edu.unc.famaf.redditreader.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsDetailActivityFragment extends Fragment {
    TextView title;

    public NewsDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news_detail, container, false);

        title = (TextView) v.findViewById(R.id.detail_title);

        return v;
    }

    public void setDetailPost(PostModel post) {
        title.setText(post.getTitle());
    }

}
