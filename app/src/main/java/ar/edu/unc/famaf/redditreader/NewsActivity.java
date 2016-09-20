package ar.edu.unc.famaf.redditreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private static final int PICK_LOGIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PostModel post1 = new PostModel();
        post1.setTextSub("sratra");
        post1.setTextTitle("titulo asrtatsatsra");
        post1.setTextTime("8h");
        post1.setImage(R.drawable.reddit_post);
        post1.setComments(3);

        PostModel post2 = new PostModel();
        post2.setTextSub("/stasratra");
        post2.setTextTitle("titsartsraulo asrtatsatsra");
        post2.setTextTime("2h");
        post2.setImage(R.drawable.reddit_post);
        post2.setComments(654);

        PostModel post3 = new PostModel();
        post3.setTextSub("/stastsraratra");
        post3.setTextTitle("tittsasartsraulo asrtatsatsra");
        post3.setTextTime("6h");
        post3.setImage(R.drawable.reddit_post);
        post3.setComments(654);

        PostModel post4 = new PostModel();
        post4.setTextSub("/stasratra");
        post4.setTextTitle("titsartsrautsratslo asrtatsatsra");
        post4.setTextTime("4h");
        post4.setImage(R.drawable.reddit_post);
        post4.setComments(54);

        PostModel post5 = new PostModel();
        post5.setTextSub("/sttrsaasratra");
        post5.setTextTitle("titsartsrsatsautsratslo asrtattsratrssatsra");
        post5.setTextTime("44h");
        post5.setImage(R.drawable.reddit_post);
        post5.setComments(54332);

        ArrayList<PostModel> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        list.add(post3);
        list.add(post4);
        list.add(post5);

        PostAdapter adapter = new PostAdapter(this, R.layout.post_news, list);

        ListView listView = (ListView) findViewById(R.id.posts_list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_in) {
            NewsActivityFragment newsfragment = (NewsActivityFragment)
                    getSupportFragmentManager().findFragmentById(R.id.news_activity_fragment_id);

            Intent i = new Intent(this, LoginActivity.class);
            startActivityForResult(i, PICK_LOGIN_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_LOGIN_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                String user = data.getStringExtra("user");

                TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
                textView.setText(String.format("User %s logged in", user));
            }
        }
    }
}
