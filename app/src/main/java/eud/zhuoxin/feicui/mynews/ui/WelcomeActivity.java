package eud.zhuoxin.feicui.mynews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import eud.zhuoxin.feicui.mynews.R;

/**
 * Created by Administrator on 2017/1/16.
 */
public class WelcomeActivity extends AppCompatActivity implements Animation.AnimationListener{
    //声明
    private ImageView logo;
    private Animation anim;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomes);
        //初始化
        logo= (ImageView) findViewById(R.id.welcome_iv);
        anim= AnimationUtils.loadAnimation(this,R.anim.animation_welcome_alpha);
        //设置动画停留在结束位置
        anim.setFillAfter(true);
        //设置动画的监听事件
        anim.setAnimationListener(this);
        logo.startAnimation(anim);

    }
    /**动画开始时触发*/
    @Override
    public void onAnimationStart(Animation animation) {

    }
    /***动画结束时触发*/
    @Override
    public void onAnimationEnd(Animation animation) {
        //跳到Home页面
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
        //关页面
        finish();
    }
    /**动画重复时触发*/
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}