package eud.zhuoxin.feicui.mynews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.api.UserListener;
import eud.zhuoxin.feicui.mynews.fragment.ForgetpassFragment;
import eud.zhuoxin.feicui.mynews.fragment.LoginFragment;
import eud.zhuoxin.feicui.mynews.fragment.RegisterFragment;

/**
 * Created by Administrator on 2017/1/17.
 */

public class UserActivity extends AppCompatActivity implements UserListener {
    Fragment showFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //默认显示的页面
        showLoginFragment();
    }
    /**显示登陆Fragment*/
    private void showLoginFragment(){
        //创建建Fragment对象
         LoginFragment shows = new LoginFragment();
        //获得任务管理器
        FragmentManager fm=getSupportFragmentManager();
        //打开事物
        FragmentTransaction ft=fm.beginTransaction();
        //添加到
        ft.replace(R.id.Activity_user_fl,shows);
        //当前显示的等于新创建的
        showFragment=shows;
        //提交事物
        ft.commit();
    }
    /***显示注册Fragment*/
    private void showRegisterFragment(){
        //创建建Fragment对象
        RegisterFragment shows = new RegisterFragment();
        //获得任务管理器
        FragmentManager fm=getSupportFragmentManager();
        //打开事物
        FragmentTransaction ft=fm.beginTransaction();
        //添加到
        ft.replace(R.id.Activity_user_fl,shows);
        //当前显示的等于新创建的
        showFragment=shows;
        //提交事物
        ft.commit();
    }
    /**显示找回密码Fragment*/
    private void showForgetpassFragment(){
        //创建建Fragment对象
        ForgetpassFragment shows = new ForgetpassFragment();
        //获得任务管理器
        FragmentManager fm=getSupportFragmentManager();
        //打开事物
        FragmentTransaction ft=fm.beginTransaction();
        //添加到
        ft.replace(R.id.Activity_user_fl,shows);
        //当前显示的等于新创建的
        showFragment=shows;
        //提交事物
        ft.commit();
    }
    @Override
    public void setTargetFragment(String targentFragment) {
        switch (targentFragment){
            case "register":
                showRegisterFragment();
                break;
            case "forgetpass":
                showForgetpassFragment();
                break;
            case "login":
                showLoginFragment();
                break;
        }
    }
}
