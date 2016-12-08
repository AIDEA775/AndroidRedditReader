package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.LoadImageTask;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsDetailActivityFragment extends Fragment {
    private TextView mSubreddit;
    private TextView mDomain;
    private TextView mTitle;
    private TextView mCreated_utc;
    private ImageView mPreview;
    private Button mComments;
    private TextView mAuthor;
    private ProgressBar mProgressBar;

    public NewsDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_detail, container, false);

        mSubreddit = (TextView) v.findViewById(R.id.detail_subreddit);
        mDomain = (TextView) v.findViewById(R.id.detail_domain);
        mTitle = (TextView) v.findViewById(R.id.detail_title);
        mCreated_utc = (TextView) v.findViewById(R.id.detail_created_utc);
        mPreview = (ImageView) v.findViewById(R.id.detail_preview);
        mComments = (Button) v.findViewById(R.id.detail_comments);
        mAuthor = (TextView) v.findViewById(R.id.detail_author);
        mProgressBar = (ProgressBar) v.findViewById(R.id.detail_progress);
        return v;
    }

    public void setDetailPost(final PostModel post) {
        mSubreddit.setText(post.getSubredditPath());
        mDomain.setText(post.getDomain());
        mTitle.setText(post.getTitle());
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostWebView.class);
                intent.putExtra(PostWebView.ARG_URL, post.getUrl());
                startActivity(intent);
            }
        });
        mCreated_utc.setText(post.getCreatedAgo());
        mComments.setText(String.format(Locale.US, "%d " +
                getContext().getString(R.string.coments_post), post.getNumComments()));
        mAuthor.setText(post.getAuthor());
        new LoadImageTask(mPreview, mProgressBar).execute(post.getPreview());
    }
}
