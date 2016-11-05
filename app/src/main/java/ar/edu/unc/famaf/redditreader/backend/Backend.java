package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend implements GetTopPostsTask.GetTopPostsListener {
    private static Backend ourInstance = new Backend();
    private BackendListener listener;

    public interface BackendListener {
        void onReceivePostsUI(List<PostModel> postModelsList);
    }

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPosts(Context context, BackendListener listener) {
        this.listener = listener;

        if (isOnline(context)) {
            Log.i("Backend", "Executing GetTopPostsTask");
            GetTopPostsTask getTopPostsTask = new GetTopPostsTask(this);
            getTopPostsTask.execute();
        } else {
            // todo traer de la base de datos
        }
    }

    @Override
    public void onReceivePosts(List<PostModel> postModelsList) {
        // todo guardar en la base de datos
        listener.onReceivePostsUI(postModelsList);
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
