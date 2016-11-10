package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent i = getIntent();
        PostModel post = (PostModel) i.getSerializableExtra("Post");

        // TODO pasar el post al fragment y quitar detail title de la actividad

        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText(post.getTitle());
    }
}
