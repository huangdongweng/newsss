package eud.zhuoxin.feicui.mynews.dome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import eud.zhuoxin.feicui.mynews.R;

/**
 * Created by Administrator on 2017/1/7.
 * Fragment 碎片
 *  使用方法
 *      1.静态添加
 *          1）创建Fragment
 *          2)在activiry管理的布局中，添加Fragment
 *          3)Activity管理布局
 *
 *
 *      2.动态添加
 *
 *
 */

public class FragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentdemo);

    }
}
