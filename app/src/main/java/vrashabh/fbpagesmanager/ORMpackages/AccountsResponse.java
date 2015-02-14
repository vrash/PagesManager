package vrashabh.fbpagesmanager.ORMpackages;

import android.app.Application;

import java.io.Serializable;

/**
 * Created by vrashabh on 2/13/15.
 */
public class AccountsResponse extends Application implements Serializable{

    public String access_token;
        public String category;
        public String name;
        public String id;
        public String[] perms;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getPerms() {
        return perms;
    }

    public void setPerms(String[] perms) {
        this.perms = perms;
    }

}