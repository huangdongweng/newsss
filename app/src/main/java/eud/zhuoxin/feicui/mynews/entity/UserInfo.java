package eud.zhuoxin.feicui.mynews.entity;

/**
 * Created by Administrator on 2017/1/16.
 */

public class UserInfo {
    private String message;
    private int status;
    private Data data;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public class Data{
        private int result;
        private String token;
        private String explain;

        public int getResult() {
            return result;
        }

        public String getToken() {
            return token;
        }

        public String getExplain() {
            return explain;
        }
    }

}
