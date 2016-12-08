package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class Backend implements GetPostsTask.GetPostsListener {
    private static RedditDBHelper dbHelper;
    private static boolean clear = true;

    private Context context;
    private PostsIteratorListener listener;
    private String filter;

    private List<PostModel> postsList = null;
    private String lastPost;
    private int index;

    public interface PostsIteratorListener {
        void nextPosts(List<PostModel> posts);
    }

    public Backend(Context context, PostsIteratorListener listener, String filter) {
        this.context = context;
        this.listener = listener;
        this.filter = filter;

        if (dbHelper == null)
            dbHelper = new RedditDBHelper(context);
    }

    public void reloadPosts() {
        Log.i("Backend", "Reload posts from database");
        this.postsList = dbHelper.getPostsFromDatabase(this.filter);
        if (!this.postsList.isEmpty()) {
            this.lastPost = postsList.get(postsList.size() - 1).getId();
            returnPostsToListener();
        }
    }

    public void getNextPosts() {
        // Load more posts
        if (this.postsList == null || index + 5 >= this.postsList.size()) {
            if (isOnline(context)) {

                // Clear old post
                if (clear) {
                    dbHelper.clearPosts();
                    clear = false;
                }

                // From internet
                Log.i("Backend", "Executing GetPostsTask");
                GetPostsTask getPostsTask = new GetPostsTask(this);
                getPostsTask.execute(filter, this.lastPost);
            } else {
                // From database
                Log.i("Backend", "Reading from database");
                this.postsList = dbHelper.getPostsFromDatabase(this.filter);
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

        new SavePostsThread(posts).start();

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

    private class SavePostsThread extends Thread {
        List<PostModel> postModelList;

        SavePostsThread(List<PostModel> postModels) {
            this.postModelList = postModels;
        }

        @Override
        public void run() {
            dbHelper.saveNextPosts(this.postModelList, filter);
        }
    }
}
