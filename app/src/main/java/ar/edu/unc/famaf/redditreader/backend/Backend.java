package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class Backend implements GetTopPostsTask.GetTopPostsListener {
    private static Backend ourInstance = new Backend();
    private PostsIteratorListener listener;
    private RedditDBHelper dbHelper;
    private List<PostModel> postsList = null;
    private String lastPost;

    public interface PostsIteratorListener {
        void nextPosts(List<PostModel> posts);
    }

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getNextPosts(Context context, PostsIteratorListener listener) {
        this.listener = listener;
        this.dbHelper = new RedditDBHelper(context);

        // Load more posts
        if (this.postsList == null || this.postsList.isEmpty()) {
            if (isOnline(context)) {
                // From internet
                Log.i("Backend", "Executing GetTopPostsTask");
                GetTopPostsTask getTopPostsTask = new GetTopPostsTask(this);
                getTopPostsTask.execute(this.lastPost);
            } else {
                // From database
                this.postsList = this.dbHelper.getTopPostsFromDatabase();
            }
        }

        if (this.postsList != null) {
            returnPostsToListener();
        }
    }

    @Override
    public void onReceivePosts(Listing listing) {
        List<PostModel> posts = listing.getChildren();

        this.postsList = posts;
        this.dbHelper.saveTopPosts(posts);
        this.lastPost = listing.getAfter();

        returnPostsToListener();
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void returnPostsToListener() {
        if (this.postsList.size() >= 5) {

            ArrayList<PostModel> nextPosts = new ArrayList<>();

            // Pop and return 5 post of postList
            for (int i = 0; i < 5; i++) {
                nextPosts.add(this.postsList.get(0));
                this.postsList.remove(0);
            }

            listener.nextPosts(nextPosts);
        }
    }
}
