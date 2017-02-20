package eud.zhuoxin.feicui.mynews.app;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/1/5.
 * 全局唯一，并且优先于所有人初始化
 */

public class App extends Application {
    //网络访问接口
    //静态常量全部大写
    public static final String BASE_URL = "http://v.juhe.cn/toutiao/index?type=";
    public static final String APP_KRY = "&key=db3cab4bb2c89b9bb03ad01e5f1c9143";
    //图片接口
    public static final String IMAGE_BASE = "http://gank.io/api/data/福利/10/1";
    //用户
    public static final String USER_BASE ="http://118.244.212.82:9092/newsClient/";
    //登陆
    public static final String USER_LOG="user_login?ver=2000";
    public static final String USER_UID="&uid=";
    public static final String USER_PWD="&pwd=";
    public static final String USER_DEV="&device=0";
    //注册
    public static final String USER_REG="user_register?ver=2000";


    public static final String USER_EMA="&email=";
    //找回密码
    public static final String USER_FOR="user_forgetpass?ver=2000";

    //带返回值的跳转的请求码和响应码
    public static final int REQUESTCODE=0x20;
    public static final int RESULTCODE=0X21;
    //接口回调状态
    public static final int SUCCEED=0x10;
    public static final int PLILER=0x12;
    public static final int EXCEPTION=0x14;
    public static final int IMAGER=0x16;

    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this,"1ac894124ba86");
    }
}
