package ar.edu.unc.famaf.redditreader.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.R;


public class PostAdapter extends ArrayAdapter<PostModel>{
    private List<PostModel> list = null;
    private LruCache<String, Bitmap> mMemoryCache;

    public PostAdapter(Context context, int textViewResourceId, List<PostModel> postsList) {
        super(context, textViewResourceId);
        this.list = postsList;

        final int cacheSize = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8) ;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    private static class ViewHolder {
        TextView textSub;
        TextView textTitle;
        TextView textTime;
        ImageView imageSrc;
        TextView comments;
        LoadImage thread;
        ProgressBar progressBar;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public int getPosition(PostModel item) {
        return this.list.indexOf(item);
    }

    @Override
    public PostModel getItem(int position) {
        return this.list.get(position);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = ((LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.post_news, null);

            holder = new ViewHolder();
            holder.textSub = (TextView) convertView.findViewById(R.id.post_text_sub);
            holder.textTitle = (TextView) convertView.findViewById(R.id.post_text_title);
            holder.textTime = (TextView) convertView.findViewById(R.id.post_text_time);
            holder.comments = (TextView) convertView.findViewById(R.id.post_comments);
            holder.imageSrc = (ImageView) convertView.findViewById(R.id.post_image);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.post_image_progressbar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            if (holder.thread != null) {
                holder.thread.setImage = false;
            }
        }

        PostModel post = list.get(position);

        holder.textSub.setText(post.getTextSub());
        holder.textTitle.setText(post.getTextTitle());
        holder.textTime.setText(post.getTextTime());
        holder.comments.setText(String.format(Locale.US, "%d " +
                getContext().getString(R.string.coments_post), post.getComments()));

        final Bitmap bitmap = getBitmapFromCache(post.getImageSrc().toString());
        if (bitmap != null) {
            Log.i("Post Adapter: Cache", "Find " + post.getImageSrc().toString());
            holder.progressBar.setVisibility(View.GONE);
            holder.imageSrc.setImageBitmap(bitmap);
        } else {
            Log.i("Post Adapter: Cache", "Search fail " + post.getImageSrc().toString());
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.imageSrc.setImageResource(android.R.color.transparent);
            holder.thread = new LoadImage(holder.imageSrc, holder.progressBar);
            holder.thread.execute(post.getImageSrc());
        }
        return convertView;
    }

    private void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            Log.i("Post Adapter: Cache", "Save bitmap " + key);
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromCache(String key) {
        return mMemoryCache.get(key);
    }

    private class LoadImage extends AsyncTask<URL, Void, Bitmap> {
        private ImageView imageView;
        private ProgressBar progressBar;
        boolean setImage;

        LoadImage(ImageView imageView, ProgressBar progressBar){
            this.imageView = imageView;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            setImage = true;
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            try {
                HttpURLConnection connection = (HttpURLConnection) params[0].openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                addBitmapToCache(String.valueOf(params[0].toString()), bitmap);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap param) {
            if (setImage) {
                imageView.setImageBitmap(param);
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}
