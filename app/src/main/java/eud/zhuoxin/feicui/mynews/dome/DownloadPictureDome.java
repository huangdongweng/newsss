package eud.zhuoxin.feicui.mynews.dome;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/7.
 *
 */

public class DownloadPictureDome extends AsyncTask<String, Integer, Bitmap> {
        private Context context;
        private ImageView show;

    public DownloadPictureDome(Context context, ImageView show) {
        this.context = context;
        this.show = show;
    }

    /**
     *  异步任务后台执行的方法
     *      是子线程执行的方法，可以做耗时的操作
     * @param strings
     * @return
     */
    @Override
    protected Bitmap doInBackground(String... strings) {
        HttpURLConnection conn=null;
        try {
            //打开链接
            conn = (HttpURLConnection) new URL(strings[0]).openConnection();
            conn.setRequestMethod("GET");//设置请求方式
            conn.setReadTimeout(10000);//设置读取超时
            conn.setConnectTimeout(8000);//设置链接超时
//            conn.setRequestProperty();//设置请求头信息
            //网络链接
            conn.connect();
            if (conn.getResponseCode()==200){//如果状态码等于200，链接成功
                Bitmap bitmap= BitmapFactory.decodeStream(conn.getInputStream());
                return bitmap;

            }else {

            }
        } catch (IOException e) {

        }finally {

            if (conn!=null){
                conn.disconnect();//关闭连接
            }
        }


        return null;
    }
    /**
     *  用来处理耗时操作结果的方法
     *      当后台耗时操作执行完之后，来处理结果
     *
     * @param bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap!=null){
            show.setImageBitmap(bitmap);
        }else {
            show.setImageResource(android.R.mipmap.sym_def_app_icon);
        }


    }
}
