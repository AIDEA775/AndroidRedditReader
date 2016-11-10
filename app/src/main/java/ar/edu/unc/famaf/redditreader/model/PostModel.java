package ar.edu.unc.famaf.redditreader.model;


import java.io.Serializable;

public class PostModel implements Serializable {
    private String domain;
    private String subreddit;
    private String id;
    private String author;
    private String thumbnail;
    private String url;
    private String title;
    private long created_utc;
    private int num_comments;
    private int ups;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedUtc() {
        return created_utc;
    }

    public void setCreatedUtc(long created_utc) {
        this.created_utc = created_utc;
    }

    public int getNumComments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }
}
