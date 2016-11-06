package ar.edu.unc.famaf.redditreader.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.text.format.DateUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;


public class PostAdapter extends ArrayAdapter<PostModel>{
    private List<PostModel> list = null;
    private LruCache<String, Bitmap> mMemoryCache;

    public PostAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.list = new ArrayList<>();

        final int cacheSize = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8) ;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void swapList(List<PostModel> list) {
        this.list = list;
    }

    private static class ViewHolder {
        TextView subreddit;
        TextView title;
        TextView created_utc;
        ImageView thumbnail;
        TextView comments;
        LoadImageTask thread;
        ProgressBar progress;
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

        if (convertView == null) convertView = ((LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.post_news, parent, false);

        if (convertView.getTag() == null) {
            holder = new ViewHolder();
            holder.subreddit = (TextView) convertView.findViewById(R.id.post_item_subreddit);
            holder.title = (TextView) convertView.findViewById(R.id.post_item_title);
            holder.created_utc = (TextView) convertView.findViewById(R.id.post_item_created_utc);
            holder.comments = (TextView) convertView.findViewById(R.id.post_item_comments);
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.post_item_thumbnail);
            holder.progress = (ProgressBar) convertView.findViewById(R.id.post_item_progressbar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            if (holder.thread != null) {
                holder.thread.setImage = false;
                holder.thread = null;
            }
        }

        PostModel post = list.get(position);

        holder.title.setText(post.getTitle());
        holder.subreddit.setText(String.format(Locale.US, "/r/%s", post.getSubreddit()));
        holder.created_utc.setText(
                DateUtils.getRelativeTimeSpanString(post.getCreatedUtc() * 1000,
                        System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS));
        holder.comments.setText(String.format(Locale.US, "%d " +
                getContext().getString(R.string.coments_post), post.getNumComments()));

        holder.progress.setVisibility(View.GONE);
        switch (post.getThumbnail()) {
            case "default":
                holder.thumbnail.setImageResource(R.drawable.reddit_default);
                break;
            case "self":
                holder.thumbnail.setImageResource(R.drawable.reddit_self);
                break;
            case "image":
                holder.thumbnail.setImageResource(R.drawable.reddit_image);
                break;
            case "nsfw":
                holder.thumbnail.setImageResource(R.drawable.reddit_nsfw);
                break;
            default:
                Bitmap bitmap = getBitmapFromCache(post.getThumbnail());
                if (bitmap == null) {
                    RedditDBHelper dbHelper = new RedditDBHelper(getContext());
                    bitmap = dbHelper.getThumbnailBitmap(post.getThumbnail());
                }
                if (bitmap != null) {
                    holder.thumbnail.setImageBitmap(bitmap);
                } else {
                    holder.progress.setVisibility(View.VISIBLE);
                    holder.thumbnail.setImageResource(android.R.color.transparent);
                    holder.thread = new LoadImageTask(holder.thumbnail, holder.progress);
                    holder.thread.execute(post.getThumbnail());
                }
                break;
        }
        return convertView;
    }

    private void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromCache(String key) {
        return mMemoryCache.get(key);
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private ProgressBar progressBar;
        boolean setImage;

        LoadImageTask(ImageView imageView, ProgressBar progressBar){
            this.imageView = imageView;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            setImage = true;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                Log.i("LoadImageTask", "Save bitmap: " + params[0]);
                addBitmapToCache(String.valueOf(params[0]), bitmap);

                RedditDBHelper dbHelper = new RedditDBHelper(getContext());
                dbHelper.saveThumbnailBitmap(params[0], bitmap);

                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap param) {
            if (setImage && param != null) {
                imageView.setImageBitmap(param);
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}
