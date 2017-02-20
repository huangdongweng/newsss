package eud.zhuoxin.feicui.mynews.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.adapter.GuideAdapter;

/**
 * Created by Administrator on 2017/1/4.
 */

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private Button but;
    private ImageView iv1, iv2, iv3;
    private LayoutInflater inflater;
    private List<View> data = new ArrayList<>();
    /**
     * 判断是否是第一次运行
     */
    private boolean isFirstCome;//false表示不是第一次

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        isFirstCome = getSharedPreferences("share", MODE_PRIVATE).getBoolean("isFirstCome", false);
        if (!isFirstCome) {
            //初始化控件
            initUI();
            //初始化数据源
            initdata();
            //创建监听事件
            GuideAdapter adapter = new GuideAdapter(data);
            vp.setAdapter(adapter);
            /**改变文件中的状态*/
            //获取编辑器对象
            SharedPreferences.Editor edit = getSharedPreferences("share", MODE_PRIVATE).edit();
            edit.putBoolean("isFirstCome", true);//存数据
//            edit.putString("keys","nipkjki");
            edit.commit();//提交
            //给ViewPager设置监听事件
            vp.addOnPageChangeListener(this);
            //给skip添加监听事件
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
                    //跳转到欢迎页面
                    startActivity(intent);
                    //关页面
                    finish();
                }
            });
        } else {
            Intent intent = new Intent(this, WelcomeActivity.class);
            //跳转到欢迎页面
            startActivity(intent);
            //关页面
            finish();
        }
    }

    /**
     * 初始化控件
     */
    private void initUI() {
        vp = (ViewPager) findViewById(R.id.activity_guide_vp);
        but = (Button) findViewById(R.id.activity_guide_butt);
        iv1 = (ImageView) findViewById(R.id.activity_guide_iv1);
        iv2 = (ImageView) findViewById(R.id.activity_guide_iv2);
        iv3 = (ImageView) findViewById(R.id.activity_guide_iv3);

    }

    /**
     * 初始化数据源
     */
    private void initdata() {
        inflater = LayoutInflater.from(this);
        data.add(inflater.inflate(R.layout.activity_guide_vp1, null));
        data.add(inflater.inflate(R.layout.activity_guide_vp3, null));
        data.add(inflater.inflate(R.layout.activity_guide_vp4, null));
    }

    /**
     * 页面在滑动时会调用的方法
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 页面最终停止时会调用的方法
     */
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                iv1.setImageResource(R.drawable.shape_guide_cirk_red);
                iv2.setImageResource(R.drawable.shape_guide_cirk_gray);
                iv3.setImageResource(R.drawable.shape_guide_cirk_gray);
                but.setVisibility(View.INVISIBLE);//消失
                break;
            case 1:
                iv1.setImageResource(R.drawable.shape_guide_cirk_gray);
                iv2.setImageResource(R.drawable.shape_guide_cirk_red);
                iv3.setImageResource(R.drawable.shape_guide_cirk_gray);
                but.setVisibility(View.INVISIBLE);//消失
                break;
            case 2  :
                iv1.setImageResource(R.drawable.shape_guide_cirk_gray);
                iv2.setImageResource(R.drawable.shape_guide_cirk_gray);
                iv3.setImageResource(R.drawable.shape_guide_cirk_red);
                but.setVisibility(View.VISIBLE);//可见的
                break;
        }

    }
    /**
     * 但页面状态发生变化时
     */
    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
