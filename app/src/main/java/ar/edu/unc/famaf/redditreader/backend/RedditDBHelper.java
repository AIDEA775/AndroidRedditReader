package ar.edu.unc.famaf.redditreader.backend;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ar.edu.unc.famaf.redditreader.backend.RedditContract.PostEntry;

public class RedditDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Posts.db";

    public RedditDBHelper(Context context) {
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
                + PostEntry.UPS + " INTEGER "
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + PostEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
