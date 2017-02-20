package eud.zhuoxin.feicui.mynews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.base.BaseBaseAdapter;
import eud.zhuoxin.feicui.mynews.entity.NewsInfo;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Administrator on 2017/1/9.
 */

public class NewsAdapter extends BaseBaseAdapter<NewsInfo> {

    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_newsfragment_item, null);
            holder=new NewsHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.adapter_news_item_image_iv);
            holder.title = (TextView) convertView.findViewById(R.id.adapter_news_item_title_tv);
            holder.author_name = (TextView) convertView.findViewById(R.id.adapter_news_item_author_name_tv);
            holder.date = (TextView) convertView.findViewById(R.id.adapter_news_item_data_tv);
            //标记
            convertView.setTag(holder);
        } else {
            holder = (NewsHolder) convertView.getTag();
        }
        //获得数据
        final NewsInfo info=getItem(position);
        //给控件赋值
        holder.title.setText(info.getTitle());
        holder.author_name.setText(info.getAuthor_name());
        holder.date.setText(info.getDate());
        //给图片fuz
        Picasso .with(context)
                .load(info.getImageUrl())//去哪加载图片
                .error(R.mipmap.ic_launcher)//图片加载失败设默认图片
                .into(holder.image);//加载出来图片之后赋值的控件
        return convertView;
    }
    class NewsHolder {
        ImageView image;
        TextView title;
        TextView author_name;
        TextView date;
    }
}
