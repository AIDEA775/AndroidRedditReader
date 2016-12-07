package ar.edu.unc.famaf.redditreader.backend;

import android.provider.BaseColumns;


final class RedditContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RedditContract() {}

    /* Inner class that defines the table contents */
    static class PostEntry implements BaseColumns {
        static final String TABLE_NAME = "post";
        static final String FILTER = "filter";
        static final String DOMAIN = "domain";
        static final String SUBREDDIT = "subreddit";
        static final String ID = "id";
        static final String AUTHOR = "author";
        static final String THUMBNAIL = "thumbnail";
        static final String PREVIEW = "preview";
        static final String URL = "url";
        static final String TITLE = "title";
        static final String CREATED_UTC = "created_utc";
        static final String NUM_COMMENTS = "num_comments";
        static final String UPS = "ups";
        static final String BITMAP = "bitmap";
    }
}

