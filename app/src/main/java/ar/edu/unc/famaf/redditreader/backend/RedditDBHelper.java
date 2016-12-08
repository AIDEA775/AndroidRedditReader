package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.backend.RedditContract.PostEntry;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class RedditDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Posts.db";
    private static RedditDBHelper instance;

    public static synchronized RedditDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RedditDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    private RedditDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PostEntry.TABLE_NAME + " ("
                + PostEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PostEntry.FILTER + " TEXT NOT NULL,"
                + PostEntry.DOMAIN + " TEXT NOT NULL,"
                + PostEntry.SUBREDDIT + " TEXT NOT NULL,"
                + PostEntry.ID + " TEXT NOT NULL,"
                + PostEntry.AUTHOR + " TEXT NOT NULL,"
                + PostEntry.THUMBNAIL + " TEXT NOT NULL,"
                + PostEntry.PREVIEW + " TEXT NOT NULL,"
                + PostEntry.URL + " TEXT NOT NULL,"
                + PostEntry.TITLE + " TEXT NOT NULL,"
                + PostEntry.CREATED_UTC + " INTEGER,"
                + PostEntry.NUM_COMMENTS + " INTEGER,"
                + PostEntry.UPS + " INTEGER,"
                + PostEntry.BITMAP + " BLOB,"
                + "UNIQUE (" + PostEntry.ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PostEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    List<PostModel> getPostsFromDatabase(String filter) {
        ArrayList<PostModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor c = sqLiteDatabase.query(
                PostEntry.TABLE_NAME,
                null,
                PostEntry.FILTER + "=?",
                new String[]{filter},
                null,
                null,
                null);
        while (c.moveToNext()) {
            list.add(postFromCursor(c));
        }

        c.close();
        // Don't close sqLiteDatabase for thread-safely
        return list;
    }

    private PostModel postFromCursor(Cursor c) {
        PostModel post = new PostModel();
        post.setDomain(c.getString(c.getColumnIndex(PostEntry.DOMAIN)));
        post.setSubreddit(c.getString(c.getColumnIndex(PostEntry.SUBREDDIT)));
        post.setId(c.getString(c.getColumnIndex(PostEntry.ID)));
        post.setAuthor(c.getString(c.getColumnIndex(PostEntry.AUTHOR)));
        post.setThumbnail(c.getString(c.getColumnIndex(PostEntry.THUMBNAIL)));
        post.setPreview(c.getString(c.getColumnIndex(PostEntry.PREVIEW)));
        post.setUrl(c.getString(c.getColumnIndex(PostEntry.URL)));
        post.setTitle(c.getString(c.getColumnIndex(PostEntry.TITLE)));
        post.setCreatedUtc(c.getLong(c.getColumnIndex(PostEntry.CREATED_UTC)));
        post.setNum_comments(c.getInt(c.getColumnIndex(PostEntry.NUM_COMMENTS)));
        post.setUps(c.getInt(c.getColumnIndex(PostEntry.UPS)));
        return post;
    }

    void clearPosts() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(PostEntry.TABLE_NAME, null, null);
        // Don't close sqLiteDatabase for thread-safely
    }

    void saveNextPosts(List<PostModel> postModelsList, String filter){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        for (PostModel post : postModelsList) {
            sqLiteDatabase.insertWithOnConflict(
                    PostEntry.TABLE_NAME,
                    null,
                    postToContentValues(post, filter),
                    SQLiteDatabase.CONFLICT_IGNORE);
        }

        // Don't close sqLiteDatabase for thread-safely
    }

    private ContentValues postToContentValues(PostModel post, String filter) {
        ContentValues values = new ContentValues();
        values.put(PostEntry.FILTER, filter);
        values.put(PostEntry.DOMAIN, post.getDomain());
        values.put(PostEntry.SUBREDDIT, post.getSubreddit());
        values.put(PostEntry.ID, post.getId());
        values.put(PostEntry.AUTHOR, post.getAuthor());
        values.put(PostEntry.THUMBNAIL, post.getThumbnail());
        values.put(PostEntry.PREVIEW, post.getPreview());
        values.put(PostEntry.URL, post.getUrl());
        values.put(PostEntry.TITLE, post.getTitle());
        values.put(PostEntry.CREATED_UTC, post.getCreatedUtc());
        values.put(PostEntry.NUM_COMMENTS, post.getNumComments());
        values.put(PostEntry.UPS, post.getUps());
        return values;
    }

    public void saveThumbnailBitmap(String key, Bitmap bitmap) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PostEntry.BITMAP, getBytes(bitmap));

        sqLiteDatabase.update(
                PostEntry.TABLE_NAME,
                values,
                PostEntry.THUMBNAIL + "=?",
                new String[]{key});

        // Don't close sqLiteDatabase for thread-safely
    }

    public Bitmap getThumbnailBitmap(String key) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Bitmap bitmap = null;

        Cursor c = sqLiteDatabase.query(
                PostEntry.TABLE_NAME,
                null,
                PostEntry.THUMBNAIL + "=?",
                new String[]{key},
                null,
                null,
                null);

        if (c.moveToNext()) {
            bitmap = getImage(c.getBlob(c.getColumnIndex(PostEntry.BITMAP)));
        }

        c.close();
        // Don't close sqLiteDatabase for thread-safely
        return bitmap;
    }

    private static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @Nullable
    private static Bitmap getImage(byte[] image) {
        if(image != null)
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        return null;
    }
}
