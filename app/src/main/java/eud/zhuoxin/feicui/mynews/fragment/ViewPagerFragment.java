package eud.zhuoxin.feicui.mynews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.adapter.PagerFragmentAdapter;

/**
 * Created by Administrator on 2017/1/9.
 *
 */

public class ViewPagerFragment extends Fragment {
    @BindView(R.id.viewpagerfragment_tab)
    TabLayout title_tab;
    @BindView(R.id.viewpagerfragment_vp)
    ViewPager news_vp;
    List<NewsFragment> data = new ArrayList<>();
    PagerFragmentAdapter adapter;
    String[] type = new String[]{NewsFragment.TYPE_TOUTIAO};

    public ViewPagerFragment() {
    }

    public ViewPagerFragment(String type[]) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        ButterKnife.bind(this, view);
        //创建适配器
        adapter = new PagerFragmentAdapter(getChildFragmentManager());
        //初始化标题栏
        initTabLayout();
        return view;
    }
    /**
     * 初始化标题栏
     */
    private void initTabLayout() {
        title_tab.addTab(title_tab.newTab().setText("头条"));
        title_tab.addTab(title_tab.newTab().setText("科技"));
        title_tab.addTab(title_tab.newTab().setText("国际"));
        title_tab.addTab(title_tab.newTab().setText("社会"));
        //添加TabLoyant的监听事件
        title_tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(news_vp));
        //添加ViewPager的监听事件
        news_vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(title_tab));


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据源
        initPagerData();


    }
    /**
     * VieewPager的数据源
     */
    private void initPagerData() {
        for (int i = 0; i < type.length; i++) {
            NewsFragment fragment = new NewsFragment(type[i]);
            data.add(fragment);
        }
        //添加数据
        adapter.setData(data);
        //添加适配器
        news_vp.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }


}
