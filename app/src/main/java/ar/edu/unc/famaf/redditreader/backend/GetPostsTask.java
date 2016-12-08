package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.model.Listing;


class GetPostsTask extends AsyncTask<String, Void, Listing> {
    private Parser mParser;
    private GetPostsListener mListener;

    interface GetPostsListener {
        void onReceivePosts(Listing listing);
    }

    GetPostsTask(GetPostsListener listener) {
        mParser = new Parser();
        mListener = listener;
    }

    @Override
    protected Listing doInBackground(String... params) {
        try {
            Log.i("GetPostsTask", "Connecting to reddit.com");
            HttpURLConnection conn = (HttpURLConnection)
                    new URL(String.format(Locale.US,
                            "https://www.reddit.com/%s/.json?limit=50&after=%s",
                            params[0], params[1])).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Host", "www.reddit.com");

            Log.i("GetPostsTask", "Code " + String.valueOf(conn.getResponseCode()));

            InputStream in = new BufferedInputStream(conn.getInputStream());

            return mParser.readJsonStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Listing param) {
        if (param != null) mListener.onReceivePosts(param);
    }
}
