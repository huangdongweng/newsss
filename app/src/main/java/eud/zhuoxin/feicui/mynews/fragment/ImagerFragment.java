package eud.zhuoxin.feicui.mynews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.adapter.ImageAdapter;
import eud.zhuoxin.feicui.mynews.app.App;
import eud.zhuoxin.feicui.mynews.entity.ImageInfo;
import eud.zhuoxin.feicui.mynews.entity.ImageToWL;
import eud.zhuoxin.feicui.mynews.utils.HttpClientUtil;

/**
 * Created by Administrator on 2017/1/9.
 */
public class ImagerFragment extends Fragment {
    @BindView(R.id.fragment_rv)
    RecyclerView rv;
    List<ImageInfo> data = new ArrayList<>();
    ImageAdapter adapter;
    //创建handler对象
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //接消息
            switch (msg.what) {
                case App.IMAGER:
                    ImageToWL imageToWL = (ImageToWL) msg.obj;
                    data.addAll(imageToWL.getResults());
                    adapter.notifyDataSetChanged();
                    break;
                case 0x21:
                    Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //视图 =来自...布局.(布局id，容器，boolean类型的依赖(是否添加依赖))
        View view = inflater.inflate(R.layout.fragment_iamger, container, false);
        //黄油刀.绑定（本类对象，视图）
        ButterKnife.bind(this, view);
        //创建适配器
        adapter = new ImageAdapter(getContext());
        adapter.setData(data);
        //设置每一项是否有固定的大小
        rv.setHasFixedSize(true);
        //给RecyclerView设置布局管理器  参数一:显示几列  参数二: 数据的排列方式
        //网状布局管理类 = 参数（int类型的显示几列，网状布局管理类.数据的排列方式)
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // 控件.设置布局管理方法（布局管理类对象）
        rv.setLayoutManager(layoutManager);
        //控件.设置每一项动画方法（new 默认每一项动画对象）
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置适配器 （适配器对象）
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }
    @Override
    //公共的 没有返回值的 Activity创建时 参数(可以为空 的Bundle  保存实列状态)
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //访问父级.在活动创建时(保存实列状态)
        super.onActivityCreated(savedInstanceState);
        //新建一个子线程 （新建 就绪状态方法）{
        //
        // }
        new Thread(new Runnable() {
            @Override
            //共有的 没有返回值的  运行方法
            public void run() {
                HttpClientUtil.getResult(App.IMAGE_BASE, handler);
            }
        }).start();
    }
}
