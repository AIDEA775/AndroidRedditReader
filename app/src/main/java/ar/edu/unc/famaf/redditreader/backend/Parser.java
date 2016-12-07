package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
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

    private List<PostModel> readChildrenList(JsonReader reader) throws IOException {
        List<PostModel> children = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            children.add(readChild(reader));
        }
        reader.endArray();
        return children;
    }

    private PostModel readChild(JsonReader reader) throws IOException {
        PostModel child = null;

        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "data":
                    child = readChildData(reader);
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
                case "name":
                    postModel.setId(reader.nextString());
                    break;
                case "author":
                    postModel.setAuthor(reader.nextString());
                    break;
                case "thumbnail":
                    postModel.setThumbnail(reader.nextString());
                    break;
                case "preview":
                    postModel.setPreview(readPreview(reader));
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

    private String readPreview(JsonReader reader) throws IOException{
        String url = null;
        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("images")) {
                url = readImages(reader);
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        return url;
    }

    private String readImages(JsonReader reader) throws IOException{
        String url = null;
        reader.beginArray();

        if (reader.hasNext()) {
            // take first object
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("source")) {
                    url = readSource(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }

        reader.endArray();
        return url;
    }

    private String readSource(JsonReader reader) throws IOException {
        String url = null;
        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("url")) {
                url = reader.nextString();
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        return url;
    }
}
