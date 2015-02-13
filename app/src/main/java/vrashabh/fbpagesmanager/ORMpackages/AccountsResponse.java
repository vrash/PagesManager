package vrashabh.fbpagesmanager.ORMpackages;

import java.util.ArrayList;

/**
 * Created by vrashabh on 2/13/15.
 */
public class AccountsResponse {
    ArrayList<AcResponseData> acresponse;

    public class AcResponseData {
        public String access_token;
        public String category;
        public String name;
        public String id;
        public String[] perms;
    }
}