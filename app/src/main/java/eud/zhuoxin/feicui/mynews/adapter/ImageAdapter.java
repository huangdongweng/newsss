package eud.zhuoxin.feicui.mynews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.entity.ImageInfo;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Administrator on 2017/1/11.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    private List<ImageInfo> data;
    private Context context;

    public List<ImageInfo> getData() {
        return data;
    }
    public void setData(List<ImageInfo> data) {
        this.data = data;
    }
    public ImageAdapter(Context context) {
        this.context = context;
    }
    /**创建一个 ViewHolder*/
    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //布局
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_image,null);
        return  new ImageHolder(view);
    }
    /**这个RecyclerView的页数*/
    @Override
    public int getItemCount() {
        return data.size();
    }
    /**绑定ViewHolder*/
    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        ViewGroup.LayoutParams params =holder.iv.getLayoutParams();//获取imageView的参数信息
        params.height=(int)(400+Math.random()*400);//设置imageView的随机高度
        holder.iv.setLayoutParams(params);//将修改过的参数传入

            //控件赋值赋值
        Picasso.with(context)
                .load(data.get(position).getUrl())
                .error(R.mipmap.ic_launcher)
                .into(holder.iv);
    }
    /**iewHolder*/
    public class ImageHolder extends RecyclerView.ViewHolder {
            ImageView iv;
        public ImageHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.adapter_image_item);
        }
    }
}
