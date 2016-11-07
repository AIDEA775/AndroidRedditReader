package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;


public class Backend implements GetTopPostsTask.GetTopPostsListener {
    private static Backend ourInstance = new Backend();
    private PostsIteratorListener listener;
    private RedditDBHelper dbHelper;
    private List<PostModel> postsList = null;
    private String lastPost;

    public interface PostsIteratorListener {
        void onReceivePostsUI(List<PostModel> postModelsList);
        void nextPosts(List<PostModel> posts);
    }

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPosts(Context context, PostsIteratorListener listener) {
        this.listener = listener;
        this.dbHelper = new RedditDBHelper(context);

        this.listener.onReceivePostsUI(this.dbHelper.getTopPostsFromDatabase());

        if (isOnline(context)) {
            Log.i("Backend", "Executing GetTopPostsTask");
            GetTopPostsTask getTopPostsTask = new GetTopPostsTask(this);
            getTopPostsTask.execute();
        }
    }
    // todo traer posts desde la base de datos y llamar a listener.nextPosts(posts)
    // todo traer mas post si hace falta
    public void getNextPosts(Context context, PostsIteratorListener listener) {

        Log.i("Backend", "Getting next 5 posts");
        this.listener = listener;
        this.dbHelper = new RedditDBHelper(context);

        // Si hay que cargar mas desde internet
        if (this.postsList == null || this.postsList.isEmpty()) {
            if (isOnline(context)) {
                Log.i("Backend", "Executing GetTopPostsTask");
                GetTopPostsTask getTopPostsTask = new GetTopPostsTask(this);
                getTopPostsTask.execute(this.lastPost);
            } else {
                this.postsList = this.dbHelper.getTopPostsFromDatabase();
            }
        }
        // Devolver 5 posts
        if (this.postsList != null && !this.postsList.isEmpty()) {
            returnPostsToListener();
        }
    }

    @Override
    public void onReceivePosts(List<PostModel> posts, String after) {
        this.postsList = posts;
        this.dbHelper.saveTopPosts(posts);
        this.lastPost = after;

        // Devolver 5 posts
        returnPostsToListener();
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void returnPostsToListener() {
        ArrayList<PostModel> posts = new ArrayList<>();

        // Pop 5 post for postList
        for (int i = 0; i < 5; i++) {
            posts.add(this.postsList.get(0));
            this.postsList.remove(0);
        }
        listener.nextPosts(posts);
    }
}
