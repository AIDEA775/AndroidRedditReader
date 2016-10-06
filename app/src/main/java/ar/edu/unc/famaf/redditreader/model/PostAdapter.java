package ar.edu.unc.famaf.redditreader.model;

import android.content.Context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) getContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.post_news, null);

            PostModel post = list.get(position);

            TextView textSub = (TextView) convertView.findViewById(R.id.post_text_sub);
            TextView textTitle = (TextView) convertView.findViewById(R.id.post_text_title);
            TextView textTime = (TextView) convertView.findViewById(R.id.post_text_time);
            TextView textComments = (TextView) convertView.findViewById(R.id.post_text_comments);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.post_image);

            textSub.setText(post.getTextSub());
            textTitle.setText(post.getTextTitle());
            textTime.setText(post.getTextTime());
            textComments.setText(String.format(Locale.US,"%d comments", post.getComments()));
            imageView.setImageResource(post.getImageId());
        }
        return convertView;
    }
}
