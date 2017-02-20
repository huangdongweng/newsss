package eud.zhuoxin.feicui.mynews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eud.zhuoxin.feicui.mynews.entity.NewsInfo;

/**
 * Created by Administrator on 2017/1/12.
 * 封装 数据库操作的方法
 */

public class NewsDao {
    private MyOpenHelper helper;
    private SQLiteDatabase db;
    public NewsDao(Context context) {
        helper = new MyOpenHelper(context);
    }
    /**
     * 获取数据库对象
     */
    private void init() {
        //获取一个刻读写的数据库
//        db = helper.getWritableDatabase();
        db=helper.getReadableDatabase();
    }
    /**
     * 增
     */
    public boolean add(NewsInfo info) {
        boolean isExist = isNewsExist(info);
        if (isExist) {
            db.close();
            return false;//添加失败
        } else {
            ContentValues values = new ContentValues();
            values.put("newsUrl", info.getUrl());
            values.put("imageUrl", info.getImageUrl());
            values.put("title", info.getTitle());
            values.put("data", info.getDate());
            values.put("author_name", info.getAuthor_name());
            db.insert("News", null, values);
            //关闭数据库
            db.close();
            return true;//添加成功
        }
    }
    /**
     * 删
     */
    public void delete(NewsInfo info) {
        init();
        String newsYRL = info.getUrl();
        //根据newsYRL进行数据删除
        db.delete("News", "newsUrl=?", new String[]{info.getUrl()});
        db.close();
    }
    /**
     * 改
     */
    public void alter() {

    }

    /**
     * 查
     */
    public List<NewsInfo> check() {
        init();
        List<NewsInfo> list = new ArrayList<>();
        Cursor cursor = db.query("News", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String newsUrl = cursor.getString(cursor.getColumnIndex("newsUrl"));
            String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String author_name = cursor.getString(cursor.getColumnIndex("author_name"));
            String data = cursor.getString(cursor.getColumnIndex("data"));
            NewsInfo newsInfo = new NewsInfo(title, data, author_name, newsUrl, imageUrl);
            list.add(newsInfo);
        }
        return list;
    }
    //判断是否存在
    public boolean isNewsExist(NewsInfo info) {
        init();
        Cursor cursor = db.query("News", null, "newsUrl=?", new String[]{info.getUrl()}, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
}
