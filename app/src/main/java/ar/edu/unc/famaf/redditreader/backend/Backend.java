package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class Backend implements GetTopPostsTask.GetTopPostsListener {
    private static Backend ourInstance = new Backend();
    private PostsIteratorListener listener;
    private RedditDBHelper dbHelper;
    private List<PostModel> postsList = null;
    private String lastPost;
    private int index;
    private boolean clear = true;

    public interface PostsIteratorListener {
        void nextPosts(List<PostModel> posts);
    }

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getNextPosts(Context context, PostsIteratorListener listener, String category) {
        this.listener = listener;
        this.dbHelper = new RedditDBHelper(context);

        // Load more posts
        if (this.postsList == null || index + 5 >= this.postsList.size()) {
            if (isOnline(context)) {

                // Clear old post
                if (this.clear) {
                    dbHelper.clearTopPosts();
                    clear = false;
                }

                // From internet
                Log.i("Backend", "Executing GetTopPostsTask");
                GetTopPostsTask getTopPostsTask = new GetTopPostsTask(this);
                getTopPostsTask.execute(category, this.lastPost);
            } else {
                // From database
                Log.i("Backend", "Reading from database");
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
        this.lastPost = listing.getAfter();
        this.index = 0;

        new SaveTopPostTask(posts).execute();

        returnPostsToListener();
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void returnPostsToListener() {
        if (index + 5 < this.postsList.size()) {
            listener.nextPosts(this.postsList.subList(index, index + 5));
            index += 5;
        }
    }

    private class SaveTopPostTask extends AsyncTask<Void, Void, Void> {
        List<PostModel> postModelList;

        SaveTopPostTask(List<PostModel> postModels) {
            this.postModelList = postModels;
        }

        @Override
        protected Void doInBackground(Void... params) {
            dbHelper.saveNextTopPosts(this.postModelList);
            return null;
        }
    }
}
