package ar.edu.unc.famaf.redditreader.backend;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public List<PostModel> getTopPosts() throws MalformedURLException {
        List<PostModel> list = new ArrayList<>();
        PostModel post = new PostModel();

        post.setTextSub("r/which/because/take");
        post.setTextTitle("But I must explain to you how all this mistaken");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://a.thumbs.redditmedia.com/LnxmTehOGE8BB_LCtmYgjyycOc74lTwa7fnOdBeqt00.jpg"));
        post.setComments(332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/denounce/undertakes");
        post.setTextTitle("No one rejects, dislikes, or avoids pleasure itself");
        post.setTextTime("2h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/PcPebKaCSpGy83hxw4X9wdolI3jcwvhYixY-RUuVvBo.jpg"));
        post.setComments(654);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/blinded/cannot");
        post.setTextTitle("A natural response.");
        post.setTextTime("4h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/oae3RrZQgcqDM0L7ryKxSq9es8FjZFuZui8eGe_t_jM.jpg"));
        post.setComments(654);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/physical/moment");
        post.setTextTitle("To take a trivial example, which of us ever undertakes laborious physical exercise");
        post.setTextTime("8h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/LevyZadUudBsx_0gKy-U7reWSh2RVG13ltvzH9pnXQU.jpg"));
        post.setComments(54);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/again/has/obtain");
        post.setTextTitle("But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences");
        post.setTextTime("16h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/Uxg2ofel6GYXU43dm-DWtDsy5SSpdByqqKkV5yQkHtw.jpg"));
        post.setComments(54332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/which/because/take");
        post.setTextTitle("But I must explain to you how all this mistaken");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://a.thumbs.redditmedia.com/LnxmTehOGE8BB_LCtmYgjyycOc74lTwa7fnOdBeqt00.jpg"));
        post.setComments(332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/which/because/take");
        post.setTextTitle("This Lego bag is on point");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/2GNvCACwJaHrzNFDABKM5lFIA06MPyHiQ_pvw6Gdl9w.jpg"));
        post.setComments(332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/which/because/take");
        post.setTextTitle("Sister's Love");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/Fyg3t9wLcb3FKpPXOfAESi9U33dTaqplyyn0uXX4C_I.jpg"));
        post.setComments(332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/which/because/take");
        post.setTextTitle("What a crazy sport...");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/0PsuPlyXAT2fcsC3m3puqEyX05wGAO-Tj9V9t5mQcnc.jpg"));
        post.setComments(332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/which/because/take");
        post.setTextTitle("Coolest thing I ever seen in a space sim");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/-58TmeBuR0XSsRS6tprrglrSCK-dktuQRQO-PQuxd0Q.jpg"));
        post.setComments(332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/which/because/take");
        post.setTextTitle("The new Planet Earth II trailer is pretty epic");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/XJAT_RlIBhY_APWn2NBSQUx_k0vXjvImv0TT-sCrOjQ.jpg"));
        post.setComments(332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/which/because/take");
        post.setTextTitle("This short film was made by PIXAR animators in their spare time and it will blow your mind");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/F2QUhTyBBwC0ANUynv1A7ayk2knC0QhX0b8n3l6GN9A.jpg"));
        post.setComments(332);
        list.add(post);

        post = new PostModel();
        post.setTextSub("r/which/because/take");
        post.setTextTitle("[Image] ...And that is why I succeed.");
        post.setTextTime("1h");
        post.setImageSrc(new URL("https://b.thumbs.redditmedia.com/btCjMXaHVo8AvU0gCG9gtK6f2YTjR2nRLfuuNI7JX4A.jpg"));
        post.setComments(332);
        list.add(post);
        return list;
    }
}
