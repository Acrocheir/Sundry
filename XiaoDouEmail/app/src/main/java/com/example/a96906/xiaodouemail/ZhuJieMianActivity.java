package com.example.a96906.xiaodouemail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 96906 on 2016/11/17.
 */

public class ZhuJieMianActivity extends AppCompatActivity {
    private Button btnSendEmail,btnReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhujiemian);

        /**
         * 发送
         */
        btnSendEmail = (Button)findViewById(R.id.btn_SendEmail);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZhuJieMianActivity.this,SendActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 接收
         */
        btnReceive = (Button)findViewById(R.id.btn_ReceiveEmail);
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZhuJieMianActivity.this,ReceiveActivity.class);
                startActivity(intent);
            }
        });
    }
}
