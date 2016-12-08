package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.LoadImageTask;
import ar.edu.unc.famaf.redditreader.backend.RedditDBHelper;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class PostAdapter extends ArrayAdapter<PostModel>{
    private List<PostModel> list;
    private OnPostButtonSelectedListener listener;
    private LruCache<String, Bitmap> memoryCache;

    interface OnPostButtonSelectedListener {
        void onPostButtonBrowserPicked(String url);
    }

    PostAdapter(Context context, int textViewResourceId, OnPostButtonSelectedListener listener) {
        super(context, textViewResourceId);
        this.list = new ArrayList<>();
        this.listener = listener;

        final int cacheSize = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8) ;
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    void appendPosts(List<PostModel> list) {
        this.list.addAll(list);
    }

    private static class ViewHolder {
        TextView subreddit;
        TextView title;
        TextView created_utc;
        ImageView thumbnail;
        Button comments;
        ImageButton browser;
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
            holder.comments = (Button) convertView.findViewById(R.id.post_item_comments);
            holder.browser = (ImageButton) convertView.findViewById(R.id.post_browser);
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

        final PostModel post = list.get(position);

        holder.title.setText(post.getTitle());
        holder.subreddit.setText(post.getSubredditPath());
        holder.created_utc.setText(post.getCreatedAgo());
        holder.comments.setText(String.format(Locale.US, "%d " +
                getContext().getString(R.string.coments_post), post.getNumComments()));
        holder.browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPostButtonBrowserPicked(post.getUrl());
            }
        });

        holder.progress.setVisibility(View.GONE);
        String thumbnail = post.getThumbnail();
        switch (thumbnail) {
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
                Bitmap bitmap = getBitmapFromCache(thumbnail);
                if (bitmap != null)
                    holder.thumbnail.setImageBitmap(bitmap);
                else {
                    holder.thread = new LoadThumbnailTask(getContext(), holder.thumbnail, holder.progress);
                    holder.thread.execute(post.getThumbnail());
                }
                break;
        }
        return convertView;
    }

    private void addBitmapToCache(String key, Bitmap bitmap) {
        if (key != null && bitmap != null && getBitmapFromCache(key) == null)
            memoryCache.put(key, bitmap);
    }

    private Bitmap getBitmapFromCache(String key) {
        return memoryCache.get(key);
    }


    private class LoadThumbnailTask extends LoadImageTask {
        private RedditDBHelper dbHelper;

        LoadThumbnailTask(Context context, ImageView view, ProgressBar bar) {
            super(view, bar);
            this.dbHelper = RedditDBHelper.getInstance(context);
        }

        @Override
        public Bitmap doInBackground(String... params) {
            Bitmap thumbnail = dbHelper.getThumbnailBitmap(params[0]);

            if (thumbnail == null) {
                thumbnail = super.doInBackground(params);
                if (!isCancelled() && thumbnail != null) {
                    dbHelper.saveThumbnailBitmap(params[0], thumbnail);
                }
            }
            addBitmapToCache(params[0], thumbnail);
            return thumbnail;
        }
    }
}
