package eud.zhuoxin.feicui.mynews.dome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import eud.zhuoxin.feicui.mynews.R;

/**
 * Created by Administrator on 2017/1/7.
 * 网络下载图片
 *
 */

public class DownloadPictureActivity extends AppCompatActivity {
    @BindView(R.id.activity_http_img)
    ImageView show;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        ButterKnife.bind(this);
        //创建异步任务
        DownloadPictureDome dpd=new DownloadPictureDome(this,show);
        dpd.execute("http://www.5857.com/wall/59842_3.html");
    }
}
