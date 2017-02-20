package eud.zhuoxin.feicui.mynews.api;

/**
 * Created by Administrator on 2017/1/5.
 */

public interface HttpClientListener {
    void getResultsucced(String result);//成功
    void getResultFailer(String result);//失败
    void getResulrException(Exception e);//异常


}
