package eud.zhuoxin.feicui.mynews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/1/12.
 * 创建数据库
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="db.db";
//    private static final String CREATE_NEWS="create table news(newsUrl text," +
//            "imageUrl text,title text,author_name text,data text";

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String spl="create table News(newsUrl text,imageUrl text,title text,author_name text,data text) ";
        db.execSQL(spl);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
