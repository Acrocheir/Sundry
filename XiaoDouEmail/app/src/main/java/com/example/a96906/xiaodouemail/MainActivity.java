package com.example.a96906.xiaodouemail;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etUserName,etPassword;
    private Button btnLogin;
    private SharedPreferences sp; //轻型的Android数据存储方式，它的本质是基于XML文件存储key-value键值对数据，通常用来存储一些简单的配置信息。
    private ProgressDialog pDialog;

    /**
     * 与其他线程协同工作，接收其他线程的消息并通过接收到的消息更新主UI线程的内容
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(MyApplication.session == null){
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),"账号或密码错误",
                        Toast.LENGTH_SHORT).show();
            }else{
                pDialog.dismiss();
                Intent intent = new Intent(MainActivity.this,ZhuJieMianActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(),"登录成功！",
                        Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName = (EditText)findViewById(R.id.et_UserName);
        etPassword = (EditText)findViewById(R.id.et_Password);
        btnLogin = (Button)findViewById(R.id.btn_Login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmail();
            }
        });
    }

    /**
     * 登录邮箱
     */
    private void loginEmail(){
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(getApplicationContext(),"地址不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(),"密码不能为空！",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //校验邮箱格式
        if(!EmailFormatUtil.emailFormat(userName)){
            Toast.makeText(getApplicationContext(),"邮箱格式不正确",Toast.LENGTH_SHORT).show();
        }else{
            String host = "smtp."+userName.substring(userName.lastIndexOf("@")+1);
            MyApplication.info.setMailServerHost(host);
            MyApplication.info.setMailServerPort("465");
            MyApplication.info.setUserName(userName);
            MyApplication.info.setPassword(password);
            MyApplication.info.setValidate(true);

            //进度条
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("正在登录，请稍后...");
            pDialog.show();

            //访问网络
            new Thread(){
                @Override
                public void run(){
                    //登入操作
                    HttpUtil util = new HttpUtil();
                    MyApplication.session = util.Login();
                    Message message = handler.obtainMessage();
                    message.sendToTarget();
                }
            }.start();
        }
    }
}
