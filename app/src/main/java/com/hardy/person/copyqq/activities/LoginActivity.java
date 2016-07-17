package com.hardy.person.copyqq.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardy.person.copyqq.R;
import com.hardy.person.copyqq.qq_interface.BaseUiListener;
import com.hardy.person.copyqq.utils.ConstantsHolder;
import com.hardy.person.copyqq.utils.GetApplicationContext;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 马鹏昊
 * @date {2016.6.22}
 * @des 登陆界面
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //显示头像
    private ImageView headPortrait;
    //用户名输入框
    private EditText userNameInput;
    //密码输入框
    private EditText passwordInput;
    //登录按钮
    private Button loginButton;
    //第三方qq登录按钮
    private TextView qqLogin;
    //忘记密码界面文本链接
    private TextView mForgot;
    //注册界面文本链接
    private TextView mToRegister;
    //定义程序的SharedPreference文件
    private SharedPreferences mSharedPreferences;
    //腾讯业务起源类
    public static Tencent mTencent;
    //继承自IUiListener的UiListener
    private IUiListener mUiListener;

    private void assignViews() {
        headPortrait = (ImageView) findViewById(R.id.headPortrait);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        mForgot = (TextView) findViewById(R.id.forgot);
        mForgot.setOnClickListener(this);
        mToRegister = (TextView) findViewById(R.id.toRegister);
        mToRegister.setOnClickListener(this);
        //获取程序的SharedPreference文件
        mSharedPreferences = getSharedPreferences("LastUser", MODE_PRIVATE);
        qqLogin = (TextView) findViewById(R.id.qqLogin);
        qqLogin.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //从xml文件中找到各组件
        assignViews();
        //初始化qq接口用到的相关对象
        initialTencentObjects();
        //判断是否是记住密码
        if (!isFirstLogin()) {
            //如果不是第一次登录就说明已经记住用户名和密码，直接进入自动登录上次退出时的账号
            //如果是从主界面的切换账号处打开的LoginActivity则在打开之前把“isFirstLogin”文件clear(),就不会再执行此处
            String id = mSharedPreferences.getString("openId",null);
            String expires = mSharedPreferences.getString("expires_in",null);
            String token = mSharedPreferences.getString("accessToken",null);
            if (id!=null&&token!=null){
                mTencent.setOpenId(id);
                mTencent.setAccessToken(token,expires);
            }
            Intent toMainIntent = new Intent(this, MainActivity.class);
            toMainIntent.putExtra("SourceActivity",ConstantsHolder.QQ_LOGIN);
            startActivity(toMainIntent);
            finish();
        }
    }


    private void initialTencentObjects() {
        //第一个参数是申请得到的APPId
        mTencent = Tencent.createInstance(ConstantsHolder.APP_ID, GetApplicationContext.returnApplicationContext());
        mUiListener = new BaseUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject result = (JSONObject) o;
                String openId = null;
                //有效期
                String expires = null ;
                String accessToken = null;
                try {
                    openId = result.getString("openid");
                    expires = result.getString("expires_in");
                    accessToken = result.getString("access_token");
                    mTencent.setOpenId(openId);
                    mTencent.setAccessToken(accessToken,expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //                Log.d("###", "||||" + result.toString());
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear();
                editor.putBoolean("isFirstLogin", false);
                editor.putString("openId", openId);
                editor.putString("expires_in",expires);
                editor.putString("accessToken", accessToken);
                editor.commit();
                Intent toMainIntent = new Intent(LoginActivity.this, MainActivity.class);
                toMainIntent.putExtra("SourceActivity", ConstantsHolder.QQ_LOGIN);
                startActivity(toMainIntent);
                finish();
            }

            @Override
            public void onError(UiError uiError) {
                Log.d("###", uiError.errorMessage);


            }

            @Override
            public void onCancel() {
                Log.d("###", "OnCancel!!!");

            }
        };
    }

    /*
        检查SharedPreference文件看看是否是第一次登录
    */
    private boolean isFirstLogin() {
        boolean result = mSharedPreferences.getBoolean("isFirstLogin", true);
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                //获取输入的用户名
                String name = userNameInput.getText().toString();
                //获取输入的密码
                String password = passwordInput.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    //去服务器端验证登录信息是否正确无误
                    //如果不正确则清空输入框并且提示输入密码错误

                    /*//如果正确
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    //把相关信息存入用于专门保存最新登陆者信息的SharedPreference文件里
                    //修改标签，下次直接登录进入上次登录退出时的用户
                    editor.clear();
                    editor.putBoolean("isFirstLogin", false);
                    //保存用户名
                    editor.putString("user",name);
                    //保存密码
                    editor.putString("password",password);
                    //新建一个SharedPreference文件来保存用户登录信息
                    SharedPreferences newUser = getSharedPreferences(name,MODE_PRIVATE);
                    SharedPreferences.Editor newEditor = newUser.edit();
                    newEditor.putString("user",name);
                    newEditor.putString("password",password);*/

                    //直接把用户账号信息传递给主界面，然后主界面根据账号信息去和服务器交互获取相应数据
                    Bundle userInfo = new Bundle();
                    Intent toMainIntent = new Intent(this, MainActivity.class);
                    toMainIntent.putExtra("SourceActivity",ConstantsHolder.APP_LOGIN);
                    toMainIntent.putExtras(userInfo);
                    //启动主界面
                    startActivity(toMainIntent);
                    finish();

                }
                break;
            case R.id.forgot:
                Intent toFindPasswordIntent = new Intent(this, FindPasswordActivity.class);
                startActivity(toFindPasswordIntent);
                break;
            case R.id.toRegister:
                Intent toRegisterIntent = new Intent(this, RegisterActivity.class);
                startActivity(toRegisterIntent);
                break;
            case R.id.qqLogin:
                /*//分享至QQ，不用登陆
                Bundle bundle = new Bundle();
                //必须有Url，点击后跳转的地方
                bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://www.baidu.com/");
                //标题、图片、摘要至少有一个
                bundle.putString(QQShare.SHARE_TO_QQ_TITLE,"标题：百度首页分享");
                bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
                bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY,"小明滚出去系列");
                //修改返回开发者应用按钮的显示内容，设置了不管用啊，一直是“返回”+“调用分享的应用名字”
                bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME,"返回222222");
                //开启分享至QQ
                mTencent.shareToQQ(this, bundle, mUiListener);*/

                //分享至QQ空间
                //                mTencent.shareToQzone(this,bundle,mUiListener);

                //QQ号第三方登录

                if (!mTencent.isSessionValid()) {
                    //第二个参数是可授权的列表，不填写默认也为get_user_info
                    mTencent.login(this, "all", mUiListener);
                }

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mUiListener);
    }
}
