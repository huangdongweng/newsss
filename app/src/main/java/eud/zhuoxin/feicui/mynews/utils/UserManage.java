package eud.zhuoxin.feicui.mynews.utils;


import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;

import eud.zhuoxin.feicui.mynews.app.App;
import eud.zhuoxin.feicui.mynews.entity.UserInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/17.
 */

public class UserManage {
    /**
     * 用户登陆
     */
    public static void login(String name, String pwd, final Handler handler) {
        //网络地址
        String url = App.USER_BASE + App.USER_LOG + App.USER_UID + name + App.USER_PWD + pwd + App.USER_DEV;
        //创建请求对象
        OkHttpClient client = new OkHttpClient();
        //创建请求
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //创建请求
        okhttp3.Call call = client.newCall(request);
        //将请求放入请求队列中
        call.enqueue(new Callback() {
            @Override
            //失败返回结果
            public void onFailure(Call call, IOException e) {
                //发送空消息
                handler.sendEmptyMessage(0x10);
            }

            @Override
            //成功放回结果
            public void onResponse(Call call, Response response) throws IOException {
                //获取结果
                String result = response.body().string();
                Log.i("tag","+++++++++"+result);
                //Gson解析
                Gson gson = new Gson();
                //解析
                UserInfo info = gson.fromJson(result, UserInfo.class);
                //发消息
                Message msg = handler.obtainMessage();
                msg.what = 0x12;
                msg.obj = info;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 用户注册
     */
    public static void register(String name, String pwd, String mailot, final Handler handler) {
        //网络地址
        String url = App.USER_BASE + App.USER_REG + App.USER_UID + name + App.USER_PWD + pwd +App.USER_EMA+mailot;
        //创建请求对象
        OkHttpClient client = new OkHttpClient();
        //创建请求
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //创建请求
        okhttp3.Call call = client.newCall(request);
        //将请求放入请求队列中
        call.enqueue(new Callback() {
            @Override
            //失败返回结果
            public void onFailure(Call call, IOException e) {
                //发送空消息
                handler.sendEmptyMessage(0x10);
            }

            @Override
            //成功放回结果
            public void onResponse(Call call, Response response) throws IOException {
                //获取结果
                String result = response.body().string();
                Log.i("tag","********"+result);
                //Gson解析
                Gson gson = new Gson();
                //解析
                UserInfo info = gson.fromJson(result, UserInfo.class);
                //发消息
                Message msg = handler.obtainMessage();
                msg.what = 0x12;
                msg.obj = info;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 找回密码
     */
    public static void forgetpass(String mailbox, final Handler handler) {
        //网络地址
        String url = App.USER_BASE+App.USER_FOR+App.USER_EMA+mailbox;
        //创建请求对象
        OkHttpClient client = new OkHttpClient();
        //创建请求
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //创建请求
        okhttp3.Call call = client.newCall(request);
        //将请求放入请求队列中
        call.enqueue(new Callback() {
            @Override
            //失败返回结果
            public void onFailure(Call call, IOException e) {
                //发送空消息
                handler.sendEmptyMessage(0x10);
            }
            @Override
            //成功放回结果
            public void onResponse(Call call, Response response) throws IOException {
                //获取结果
                String result = response.body().string();
                Log.i("tag","***#########*****"+result);
                //Gson解析
                Gson gson = new Gson();
                //解析
                UserInfo info = gson.fromJson(result, UserInfo.class);
                //发消息
                Message msg = handler.obtainMessage();
                msg.what = 0x12;
                msg.obj = info;
                handler.sendMessage(msg);
            }
        });
    }

}
