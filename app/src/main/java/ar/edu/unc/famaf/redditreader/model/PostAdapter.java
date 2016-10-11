package ar.edu.unc.famaf.redditreader.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.R;


public class PostAdapter extends ArrayAdapter<PostModel>{
    private List<PostModel> list = null;

    public PostAdapter(Context context, int textViewResourceId, List<PostModel> postsList) {
        super(context, textViewResourceId);
        this.list = postsList;
    }

    private static class ViewHolder {
        TextView textSub;
        TextView textTitle;
        TextView textTime;
        ImageView imageId;
        TextView comments;
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
            holder.comments = (TextView) convertView.findViewById(R.id.post_text_comments);
            holder.imageId = (ImageView) convertView.findViewById(R.id.post_image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PostModel post = list.get(position);

        holder.textSub.setText(post.getTextSub());
        holder.textTitle.setText(post.getTextTitle());
        holder.textTime.setText(post.getTextTime());
        holder.comments.setText(String.format(Locale.US, "%d " +
                getContext().getString(R.string.coments_post), post.getComments()));
        holder.imageId.setImageResource(post.getImageId());

        return convertView;
    }
}
