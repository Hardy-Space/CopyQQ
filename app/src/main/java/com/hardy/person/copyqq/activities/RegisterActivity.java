package com.hardy.person.copyqq.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hardy.person.copyqq.R;

/**
 * @author 马鹏昊
 * @date {2016.6.22}
 * @des 注册界面
 * @updateAuthor
 * @updateDate
 * @updateDes
 */
public class RegisterActivity extends Activity implements View.OnClickListener{
    //返回按钮
    private TextView back;
    //用户名输入框
    private EditText userNameInput;
    //密码输入框
    private EditText passwordInput;
    //注册按钮
    private Button registerButton;

    private void assignViews() {
        back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(this);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //找到各组件
        assignViews();



    }

    @Override
    public void onClick(View v) {
        //定义返回登录界面的Intent
        switch (v.getId()){
            case R.id.back:
                //返回按钮事件
                //结束当前Activity
                //回到登陆界面
                finish();
                break;
            case R.id.registerButton:
                //注册按钮事件
                //获取输入的用户名
                String name = userNameInput.getText().toString();
                //获取输入的密码
                String password = passwordInput.getText().toString();
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)){//如果输入都不为空
                    //此处用于向服务器提交注册信息进行注册
                    //从服务器返回注册结果信息
                    //如果失败则用Toast提示用户名已注册并停在这个界面继续注册，除非按back键返回
                    //如果注册成功就返回登陆界面重新登录
                    //结束当前Activity重新回到登陆界面
                    finish();
                }else{
                    //两个输入框有一个为空即提示信息不完整
                    Toast.makeText(RegisterActivity.this, "注册信息不完整", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
