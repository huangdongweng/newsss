package eud.zhuoxin.feicui.mynews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.api.HttpClientListener;
import eud.zhuoxin.feicui.mynews.app.App;
import eud.zhuoxin.feicui.mynews.utils.HttpClientUtil;

/**
 * Created by Administrator on 2017/1/5.
 * 测试类
 */

public class TestActivity extends AppCompatActivity{
    public static final String TYPE_TOP="top";
    private HttpClientListener listener =new HttpClientListener() {
        @Override
        public void getResultsucced(String result) {
            //打印
            System.out.println(result);

        }

        @Override
        public void getResultFailer(String result) {
            Toast.makeText(TestActivity.this,result,Toast.LENGTH_LONG).show();

        }

        @Override
        public void getResulrException(Exception e) {

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClientUtil.getResult(new URL(App.BASE_URL+TYPE_TOP+App.APP_KRY),listener);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
}
