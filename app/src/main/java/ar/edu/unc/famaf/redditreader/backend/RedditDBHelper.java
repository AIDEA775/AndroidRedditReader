package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.backend.RedditContract.PostEntry;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class RedditDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Posts.db";

    RedditDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PostEntry.TABLE_NAME + " ("
                + PostEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PostEntry.DOMAIN + " TEXT NOT NULL,"
                + PostEntry.SUBREDDIT + " TEXT NOT NULL,"
                + PostEntry.ID + " TEXT NOT NULL,"
                + PostEntry.AUTHOR + " TEXT NOT NULL,"
                + PostEntry.THUMBNAIL + " TEXT NOT NULL,"
                + PostEntry.URL + " TEXT NOT NULL,"
                + PostEntry.TITLE + " TEXT NOT NULL,"
                + PostEntry.CREATED_UTC + " INTEGER,"
                + PostEntry.NUM_COMMENTS + " INTEGER,"
                + PostEntry.UPS + " INTEGER,"
                + "UNIQUE (" + PostEntry.ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + PostEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    List<PostModel> getTopPostsFromDatabase() {
        ArrayList<PostModel> list = new ArrayList<>();
        Cursor c = getReadableDatabase().query(PostEntry.TABLE_NAME,null,null,null,null,null,null);
        while (c.moveToNext()) {
            PostModel post = new PostModel();
            post.setDomain(c.getString(c.getColumnIndex(PostEntry.DOMAIN)));
            post.setSubreddit(c.getString(c.getColumnIndex(PostEntry.SUBREDDIT)));
            post.setId(c.getString(c.getColumnIndex(PostEntry.ID)));
            post.setAuthor(c.getString(c.getColumnIndex(PostEntry.AUTHOR)));
            post.setThumbnail(c.getString(c.getColumnIndex(PostEntry.THUMBNAIL)));
            post.setUrl(c.getString(c.getColumnIndex(PostEntry.URL)));
            post.setTitle(c.getString(c.getColumnIndex(PostEntry.TITLE)));
            post.setCreatedUtc(c.getLong(c.getColumnIndex(PostEntry.CREATED_UTC)));
            post.setNum_comments(c.getInt(c.getColumnIndex(PostEntry.NUM_COMMENTS)));
            post.setUps(c.getInt(c.getColumnIndex(PostEntry.UPS)));
            list.add(post);
        }
        c.close();
        return list;
    }

    void saveTopPosts(List<PostModel> postModelsList){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.delete(PostEntry.TABLE_NAME, null, null);

        for(PostModel post : postModelsList) {
            sqLiteDatabase.insert(PostEntry.TABLE_NAME, null, postToContentValues(post));
        }

        sqLiteDatabase.close();
    }

    private ContentValues postToContentValues(PostModel post) {
        ContentValues values = new ContentValues();
        values.put(RedditContract.PostEntry.DOMAIN, post.getDomain());
        values.put(RedditContract.PostEntry.SUBREDDIT, post.getSubreddit());
        values.put(RedditContract.PostEntry.ID, post.getId());
        values.put(RedditContract.PostEntry.AUTHOR, post.getAuthor());
        values.put(RedditContract.PostEntry.THUMBNAIL, post.getThumbnail());
        values.put(RedditContract.PostEntry.URL, post.getUrl());
        values.put(RedditContract.PostEntry.TITLE, post.getTitle());
        values.put(RedditContract.PostEntry.CREATED_UTC, post.getCreatedUtc());
        values.put(RedditContract.PostEntry.NUM_COMMENTS, post.getNumComments());
        values.put(RedditContract.PostEntry.UPS, post.getUps());
        return values;
    }
}
