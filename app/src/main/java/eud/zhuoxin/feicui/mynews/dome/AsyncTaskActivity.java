package eud.zhuoxin.feicui.mynews.dome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import eud.zhuoxin.feicui.mynews.R;

/**
 * Created by Administrator on 2017/1/5.
 * \异步任务对象2
 */

public class AsyncTaskActivity extends AppCompatActivity {
    private ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        pb = (ProgressBar) findViewById(R.id.activity_asynctask_pb);
        //创建异步任务对象，并启动异步任务，让异步任务帮我们更新UI
        AsyncTaskDome task = new AsyncTaskDome(pb, this);
        task.execute();//执行

    }
}
