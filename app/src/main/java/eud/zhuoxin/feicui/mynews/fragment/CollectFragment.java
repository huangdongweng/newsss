package eud.zhuoxin.feicui.mynews.fragment;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.adapter.NewsAdapter;
import eud.zhuoxin.feicui.mynews.db.NewsDao;
import eud.zhuoxin.feicui.mynews.entity.NewsInfo;
import eud.zhuoxin.feicui.mynews.ui.NewsActivity;
/**
 * Created by Administrator on 2017/1/12.
 * 显示收藏
 */
public class CollectFragment extends Fragment {
    private List<NewsInfo> data;
    private NewsDao newsDao;
    private NewsAdapter adapter;
    @BindView(R.id.collect_lv)
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        ButterKnife.bind(this, view);
        newsDao = new NewsDao(getContext());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //查询数据库，显示数据
        data = newsDao.check();
        adapter = new NewsAdapter(getContext());
        adapter.setData(data);
        lv.setAdapter(adapter);
        //添加监听点击事件
        //跳转到newsActivity
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View iv = view.findViewById(R.id.adapter_news_item_image_iv);
                //带值跳转
                //获取值
                String title = adapter.getItem(position).getTitle();
                String imgUrl = adapter.getItem(position).getImageUrl();
                String Url = adapter.getItem(position).getUrl();
                Intent intent = new Intent(getContext(), NewsActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("imageUrl", imgUrl);
                intent.putExtra("Url", Url);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), iv, "newsImage").toBundle());
            }
        });
        //设置长按事件   删除数据
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //显示弹出对话框
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("是否删除");
                //设置积极按钮    参数一:按钮的名字   参数二:按钮的监听事件
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击弹出对话框的确定按钮，将该数据从数据库中删除
                        newsDao.delete(adapter.getData().get(position));
                        data.remove(adapter.getData().get(position));
                        adapter.notifyDataSetChanged();
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
