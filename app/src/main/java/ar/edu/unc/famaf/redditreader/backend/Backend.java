package ar.edu.unc.famaf.redditreader.backend;

import android.util.Log;
import ar.edu.unc.famaf.redditreader.model.PostAdapter;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public void getTopPosts(PostAdapter adapter) {
        Log.i("Backend", "Executing GetTopPostsTask");
        GetTopPostsTask getTopPostsTask = new GetTopPostsTask(adapter);
        getTopPostsTask.execute();
    }
}
