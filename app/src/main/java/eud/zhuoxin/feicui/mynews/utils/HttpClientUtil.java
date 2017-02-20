package eud.zhuoxin.feicui.mynews.utils;

import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import eud.zhuoxin.feicui.mynews.api.HttpClientListener;
import eud.zhuoxin.feicui.mynews.app.App;
import eud.zhuoxin.feicui.mynews.entity.ImageToWL;
import eud.zhuoxin.feicui.mynews.entity.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/5.
 * 网络链接获取结果的工具类
 * HttpUrlConnection:
 * Url:  目的地
 */

public class HttpClientUtil {
    /**
     * 获取服务器端数据
     *
     * @param target   请求url
     * @param listener 执行完下载操作之后的回调接口
     */

    public static void getResult(URL target, HttpClientListener listener) {
        HttpURLConnection conn = null;
        BufferedReader br = null;
        try {
            //打开链接
            conn = (HttpURLConnection) target.openConnection();
            conn.setRequestMethod("GET");//设置请求方式
            conn.setReadTimeout(10000);//设置读取超时
            conn.setConnectTimeout(8000);//设置链接超时
//            conn.setRequestProperty();//设置请求头信息
            //网络链接
            conn.connect();
            if (conn.getResponseCode() == 200) {//如果状态码等于200，链接成功
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str = "";
                StringBuffer sb = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sb.append(str);//字符串拼接
                }
                //最终结果就是sb.toString
                listener.getResultsucced(sb.toString());
            } else {
                listener.getResultFailer("网络链接失败，请检查网络");
            }
        } catch (IOException e) {
            //异常
            listener.getResultFailer("网络链接异常");
            listener.getResulrException(e);
        } finally {
            if (br != null) {//关流
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();//关闭连接
            }
        }
    }
    /***
     * OkHttp  网络链接
     *
     * @param url
     */
    public static void getResult(final String url, final Handler handler) {
        //创建OkHttp对象
        OkHttpClient client = new OkHttpClient();
        //创建请求对象
        final Request request = new Request.Builder()
                .url(url)//设置请求路径
                .build();
        //创建请求
        Call call = client.newCall(request);
                    //将请求放入请求队列中
        call.enqueue(new Callback() {
            //失败返回结果
            @Override
            public void onFailure(Call call, IOException e) {
                //发送空消息
//                Message msg=handler.obtainMessage();
//                msg.what=0x21;
//                handler.sendMessage(msg);
                handler.sendEmptyMessage(0x21);
            }
            //成功放回结果
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取结果
                String result = response.body().string();
//                InputStream is=response.body().byteStream();
                //Gson解析
                //创建
                Gson gson = new Gson();
                //解析
                ImageToWL datas = gson.fromJson(result, ImageToWL.class);
                //发消息
                Message msg = handler.obtainMessage();
                msg.what = App.IMAGER;
                msg.obj = datas;
                handler.sendMessage(msg);
            }
        });
    }
}
