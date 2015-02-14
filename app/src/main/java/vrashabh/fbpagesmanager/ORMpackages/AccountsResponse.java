package vrashabh.fbpagesmanager.ORMpackages;

import java.util.ArrayList;

/**
 * Created by vrashabh on 2/13/15.
 */
public class AccountsResponse {
    ArrayList<AcResponseData> data;
    ArrayList<PagingData> paging;

    public class AcResponseData {
        public String access_token;
        public String category;
        public String name;
        public String id;
        public String[] perms;
    }
    public class PagingData{
        public Cursors cursors;
    }
    public class Cursors{
        public String before;
        public String after;
    }
}