package com.example.a96906.xiaodouemail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
//import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static android.media.AudioRecord.SUCCESS;
import static android.net.NetworkInfo.DetailedState.FAILED;

/**
 * Created by 96906 on 2016/11/17.
 */

public class SendActivity extends AppCompatActivity {
    private EditText etSendTo,etSendFrom,etSendTheme,etSendContext;
    private Button btnSendCancel,btnSendOk,btnAddFuJian;
    private ProgressDialog dialog;
    private GridView gvFuJian;
    private GridViewAdapter<Attachment> adapter = null;
    private static final int SUCCESS = 1;
    private static final int FAILED = -1;
    HttpUtil util = new HttpUtil();
    Handler handler = new Handler() {


        public void handleMessage(android.os.Message msg) {
            dialog.dismiss();
            Toast.makeText(SendActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        //初始化
        etSendTo = (EditText)findViewById(R.id.et_SendTo);
        etSendFrom = (EditText)findViewById(R.id.et_SendFrom);
        etSendTheme = (EditText)findViewById(R.id.et_SendTheme);
        etSendContext = (EditText)findViewById(R.id.et_SendContext);
        btnSendCancel = (Button)findViewById(R.id.btn_SendCancel);
        btnAddFuJian = (Button)findViewById(R.id.btn_AddFuJian);
        btnSendOk = (Button)findViewById(R.id.btn_SendOk);
        gvFuJian = (GridView)findViewById(R.id.gv_FuJian);
        adapter = new GridViewAdapter<Attachment>(this);
        gvFuJian.setAdapter(adapter);

        etSendFrom.setText(MyApplication.info.getUserName());


        /**
         * 取消
         */
        btnSendCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendActivity.this,ZhuJieMianActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 添加附件
         */
        btnAddFuJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAttachment();
            }
        });
        /**
         * 发送
         */
        btnSendOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

   // private void MySend(View view){
    //    Toast.makeText(getApplicationContext(),"正在发送",Toast.LENGTH_SHORT).show();
     //   sendMail();
   // }

    /**
     * 设置邮件数据
     */
    private void sendMail(){
        MyApplication.info.setAttachmentInfos(adapter.getList());
        MyApplication.info.setFromAddress(etSendFrom.getText().toString().trim());
        MyApplication.info.setTheme(etSendTheme.getText().toString().trim());
        MyApplication.info.setTextContent(etSendContext.getText().toString().trim());
        //收件人
        String str=etSendTo.getText().toString().trim();
        String []recevers=str.split(",");
        for(int i=0;i<recevers.length;i++){
            if(recevers[i].startsWith("<")&&recevers[i].endsWith(">")){
                recevers[i]=recevers[i].substring(recevers[i].lastIndexOf("<")+1, recevers[i].lastIndexOf(">"));
            }
        }
        MyApplication.info.setReceivers(recevers);


        //发送邮件
        dialog=new ProgressDialog(this);
        dialog.setMessage("正在发送");
        dialog.show();


         //发送

        new Thread(){
            @Override
            public void run() {
                boolean flag=util.sendTextMail(MyApplication.info, MyApplication.session);
                Message msg = new Message();
                if(flag){
                    msg.what=SUCCESS;
                    handler.sendMessage(msg);
                }else{
                    msg.what=FAILED;
                    handler.sendMessage(msg);
                }
            }

        }.start();

    }

    /**
     * 添加附件
     */
    private void addAttachment() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/");
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri = null;
                    if (data != null) {
                        uri = data.getData();
                    }

                    String path = uri.getPath();
                    Attachment affInfos = Attachment.GetFileInfo(path);
                    adapter.appendToList(affInfos);
                    int a = adapter.getList().size();
                    int count = (int) Math.ceil(a / 4.0);
                    gvFuJian.setLayoutParams(new LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            (int) (94 * 1.5 * count)));
                    break;
            }
        }
    }
    /**
     * 设置邮件数据
     */
    /*
    private void SendMail(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在发送");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

                    Properties props = System.getProperties();
                    props.setProperty("mail.smtp.host","smtp.qq.com");
                    props.setProperty("mail.smtp.socketFactory.class",SSL_FACTORY);
                    props.setProperty("mail.smtp.socketFactory.fallback",
                            "false");
                    props.setProperty("mail.smtp.port", "465");
                    props.setProperty("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.auth", "true");
                    final String username = "yourusername";
                    final String password = "yourpassword";
                    Session session = Session.getDefaultInstance(props,
                            new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username,
                                            password);
                                }
                            });
                    final Message msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(username+"@qq.com"));
                    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(etSendTo.getText().toString()));
                    msg.setSubject(etSendTheme.getText().toString());
                    msg.setText(etSendContext.getText().toString());
                    msg.setSentDate(new Date());

                    Transport.send(msg);
                    handler.sendEmptyMessage(0);
                }catch (MessagingException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    */
}
