package eud.zhuoxin.feicui.mynews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import eud.zhuoxin.feicui.mynews.R;
import it.sephiroth.android.library.picasso.Picasso;
/**
 *
 * Created by Administrator on 2017/1/10.
 */
public class NewsActivity extends AppCompatActivity {
    private String title;
    private String imageUrl;
    private String Url;
    @BindView(R.id.activity_news_iv)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activiity_news_wv)
    WebView wv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        //获得上个页面传递的值
        title = getIntent().getStringExtra("title");
        imageUrl = getIntent().getStringExtra("imageUrl");
        Url = getIntent().getStringExtra("Url");
        //设置ActionBar
        setSupportActionBar(toolbar);//设置actionar为toolbar
        getSupportActionBar().setTitle(title);//给toolbar赋值
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置回退按钮可用
        //设置图片
        Picasso.with(this)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(image);
        //设置WebView显示网页
        wv.loadUrl(Url);
    }

    /**
     * 设置浮动按钮的监听事件
     *
     * @param view
     */
    @OnClick(R.id.activity_news_fab)
    public void onCliek(View view) {
        switch (view.getId()) {
            case R.id.activity_news_fab:
//                Snackbar.make(view, "添加收藏", Snackbar.LENGTH_LONG).setAction("action", null).show();
                showShare();
                break;
        }
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("哈哈！！！");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(Url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(imageUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(Url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是最后的");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(Url);
        // 启动分享GUI
        oks.show(this);
    }

}
