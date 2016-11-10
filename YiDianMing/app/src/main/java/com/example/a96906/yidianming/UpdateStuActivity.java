package com.example.a96906.yidianming;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

public class UpdateStuActivity extends AppCompatActivity{
    private DBOpenHelper dbOH;
    private SQLiteDatabase sqRead,sqWrite;
    private ImageView ivUpdateTouXiang;
    private Button btnUpdateCancel,btnUpdateOld,btnUpdateData;
    private EditText etUpdateSno,etUpdateSname,etUpdateSclass,etUpdateCaoXing;
    private String strUpdateSno; //保存存在的学号
    private Bitmap updataBitmap;
    private boolean isUpdateTouXiang = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_student);

        dbOH = new DBOpenHelper(this);
        sqRead = dbOH.getReadableDatabase(); //读
        sqWrite = dbOH.getWritableDatabase();  //写

        btnUpdateCancel = (Button)findViewById(R.id.btn_UpdateCancel);
        btnUpdateOld = (Button)findViewById(R.id.btn_UpdateOld);
        btnUpdateData = (Button)findViewById(R.id.btn_UpdateData);

        ivUpdateTouXiang = (ImageView)findViewById(R.id.iv_UpdateTouXiang);
        etUpdateSno = (EditText)findViewById(R.id.et_UpdateSno);
        etUpdateSname = (EditText)findViewById(R.id.et_UpdateSname);
        etUpdateSclass = (EditText)findViewById(R.id.et_UpdateSclass);
        etUpdateCaoXing = (EditText)findViewById(R.id.et_UpdateCaoXing);

        BuKeYong();

        /**
         * 取消
         */
        btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateStuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 点击添加头像
         */
        ivUpdateTouXiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*"); //定义打开图片类型
                intent.setAction(Intent.ACTION_GET_CONTENT); //设置action为A。。。，代表图片内容
                startActivityForResult(intent,1); //得到图片返回当前页
            }
        });
        /**
         * 原始信息
         */
        btnUpdateOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(!etUpdateSno.getText().toString().equals("")) {
                        //先判断是否存在该学生
                        Cursor isExists = sqRead.query(Studnet.TABLE, new String[]{Studnet.KEY_SNO},
                                Studnet.KEY_SNO + "=?", new String[]{etUpdateSno.getText().toString()}, null, null, null);
                        if (isExists.getCount() == 0)
                            Toast.makeText(getApplicationContext(), "不存在该学生！请核对学号后再次操作", Toast.LENGTH_SHORT).show();
                        else {
                            isExists.moveToNext();
                            strUpdateSno = isExists.getString(0); // 存储找到的学号
                            Cursor c = sqRead.query(Studnet.TABLE, null, Studnet.KEY_SNO + "=?",
                                    new String[]{etUpdateSno.getText().toString()}, null, null, null);
                            c.moveToNext();
                            KeYong();
                            etUpdateSname.setText(c.getString(1));
                            etUpdateSclass.setText(c.getString(2));
                            etUpdateCaoXing.setText(c.getString(3));
                            if (c.getBlob(4) != null) {
                                ByteArrayInputStream baisTouXiang = new ByteArrayInputStream(c.getBlob(4));
                                ivUpdateTouXiang.setImageDrawable(Drawable.createFromStream(baisTouXiang, "touxiang"));
                            } else
                                ivUpdateTouXiang.setImageResource(R.mipmap.unnamed);
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "请先输入学号复原原始信息", Toast.LENGTH_SHORT).show();
                }catch (Exception e){}
            }
        });
        /**
         * 更新数据
         */
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = null;
                date = sdf.format(new java.util.Date())+"："; //获取当前时间
                ContentValues cv = new ContentValues();
                cv.put(Studnet.KEY_SNO,etUpdateSno.getText().toString());
                cv.put(Studnet.KEY_SNAME,etUpdateSname.getText().toString());
                cv.put(Studnet.KEY_SCLASS,etUpdateSclass.getText().toString());
                cv.put(Studnet.KEY_SBEIZHU,date+etUpdateCaoXing.getText().toString());
                if (isUpdateTouXiang == true) {
                    ivUpdateTouXiang.setDrawingCacheEnabled(true); //能够获取ImageView中的图片
                    cv.put(Studnet.KEY_STOUXIANG, getPicture(updataBitmap));
                    ivUpdateTouXiang.setDrawingCacheEnabled(false);
                }
                isUpdateTouXiang = false;
                sqWrite.update(Studnet.TABLE,cv,Studnet.KEY_SNO+"=?",new String[]{strUpdateSno});
                Toast.makeText(getApplicationContext(),"更新已成功！",Toast.LENGTH_SHORT).show();
                BuKeYong();
                ivUpdateTouXiang.setImageResource(R.mipmap.unnamed);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //接受用户通过其他activity返回的数据
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){ //请求为1，进行处理
            if(resultCode == RESULT_OK){ //成功得到照片返回
                ContentResolver cr = this.getContentResolver();//通过Activity的content得到ContentR。。
                Uri uri = data.getData(); //得到返回的data数据
                try {
                    //通过ContentR。。得到对应图片的Bitmap
                    updataBitmap = MediaStore.Images.Media.getBitmap(cr,uri);
                    ivUpdateTouXiang.setImageBitmap(updataBitmap); //图片放Image。。去
                    isUpdateTouXiang = true;
                }catch (Exception e){
                    Log.e("Exception",e.getMessage(),e);
                }
            }
        }
    }

    /**
     * 获取头像用于存在数据库中
     */
    //将Bitmap转换成可以用来存储的byte[]类型
    private byte[] getPicture(Bitmap bitmap){
        if(bitmap == null)
            return null;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        return baos.toByteArray();
    }

    /**
     * 只能查到学生才能用按钮
     */
    private void BuKeYong(){
        ivUpdateTouXiang.setEnabled(false);
        etUpdateSname.setEnabled(false);
        etUpdateSclass.setEnabled(false);
        etUpdateCaoXing.setEnabled(false);
        btnUpdateData.setEnabled(false);
    }
    private void KeYong(){
        ivUpdateTouXiang.setEnabled(true);
        etUpdateSname.setEnabled(true);
        etUpdateSclass.setEnabled(true);
        etUpdateCaoXing.setEnabled(true);
        btnUpdateData.setEnabled(true); //有原始数据，按钮可用
    }
}
