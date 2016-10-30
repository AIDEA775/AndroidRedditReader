package ar.edu.unc.famaf.redditreader.model;

public class Child {
    private String kind;
    private PostModel data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public PostModel getData() {
        return data;
    }

    public void setData(PostModel data) {
        this.data = data;
    }
}