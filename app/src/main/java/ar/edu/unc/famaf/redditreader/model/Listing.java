package ar.edu.unc.famaf.redditreader.model;


import java.util.List;

public class Listing {
    private String modhash;
    private List<PostModel> children;
    private String after;
    private String before;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public List<PostModel> getChildren() {
        return children;
    }

    public void setChildren(List<PostModel> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
