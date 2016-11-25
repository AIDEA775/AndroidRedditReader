package ar.edu.unc.famaf.redditreader.backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
    public boolean setImage;
    private ImageView view;
    private ProgressBar bar;

    public LoadImageTask(ImageView view, ProgressBar bar) {
        this.view = view;
        this.bar = bar;
    }

    @Override
    public void onPreExecute() {
        setImage = true;
        bar.setVisibility(View.VISIBLE);
        view.setImageResource(android.R.color.transparent);
    }

    @Override
    public Bitmap doInBackground(String... params) {
        try {
            String key = params[0];
            Bitmap bitmap = null;

            URL url = new URL(key);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(50000);
            connection.connect();
            InputStream input = null;

            if (!isCancelled())
                input = connection.getInputStream();

            if (!isCancelled() && input != null)
                    bitmap = BitmapFactory.decodeStream(input);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap param) {
        if (setImage) {
            bar.setVisibility(View.GONE);
            if (param != null)
                view.setImageBitmap(param);
        }

    }
}

