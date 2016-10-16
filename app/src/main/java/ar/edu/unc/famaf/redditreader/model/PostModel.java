package ar.edu.unc.famaf.redditreader.model;


import java.net.URL;

public class PostModel {
    private String textSub;
    private String textTitle;
    private String textTime;
    private URL imageSrc;
    private int comments;

    public void setTextSub(String textSub) {
        this.textSub = textSub;
    }

    public String getTextSub() {
        return this.textSub;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextTitle() {
        return this.textTitle;
    }

    public void setTextTime(String textTime) {
        this.textTime = textTime;
    }

    public String getTextTime() {
        return this.textTime;
    }

    public void setImageSrc(URL image) {
        this.imageSrc = image;
    }

    public URL getImageSrc() {
        return this.imageSrc;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getComments() {
        return this.comments;
    }
}
