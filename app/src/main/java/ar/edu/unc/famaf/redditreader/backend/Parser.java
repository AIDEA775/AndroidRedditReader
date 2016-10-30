package ar.edu.unc.famaf.redditreader.backend;


import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.Child;
import ar.edu.unc.famaf.redditreader.model.PostModel;

@SuppressWarnings("TryFinallyCanBeTryWithResources")
public class Parser {
    Listing readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readRedditObject(reader);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Listing readRedditObject(JsonReader reader) throws IOException {
        Listing listing = null;

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("data")) {
                listing = readListingData(reader);
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        return listing;
    }

    private Listing readListingData(JsonReader reader) throws IOException {
        Listing listing = new Listing();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("modhash")) {
                listing.setModhash(reader.nextString());
            } else if (name.equals("children")) {
                listing.setChildren(readChildrenList(reader));
            } else if (name.equals("after") && reader.peek() != JsonToken.NULL) {
                listing.setAfter(reader.nextString());
            } else if (name.equals("before") && reader.peek() != JsonToken.NULL) {
                listing.setAfter(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return listing;
    }


    private List<Child> readChildrenList(JsonReader reader) throws IOException {
        List<Child> children = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            children.add(readChild(reader));
        }
        reader.endArray();
        return children;
    }

    private Child readChild(JsonReader reader) throws IOException {
        Child child = new Child();

        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "kind":
                    child.setKind(reader.nextString());
                    break;
                case "data":
                    child.setData(readChildData(reader));
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return child;
    }

    private PostModel readChildData(JsonReader reader) throws IOException {
        PostModel postModel = new PostModel();

        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "domain":
                    postModel.setDomain(reader.nextString());
                    break;
                case "subreddit":
                    postModel.setSubreddit(reader.nextString());
                    break;
                case "id":
                    postModel.setId(reader.nextString());
                    break;
                case "author":
                    postModel.setAuthor(reader.nextString());
                    break;
                case "thumbnail":
                    postModel.setThumbnail(reader.nextString());
                    break;
                case "url":
                    postModel.setUrl(reader.nextString());
                    break;
                case "title":
                    postModel.setTitle(reader.nextString());
                    break;
                case "created_utc":
                    postModel.setCreatedUtc(reader.nextLong());
                    break;
                case "num_comments":
                    postModel.setNum_comments(reader.nextInt());
                    break;
                case "ups":
                    postModel.setUps(reader.nextInt());
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return postModel;
    }
}
