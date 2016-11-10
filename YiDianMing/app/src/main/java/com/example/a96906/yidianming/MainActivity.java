package com.example.a96906.yidianming;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Intent intent1,intent2,intent3,intent4; //跳转
    private DBOpenHelper dbOH;
    private SQLiteDatabase sqLD;
    private Button btnDianMing,btnAddStudent,btnDeleteStudent,btnUpdateStu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbOH = new DBOpenHelper(this);
        sqLD = dbOH.getReadableDatabase();
        final Cursor c = sqLD.query(Studnet.TABLE,null,null,null,null,null,null); //数据库数据在C中

        /**
         * 点名
         */
        btnDianMing = (Button)findViewById(R.id.btn_DianMing);
        btnDianMing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c.getCount()==0)
                    Toast.makeText(getApplicationContext(),"还没有录入学生数据，无法进行点名！",Toast.LENGTH_SHORT).show();
                else {
                    intent1 = new Intent(MainActivity.this, DianMingActivity.class);
                    startActivity(intent1);
                }
            }
        });
        /**
         * 添加学生
         */
        btnAddStudent = (Button)findViewById(R.id.btn_AddStu);
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2 = new Intent(MainActivity.this,AddStuActivity.class);
                startActivity(intent2);
            }
        });
        /**
         * 更新数据
         */
        btnUpdateStu = (Button)findViewById(R.id.btn_UpdateStu);
        btnUpdateStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c.getCount()==0)
                    Toast.makeText(getApplicationContext(),"还没有录入学生数据，无法进行数据更新！",Toast.LENGTH_SHORT).show();
                else {
                    intent4 = new Intent(MainActivity.this, UpdateStuActivity.class);
                    startActivity(intent4);
                }
            }
        });
        /**
         * 查找或删除学生
         */
        btnDeleteStudent = (Button)findViewById(R.id.btnDeleteStu);
        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c.getCount()==0)
                    Toast.makeText(getApplicationContext(),"还没有录入学生数据，无法进行删除操作！",Toast.LENGTH_SHORT).show();
                else {
                    intent3 = new Intent(MainActivity.this, DeleteStudent.class);
                    startActivity(intent3);
                }
            }
        });
    }
}
