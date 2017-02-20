package eud.zhuoxin.feicui.mynews.fragment;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.adapter.NewsAdapter;
import eud.zhuoxin.feicui.mynews.api.HttpClientListener;
import eud.zhuoxin.feicui.mynews.app.App;
import eud.zhuoxin.feicui.mynews.db.NewsDao;
import eud.zhuoxin.feicui.mynews.entity.NewsInfo;
import eud.zhuoxin.feicui.mynews.entity.NewsToJuhe;
import eud.zhuoxin.feicui.mynews.ui.NewsActivity;
import eud.zhuoxin.feicui.mynews.utils.HttpClientUtil;

/**
 * Created by Administrator on 2017/1/9.
 */

public class NewsFragment extends Fragment {
    public static final String TYPE_TOUTIAO = "top";
    public static final String TYPE_KEJI = "keji";
    public static final String TYPE_GUOJI = "guoji";
    public static final String TYPE_SHEHUI = "shehui";
    public String type = "top";
//    private Context context;
    @BindView(R.id.newsfragment_lv)
    ListView listView ;
    List<NewsInfo> data = new ArrayList<>();
    private NewsAdapter adapter;
    private NewsDao newsDao;
    //下拉刷新
    @BindView(R.id.newsfragment_srl)
    SwipeRefreshLayout refresh;
    //获得数据
    public NewsFragment(String type) {
        this.type = type;
    }
    /**
     * 接消息
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (refresh.isRefreshing()) {//如果progressbar当前正在显示
                refresh.setRefreshing(false);//设置progressbar隐藏
                Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();
            }
            switch (msg.what) {
                case App.SUCCEED:
                    adapter.notifyDataSetChanged();
                    break;
                case App.PLILER:
                    Toast.makeText(getContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case App.EXCEPTION:
                    Toast.makeText(getContext(), ((Exception) msg.obj).toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    /**
     * 网络访问的回调接口
     */
    HttpClientListener listenener = new HttpClientListener() {
        //成功
        @Override
        public void getResultsucced(String result) {
            //解析下载数据
            //创建Gson解析
            Gson gson = new Gson();
            //解析
            NewsToJuhe juhe = gson.fromJson(result, NewsToJuhe.class);
            //获得解析结果
            List<NewsToJuhe.NewsData> newsDatas = juhe.getResult().getData();
            //遍历
            for (NewsToJuhe.NewsData info : newsDatas) {
                //添加数据到集合
                data.add(new NewsInfo(info.getTitle(), info.getDate(), info.getAuthor_name(),
                        info.getUrl(), info.getThumbnail_pic_s()));
            }
            //发消息
            Message msg = handler.obtainMessage();
            msg.what = App.SUCCEED;
            handler.sendMessage(msg);
//            handler.sendEmptyMessage(App.SUCCEED);
        }

        //失败
        @Override
        public void getResultFailer(String result) {
            Message message = handler.obtainMessage();
            message.what = App.PLILER;
            message.obj = result;
            handler.sendMessage(message);
        }
        //异常
        @Override
        public void getResulrException(Exception e) {
            Message message = handler.obtainMessage();
            message.what = App.EXCEPTION;
            message.obj = e;
            handler.sendMessage(message);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
//        context = getActivity();
        ButterKnife.bind(this, view);
        //设置下拉刷新
        //设置下拉刷新的color

        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorgray);
        //下拉刷新数据
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //初始化数据源
                initListData();
            }
        });
        return view;
    }

    /**
     * 初始化数据源
     */
    private void initListData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取服务器端数据
                    HttpClientUtil.getResult(new URL(App.BASE_URL + type + App.APP_KRY), listenener);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsDao = new NewsDao(getContext());
        //初始化数据源
        initListData();
        //创建适配器
        adapter = new NewsAdapter(getContext());
        //添加数据
        adapter.setData(data);
        //添加适配器
        listView.setAdapter(adapter);
        //listView的监听点击事件事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View iv = view.findViewById(R.id.adapter_news_item_image_iv);
                //带值跳转
                //获取值
                String title = adapter.getItem(i).getTitle();
                String imgUrl = adapter.getItem(i).getImageUrl();
                String Url = adapter.getItem(i).getUrl();
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("imageUrl", imgUrl);
                intent.putExtra("Url", Url);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), iv, "newsImage").toBundle());
            }
        });
        //listView的监听长按事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //显示弹出对话框
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("是否收藏");
//                builder.setMessage("------");//设置信息
//                builder.setView()//设置布局
//                builder.setIcon()//设置图标
                //设置积极按钮    参数一:按钮的名字   参数二:按钮的监听事件
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean b = newsDao.add(adapter.getItem(position));
                        if (b) {
                            Toast.makeText(getContext(), "收藏成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "已收藏该内容，不能重复", Toast.LENGTH_LONG).show();
                        }
                        //让对话框消失
                        dialog.dismiss();
                    }
                });
                //设置消级按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                //创建对话框并显示
                builder.create().show();
                //true 中断点击事件继续向下传递   只响应长按事件
                //false 不中断     响应长按事件之后进而响应点击事件
                return true;
            }
        });
    }
}
