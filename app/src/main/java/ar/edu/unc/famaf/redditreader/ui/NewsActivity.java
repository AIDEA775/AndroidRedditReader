package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class NewsActivity extends AppCompatActivity
        implements NewsActivityFragment.OnPostItemSelectedListener {
    private static final int PICK_LOGIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, PICK_LOGIN_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_LOGIN_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                String user = data.getStringExtra("user");

                TextView textView = (TextView) findViewById(R.id.loginStatusTextView);
                textView.setText(String.format("User %s logged in", user));
            }
        }
    }

    @Override
    public void onPostItemPicked(PostModel post) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        // TODO pasarle el post serializado
        startActivity(intent);
    }
}
