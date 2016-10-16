package ar.edu.unc.famaf.redditreader.ui;

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

import java.net.MalformedURLException;

import ar.edu.unc.famaf.redditreader.model.PostAdapter;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.R;


public class NewsActivity extends AppCompatActivity {
    private static final int PICK_LOGIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Backend mBackend = Backend.getInstance();

        PostAdapter adapter = null;
        try {
            adapter = new PostAdapter(this, R.layout.post_news, mBackend.getTopPosts());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
