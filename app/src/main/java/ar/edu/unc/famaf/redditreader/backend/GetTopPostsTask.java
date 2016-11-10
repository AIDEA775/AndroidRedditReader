package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.model.Listing;


public class GetTopPostsTask extends AsyncTask<String, Void, Listing> {
    private Parser parser;
    private GetTopPostsListener listener;

    interface GetTopPostsListener {
        void onReceivePosts(Listing listing);
    }

    GetTopPostsTask(GetTopPostsListener listener) {
        this.parser = new Parser();
        this.listener = listener;
    }

    @Override
    protected Listing doInBackground(String... params) {
        try {
            Log.i("GetTopPost", "Connecting to reddit.com");
            HttpURLConnection conn = (HttpURLConnection)
                    new URL("https://www.reddit.com/top/.json?limit=50&after=" + params[0]).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Host", "www.reddit.com");

            Log.i("GetTopPost", "Code " + String.valueOf(conn.getResponseCode()));

            InputStream in = new BufferedInputStream(conn.getInputStream());

            return parser.readJsonStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Listing param) {
        if (param != null) {
            listener.onReceivePosts(param);
        }
    }
}
