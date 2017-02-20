package eud.zhuoxin.feicui.mynews.fragment;

import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import eud.zhuoxin.feicui.mynews.R;
import eud.zhuoxin.feicui.mynews.app.App;
import eud.zhuoxin.feicui.mynews.entity.UserInfo;
import eud.zhuoxin.feicui.mynews.ui.UserActivity;
import eud.zhuoxin.feicui.mynews.utils.UserManage;
/**
 * Created by Administrator on 2017/1/16.
 */
public class LoginFragment extends Fragment {
    @BindView(R.id.fragment_username_et)
    EditText name;
    @BindView(R.id.fragment_pwd_et)
    EditText pwd;
    private UserActivity activity;
    private String username;
    private String userpwd;
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
                                    Toast.makeText(getContext(), "正常登陆", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("name", username);
                                    intent.putExtra("pwd",userpwd);
                                    getActivity().setResult(App.RESULTCODE,intent);
                                    //当前Activity消失
                                    getActivity().finish();
                                    break;
                                case -1:
                                    Toast.makeText(getContext(), "用户名或密码错误", Toast.LENGTH_LONG).show();
                                    break;
                                case -2:
                                    Toast.makeText(getContext(), "限制登陆(禁言,封IP)", Toast.LENGTH_LONG).show();
                                    break;
                                case -3:
                                    Toast.makeText(getContext(), "限制登陆(异地登陆等异常)", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }else {
                            Toast.makeText(getContext(), "登陆失败", Toast.LENGTH_LONG).show();
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
        View view = inflater.inflate(R.layout.fragment_landing, container, false);
        //绑定视图
        ButterKnife.bind(this, view);
        return view;
    }
    /***
     * 监听点击事件
     *
     * @param view
     */
    @OnClick({R.id.fragment_denglu_but, R.id.activity_zhuce_tv, R.id.fragment_wangji_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_denglu_but:
                username = name.getText().toString().trim();
                userpwd = pwd.getText().toString().trim();
                //判断用户输入是否合法
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpwd)) {
                    Toast.makeText(getContext(), "请输入完整的用户名和密码", Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserManage.login(username, userpwd, handler);
                        }
                    }).start();
                }
                break;
            case R.id.activity_zhuce_tv://注册
                activity= (UserActivity) getActivity();
                activity.setTargetFragment("register");
                break;
            case R.id.fragment_wangji_tv://密码找回
                activity= (UserActivity) getActivity();
                activity.setTargetFragment("forgetpass");
                break;
        }
    }
}
