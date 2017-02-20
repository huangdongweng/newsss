package eud.zhuoxin.feicui.mynews.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public abstract class BaseBaseAdapter<T> extends BaseAdapter {
    List<T> data=new ArrayList<>();
    public Context context;
    public LayoutInflater inflater;
    public BaseBaseAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
//获取数据源的大小
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
