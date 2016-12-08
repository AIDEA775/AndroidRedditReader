package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class Backend implements GetPostsTask.GetPostsListener {
    private static boolean sClearPosts = true;

    private RedditDBHelper mDbHelper;
    private PostsIteratorListener mListener;
    private Context mContext;
    private String mFilter;

    private List<PostModel> mPostsList = null;
    private String mLastPost;
    private int mIndex;

    public interface PostsIteratorListener {
        void nextPosts(List<PostModel> posts);
    }

    public Backend(Context context, PostsIteratorListener listener, String filter) {
        mContext = context;
        mListener = listener;
        mFilter = filter;
        mDbHelper = RedditDBHelper.getInstance(context);
    }

    public void reloadPostsFromDatabase() {
        Log.i("Backend", "Reload posts from database");
        mPostsList = mDbHelper.getPostsFromDatabase(mFilter);
        if (!mPostsList.isEmpty()) {
            mLastPost = mPostsList.get(mPostsList.size() - 1).getId();
            returnPostsToListener();
        }
    }

    public void getNextPosts() {
        // Load more posts
        if (mPostsList == null || mIndex + 5 >= mPostsList.size()) {
            if (isOnline(mContext)) {

                // Clear old post
                if (sClearPosts) {
                    mDbHelper.clearPosts();
                    sClearPosts = false;
                }

                // From internet
                Log.i("Backend", "Executing GetPostsTask");
                GetPostsTask getPostsTask = new GetPostsTask(this);
                getPostsTask.execute(mFilter, mLastPost);
            } else {
                // From database
                Log.i("Backend", "Reading from database");
                this.mPostsList = mDbHelper.getPostsFromDatabase(mFilter);
            }
        }

        if (mPostsList != null) returnPostsToListener();
    }

    @Override
    public void onReceivePosts(Listing listing) {
        List<PostModel> posts = listing.getChildren();

        mPostsList = posts;
        mLastPost = listing.getAfter();
        mIndex = 0;

        new SavePostsThread(posts).start();

        returnPostsToListener();
    }

    private void returnPostsToListener() {
        if (mIndex + 5 < this.mPostsList.size()) {
            mListener.nextPosts(this.mPostsList.subList(mIndex, mIndex + 5));
            mIndex += 5;
        }
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class SavePostsThread extends Thread {
        private List<PostModel> mPostModelList;

        SavePostsThread(List<PostModel> postModels) {
            mPostModelList = postModels;
        }

        @Override
        public void run() {
            mDbHelper.saveNextPosts(mPostModelList, mFilter);
        }
    }
}
