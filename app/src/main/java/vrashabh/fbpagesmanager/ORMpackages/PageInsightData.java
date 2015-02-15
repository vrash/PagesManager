package vrashabh.fbpagesmanager.ORMpackages;

/**
 * Created by vrashabh on 2/15/15.
 */
public class PageInsightData {
    String id;
    String name;
    String numberOfUniquePostViews;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfUniquePostViews() {
        return numberOfUniquePostViews;
    }

    public void setNumberOfUniquePostViews(String numberOfUniquePostViews) {
        this.numberOfUniquePostViews = numberOfUniquePostViews;
    }
}
