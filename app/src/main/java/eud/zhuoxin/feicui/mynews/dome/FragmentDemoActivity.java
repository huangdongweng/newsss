package eud.zhuoxin.feicui.mynews.dome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import eud.zhuoxin.feicui.mynews.R;

/**
 * Created by Administrator on 2017/1/7.
 */

public class FragmentDemoActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.dome_fragment);
    }


    @OnCheckedChanged({R.id.dome_home_rb,R.id.dome_search_rb,R.id.dome_my_rb,R.id.dome_tuan_rb})
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
