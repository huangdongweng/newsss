package eud.zhuoxin.feicui.mynews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.entity.UserInfo;
import eud.zhuoxin.feicui.mynews.ui.HomeActivity;
import eud.zhuoxin.feicui.mynews.ui.UserActivity;
import eud.zhuoxin.feicui.mynews.utils.UserManage;

/**
 * Created by Administrator on 2017/1/18.
 */

public class ForgetpassFragment extends Fragment {
    @BindView(R.id.fragment_forgetpass_mailbox)
    EditText milbox;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 0x12:
                        UserInfo info = (UserInfo) msg.obj;
                        switch (info.getData().getResult()) {
                            case 0:
                                Toast.makeText(getContext(), "为发送邮箱成功", Toast.LENGTH_LONG).show();

                                break;
                            case -1:
                                Toast.makeText(getContext(), "发送失败（该邮箱未注册）)", Toast.LENGTH_LONG).show();
                                break;
                            case -2:
                                Toast.makeText(getContext(), "发送失败（邮箱不存在或被封号）", Toast.LENGTH_LONG).show();
                                break;
                        }
                        break;
                    case 0x10:
                        Toast.makeText(getContext(), "请检查网络链接设置", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgetpass, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.fragment_forgetpass_but)
    public void onClick(View view) {
        final String mailt = milbox.getText().toString().trim();
        if ( TextUtils.isEmpty(mailt)) {
            Toast.makeText(getContext(), "请输入完整的数据", Toast.LENGTH_LONG).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserManage.forgetpass( mailt, handler);
                }
            }).start();
        }
    }
        }
