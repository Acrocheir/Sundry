package com.example.a96906.yidianming;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

public class DeleteStudent extends AppCompatActivity {
    private DBOpenHelper dbOH;
    private SQLiteDatabase sqLD;
    private Button btnDeleteCancel,btnDelete,btnDelSelect;
    private EditText etDeleteSno;
    private TextView tvDeleteSname,tvDeleteSclass;
    private ImageView ivDeleteTouXiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_student);

        dbOH = new DBOpenHelper(this);
        sqLD = dbOH.getReadableDatabase();

        /**
         * 取消
         */
        btnDeleteCancel = (Button)findViewById(R.id.btn_DeleteCancel);
        btnDeleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteStudent.this,MainActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 查找
         */
        etDeleteSno = (EditText)findViewById(R.id.et_DeleteSno);
        tvDeleteSname = (TextView) findViewById(R.id.et_DeleteSname);
        tvDeleteSclass = (TextView)findViewById(R.id.tv_DeleteSclass);
        ivDeleteTouXiang = (ImageView)findViewById(R.id.iv_DeleteTouXiang);
        btnDelSelect = (Button)findViewById(R.id.btn_DelSelect);
        btnDelSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tvDeleteSname.setText(null);
                    if (etDeleteSno.getText().toString().trim().equals(""))
                        Toast.makeText(getApplicationContext(), "请输入删除学生学号！", Toast.LENGTH_SHORT).show();
                    else if (!etDeleteSno.getText().toString().trim().equals("")) {
                        Cursor c = sqLD.query(Studnet.TABLE, null, Studnet.KEY_SNO + "=?",
                                new String[]{etDeleteSno.getText().toString()}, null, null, null);
                        c.moveToNext();
                        if (!c.getString(0).equals("")) {
                            tvDeleteSname.setText(c.getString(1));
                            tvDeleteSclass.setText(c.getString(2));
                            //显示头像
                            if(c.getBlob(4)!=null) { //存有头像
                                ByteArrayInputStream baisTouXiang = new ByteArrayInputStream(c.getBlob(4));
                                ivDeleteTouXiang.setImageDrawable(Drawable.createFromStream(baisTouXiang, "touxiang"));
                            } else{
                                ivDeleteTouXiang.setImageResource(R.mipmap.unnamed);
                            }
                            Toast.makeText(getApplicationContext(), "已成功查找到学号为："+c.getString(0)+"的学生",
                                    Toast.LENGTH_SHORT).show();
                            btnDelete.setEnabled(true);
                        } else
                            Toast.makeText(getApplicationContext(), "查找失败！请核对信息后再次查找", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "查找失败！请核对信息后再次查找", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 删除
         */
        btnDelete = (Button)findViewById(R.id.btn_Delete);
        btnDelete.setEnabled(false); //先查找才能删除
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqLD.delete(Studnet.TABLE, Studnet.KEY_SNO + "=?",
                            new String[]{etDeleteSno.getText().toString()});
                    etDeleteSno.setText(null);
                    tvDeleteSname.setText(null);
                    tvDeleteSclass.setText(null);
                    ivDeleteTouXiang.setImageResource(R.mipmap.unnamed);
                    Toast.makeText(getApplicationContext(), "学生"+tvDeleteSname.getText().toString()+"的信息已成功删除！",
                            Toast.LENGTH_SHORT).show();
                    btnDelete.setEnabled(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        /*
        /**
         *输入学生学号改变时候

        etDeleteSno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etDeleteSno.setText(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        */
    }
}
