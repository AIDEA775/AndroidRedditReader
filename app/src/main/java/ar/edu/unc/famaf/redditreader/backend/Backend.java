package ar.edu.unc.famaf.redditreader.backend;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public List<PostModel> getTopPosts() {
        PostModel post1 = new PostModel();
        post1.setTextSub("r/which/because/take");
        post1.setTextTitle("But I must explain to you how all this mistaken");
        post1.setTextTime("1h");
        post1.setImage(R.drawable.reddit_post);
        post1.setComments(332);

        PostModel post2 = new PostModel();
        post2.setTextSub("r/denounce/undertakes");
        post2.setTextTitle("No one rejects, dislikes, or avoids pleasure itself");
        post2.setTextTime("2h");
        post2.setImage(R.drawable.reddit_post);
        post2.setComments(654);

        PostModel post3 = new PostModel();
        post3.setTextSub("r/blinded/cannot");
        post3.setTextTitle("Nor again is there anyone");
        post3.setTextTime("4h");
        post3.setImage(R.drawable.reddit_post);
        post3.setComments(654);

        PostModel post4 = new PostModel();
        post4.setTextSub("r/physical/moment");
        post4.setTextTitle("To take a trivial example, which of us ever undertakes laborious physical exercise");
        post4.setTextTime("8h");
        post4.setImage(R.drawable.reddit_post);
        post4.setComments(54);

        PostModel post5 = new PostModel();
        post5.setTextSub("r/again/has/obtain");
        post5.setTextTitle("But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences");
        post5.setTextTime("16h");
        post5.setImage(R.drawable.reddit_post);
        post5.setComments(54332);

        List<PostModel> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        list.add(post3);
        list.add(post4);
        list.add(post5);
        return list;
    }
}
