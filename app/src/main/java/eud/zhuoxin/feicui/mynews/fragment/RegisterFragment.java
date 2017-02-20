package eud.zhuoxin.feicui.mynews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import eud.zhuoxin.feicui.mynews.ui.UserActivity;
import eud.zhuoxin.feicui.mynews.utils.UserManage;

/**
 * Created by Administrator on 2017/1/16.
 */
public class RegisterFragment extends Fragment {
    @BindView(R.id.fragment_register_name)
    EditText name;
    @BindView(R.id.fragment_register_pwd)
    EditText pwd;
    @BindView(R.id.fragment_register_mailbox)
    EditText milbox;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 0x12:
                        UserInfo info = (UserInfo) msg.obj;
                        if (info.getData() != null) {
                            switch (info.getData().getResult()) {
                                case 0:
                                    Toast.makeText(getContext(), "正常注册", Toast.LENGTH_LONG).show();
                                    //显示LoginFragment
                                    UserActivity activity = (UserActivity) getActivity();
                                    activity.setTargetFragment("login");

                                    break;
                                case -1:
                                    Toast.makeText(getContext(), "服务器不允许注册(用户数量已满)", Toast.LENGTH_LONG).show();
                                    break;
                                case -2:
                                    Toast.makeText(getContext(), "用户名重复", Toast.LENGTH_LONG).show();
                                    break;
                                case -3:
                                    Toast.makeText(getContext(), "邮箱重复", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(getContext(), "注册失败", Toast.LENGTH_LONG).show();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fragment_register_but)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_but:
                Toast.makeText(getContext(), "*****", Toast.LENGTH_LONG).show();
                final String names = name.getText().toString().trim();
                final String pwds = pwd.getText().toString().trim();
                final String mailt = milbox.getText().toString().trim();
                if (TextUtils.isEmpty(names) || TextUtils.isEmpty(pwds) || TextUtils.isEmpty(mailt)) {
                    Toast.makeText(getContext(), "请输入完整的数据", Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserManage.register(names, pwds, mailt, handler);
                        }
                    }).start();
                }
                break;
        }
    }
}
