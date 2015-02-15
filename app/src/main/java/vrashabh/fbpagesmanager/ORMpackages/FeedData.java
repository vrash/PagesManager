package vrashabh.fbpagesmanager.ORMpackages;

import java.util.ArrayList;

/**
 * Created by vrashabh on 2/14/15.
 */
public class FeedData {

    String id;
    ArrayList<FROMTYPE> from;
    ArrayList<FROMTYPE> to;
    String message;
    String privacy;
    String type;
    ArrayList<APTYPE> applicationPoster;
    String created_time;
    String updated_time;
    String story;
    String picture;
    String link;
    String icon;
    boolean isPublished;

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<FROMTYPE> getFrom() {
        return from;
    }

    public void setFrom(ArrayList<FROMTYPE> from) {
        this.from = from;
    }

    public ArrayList<FROMTYPE> getTo() {
        return to;
    }

    public void setTo(ArrayList<FROMTYPE> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<APTYPE> getApplicationPoster() {
        return applicationPoster;
    }

    public void setApplicationPoster(ArrayList<APTYPE> applicationPoster) {
        this.applicationPoster = applicationPoster;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    class FROMTYPE {
        String category;
        String name;
        String id;

    }

    class APTYPE {
        String name;
        String id;
    }

}
