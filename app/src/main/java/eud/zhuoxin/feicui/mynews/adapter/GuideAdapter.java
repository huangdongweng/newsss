package eud.zhuoxin.feicui.mynews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */

public class GuideAdapter extends PagerAdapter {

    private List<View> data;

    public GuideAdapter(List<View> data) {
        this.data = data;
    }
    /**返回ViewPager要显示页面的数量*/
    @Override
    public int getCount() {
        return data.size();
    }
    /**判断视图是否来自View对象*/
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    /**添加下一个即将显示的View*/
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //讲数据源中的View对像添加到容器中（如果显示，如何显示）
        container.addView(data.get(position));
        return data.get(position);
    }
    /**销毁上一个页面*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }
}
