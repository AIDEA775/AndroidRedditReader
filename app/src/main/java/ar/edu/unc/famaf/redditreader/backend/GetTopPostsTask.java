package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Child;
import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class GetTopPostsTask extends AsyncTask<Void, Void, List<PostModel>> {
    private Parser parser;
    private GetTopPostsListener listener;

    interface GetTopPostsListener {
        void onReceivePosts(List<PostModel> postModels);
    }

    GetTopPostsTask(GetTopPostsListener listener) {
        this.parser = new Parser();
        this.listener = listener;
    }

    @Override
    protected List<PostModel> doInBackground(Void... params) {
        try {
            Log.i("GetTopPost", "Connecting to reddit.com");
            HttpURLConnection conn = (HttpURLConnection)
                    new URL("https://www.reddit.com/top/.json?limit=50").openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Host", "www.reddit.com");

            Log.i("GetTopPost", "Code " + String.valueOf(conn.getResponseCode()));

            InputStream in = new BufferedInputStream(conn.getInputStream());

            Listing listing = parser.readJsonStream(in);

            Log.i("GetTopPost", "Parsed JSON");
            List<PostModel> posts = new ArrayList<>();
            List<Child> children = listing.getChildren();

            for (Child child : children) {
                posts.add(child.getData());
            }
            return posts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<PostModel> param) {
        if (param != null) {
            listener.onReceivePosts(param);
        }
    }
}
