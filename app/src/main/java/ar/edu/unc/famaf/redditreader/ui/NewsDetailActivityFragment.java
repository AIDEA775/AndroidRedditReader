package ar.edu.unc.famaf.redditreader.ui;

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
    TextView subreddit;
    TextView title;
    TextView created_utc;
    ImageView preview;
    Button comments;
    TextView author;
    ProgressBar progressBar;

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

        subreddit = (TextView) v.findViewById(R.id.detail_subreddit);
        title = (TextView) v.findViewById(R.id.detail_title);
        created_utc = (TextView) v.findViewById(R.id.detail_created_utc);
        preview = (ImageView) v.findViewById(R.id.detail_preview);
        comments = (Button) v.findViewById(R.id.detail_comments);
        author = (TextView) v.findViewById(R.id.detail_author);
        progressBar = (ProgressBar) v.findViewById(R.id.detail_progress);
        return v;
    }

    public void setDetailPost(PostModel post) {
        subreddit.setText(post.getSubredditPath());
        title.setText(post.getTitle());
        created_utc.setText(post.getCreatedAgo());
        comments.setText(String.format(Locale.US, "%d " +
                getContext().getString(R.string.coments_post), post.getNumComments()));
        author.setText(post.getAuthor());
        new LoadImageTask(preview, progressBar).execute(post.getPreview());
    }

}
