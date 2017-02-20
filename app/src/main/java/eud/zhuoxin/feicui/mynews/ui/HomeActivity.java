package eud.zhuoxin.feicui.mynews.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebHistoryItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.app.App;
import eud.zhuoxin.feicui.mynews.fragment.CollectFragment;
import eud.zhuoxin.feicui.mynews.fragment.ImagerFragment;
import eud.zhuoxin.feicui.mynews.fragment.NewsFragment;
import eud.zhuoxin.feicui.mynews.fragment.ViewPagerFragment;
import it.sephiroth.android.library.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, PlatformActionListener {
    private TextView tv;
    private ImageView iv;
    private ImageView iv1;

    /**
     * 当前显示的Fragment
     */
    Fragment showFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //系统控件初始化
        initSystemView();
        //设置默认显示新闻页面
        showNewsFragment();
//        ShowImaggeFragment();
    }

    /**
     * 系统控件初始化
     */
    private void initSystemView() {
        //初始化Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //添加浮动按钮的监听事件
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //设置侧滑开关
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //获取头部控件
        View headview = navigationView.getHeaderView(0);
        iv = (ImageView) headview.findViewById(R.id.imageView);
        tv = (TextView) headview.findViewById(R.id.head_main_tv);
        iv1 = (ImageView) headview.findViewById(R.id.head_img_qq);
        iv.setOnClickListener(this);
        tv.setOnClickListener(this);
        iv1.setOnClickListener(this);
    }

    //重写按下返回键的方法
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //  引入菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //菜单监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Navigation每一项的监听事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //判断当前显示的fragment是否与用户点击的fragment相同，不相同 添加，相同 吐司提示
            if (!(showFragment instanceof ViewPagerFragment)) {
                // 添加新闻
                showNewsFragment();
            } else {
                Toast.makeText(this, "无需更新", Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_gallery) {
            // 添加图片
            ShowImaggeFragment();
        } else if (id == R.id.nav_slideshow) {
            //显示收藏
            showCollectFragment();

        } else if (id == R.id.nav_share) {
            //一键分享
            showShare();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 显示新闻界面
     */
    private void showNewsFragment() {
        String[] type = new String[]{NewsFragment.TYPE_TOUTIAO, NewsFragment.TYPE_KEJI, NewsFragment.TYPE_GUOJI, NewsFragment.TYPE_SHEHUI};
        //创建ViewPager
        ViewPagerFragment vpFragment = new ViewPagerFragment(type);
        //获得任务管理器
        FragmentManager fm = getSupportFragmentManager();
        //打开事物
        FragmentTransaction ft = fm.beginTransaction();
        //添加到
        ft.replace(R.id.content_fragment_fl, vpFragment);
        //当前显示的等于新创建的
        showFragment = vpFragment;
        //提交事物
        ft.commit();
    }

    /**
     * 显示图片界面
     */
    private void ShowImaggeFragment() {
        //创建建Fragment对象
        ImagerFragment shows = new ImagerFragment();
        //获得任务管理器
        FragmentManager fm = getSupportFragmentManager();
        //打开事物
        FragmentTransaction ft = fm.beginTransaction();
        //添加到
        ft.replace(R.id.content_fragment_fl, shows);
        //当前显示的等于新创建的
        showFragment = shows;
        //提交事物
        ft.commit();
    }

    /**
     * 显示收藏界面
     */
    private void showCollectFragment() {
        //创建建Fragment对象
        CollectFragment shows = new CollectFragment();
        //获得任务管理器
        FragmentManager fm = getSupportFragmentManager();
        //打开事物
        FragmentTransaction ft = fm.beginTransaction();
        //添加到
        ft.replace(R.id.content_fragment_fl, shows);
        //当前显示的等于新创建的
        showFragment = shows;
        //提交事物
        ft.commit();
    }

    /**
     * shareSDK一键分享
     */
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    /**
     * 监听点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                if (tv.getText().equals("请先登陆")) {
                    //带返回值的跳转
                    Intent intent = new Intent(this, UserActivity.class);

                        startActivityForResult(intent, App.REQUESTCODE);
                } else {
                    //设置不可点击
                    iv.setClickable(false);
                }
                break;
            case R.id.head_main_tv:

                break;
            case R.id.head_img_qq:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.SSOSetting(false);  //设置false表示使用SSO授权方式
                qq.setPlatformActionListener(this); // 设置分享事件回调

                qq.authorize();//单独授权
                qq.showUser(null);//授权并获取用户信息
                break;
            case R.id.head_img_weixin:
//                Platform weixin = ShareSDK.getPlatform(QQ.NAME);
//                weixin.SSOSetting(false);  //设置false表示使用SSO授权方式
//                weixin.setPlatformActionListener(this); // 设置分享事件回调
//
//                weixin.authorize();//单独授权
//                weixin.showUser(null);//授权并获取用户信息
                break;
            case R.id.head_img_weibo:
//                Platform weibo = ShareSDK.getPlatform(TencentWeibo.NAME);
//                weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
//                weibo.setPlatformActionListener(this); // 设置分享事件回调
//                weibo.authorize();//单独授权
//                weibo.showUser(null);//授权并获取用户信息
                break;
        }
    }

    /**
     * 用来处理放回值的方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }else {
            String name = data.getStringExtra("name");
//            String pwd = data.getStringExtra("pwd");
            tv.setText(name);
        }

    }

/**第三方登陆*/
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        String accessToken = platform.getDb().getToken(); // 获取授权token
        String openId = platform.getDb().getUserId(); // 获取用户在此平台的ID
        final String nickname = platform.getDb().getUserName(); // 获取用户昵称
        final String urlicon = platform.getDb().getUserIcon();//获取图片

        //主线程
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //获取图片
                Picasso.with(HomeActivity.this)
                        .load(urlicon)
                        .error(R.mipmap.ic_launcher)
                        .into(iv);
                //获取用户昵称
                tv.setText(nickname);
//                HttpClientUtil.getResult();
            }
        });

//部分没有封装的字段可以通过键值获取，例如下面微信的unionid字段
//        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//        String unionid = wechat.getDb().get("unionid");
// 接下来执行您要的操作
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }
}
