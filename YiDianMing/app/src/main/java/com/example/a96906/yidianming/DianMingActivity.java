package com.example.a96906.yidianming;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;

public class DianMingActivity extends AppCompatActivity {
    private DBOpenHelper dbOH;
    private SQLiteDatabase sqRead,sqWrite;
    private RadioGroup rgXuanKuang;
    private Button btnUp,btnSave,btnNext;
    private TextView tvSno,tvSname,tvSclass,etCaoXing,etCXBeiZhu;
    private RadioButton cbZaiQin,cbChiDao,cbZaoTui,cbKuangKe,cbBingJia,cbBeiZhu;
    private EditText etBeiZhu;
    private ImageView ivTouXiang;
    private byte[] bytesTouXiang; //存放从数据库中取出的头像
    private String strCaoXing; //获取当前学生的操行情况。系统时间
    private int index = 1; //判断数据库当前行数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dianming_student);

       // sqInsert();

        dbOH = new DBOpenHelper(this);
        sqRead = dbOH.getReadableDatabase(); //读
        sqWrite = dbOH.getWritableDatabase();  //写
        // 数据库数据以学号排序放在C中
        final Cursor c = sqRead.query(Studnet.TABLE,null,null,null,null,null,Studnet.KEY_SNO);

        btnUp = (Button)findViewById(R.id.btn_Up);
        btnSave = (Button)findViewById(R.id.btn_Save);
        btnNext = (Button)findViewById(R.id.btn_Next);

        tvSno = (TextView)findViewById(R.id.et_Sno);
        tvSname = (TextView)findViewById(R.id.et_Sname);
        tvSclass = (TextView)findViewById(R.id.et_Sclass);
        etCaoXing = (TextView) findViewById(R.id.et_Caoxing);
        etCXBeiZhu = (TextView)findViewById(R.id.tv_CXBeiZhu);
        etBeiZhu = (EditText)findViewById(R.id.et_BeiZhu);
        ivTouXiang = (ImageView)findViewById(R.id.iv_TouXiang);

        //单选框
        rgXuanKuang = (RadioGroup)findViewById(R.id.rg_XuanKuang);
        cbZaiQin = (RadioButton)findViewById(R.id.cb_ZaiQin);
        cbChiDao = (RadioButton)findViewById(R.id.cb_ChiDao);
        cbZaoTui = (RadioButton)findViewById(R.id.cb_ZaoTui);
        cbKuangKe = (RadioButton)findViewById(R.id.cb_KuangKe);
        cbBingJia = (RadioButton)findViewById(R.id.cb_BingJia);
        cbBeiZhu = (RadioButton)findViewById(R.id.cb_BeiZhu);

        etBeiZhu.setEnabled(false); //一开始备注框不能用
        //点击进入点名页时候显示第一行数据
        c.moveToNext();
        tvSno.setText(c.getString(0));
        tvSname.setText(c.getString(1));
        tvSclass.setText(c.getString(2));
        etCaoXing.setText(c.getString(3));
        //显示头像
        if(c.getBlob(4)!=null) { //存有头像
            ByteArrayInputStream baisTouXiang = new ByteArrayInputStream(c.getBlob(4));
            ivTouXiang.setImageDrawable(Drawable.createFromStream(baisTouXiang, "touxiang"));
        } else{
            ivTouXiang.setImageResource(R.mipmap.unnamed);
        }
        btnUp.setEnabled(false);  //一开始就为第一个学生，关掉"上一个"按钮
        strCaoXing = etCaoXing.getText().toString(); //获取已经有的操行情况

        /**
         * 上一个按钮
         */
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.moveToPrevious();
                tvSno.setText(c.getString(0));
                tvSname.setText(c.getString(1));
                tvSclass.setText(c.getString(2));
                //etCaoXing.setText(c.getString(3));
                //显示头像
                if(c.getBlob(4)!=null) {
                    ByteArrayInputStream baisTouXiang = new ByteArrayInputStream(c.getBlob(4));
                    ivTouXiang.setImageDrawable(Drawable.createFromStream(baisTouXiang, "touxiang"));
                } else{
                    ivTouXiang.setImageResource(R.mipmap.unnamed);
                }
                //为了实时显示操行情况
                Cursor cCaoXing = sqRead.query(Studnet.TABLE, new String[]{Studnet.KEY_SBEIZHU},
                        Studnet.KEY_SNO + "=?", new String[]{tvSno.getText().toString()}, null, null, null);
                cCaoXing.moveToNext();
                etCaoXing.setText(cCaoXing.getString(0));
                index--; //显示了一行下标+1
                btnNext.setEnabled(true); //打开“下一个”按钮
                strCaoXing = etCaoXing.getText().toString(); //获取已经有的操行情况
                rgXuanKuang.clearCheck(); //清空选框
                if(index==1){
                    Toast.makeText(getApplicationContext(),"此学生为第一位学生！",
                            Toast.LENGTH_SHORT).show();
                    btnUp.setEnabled(false);
                }
            }
        });
        /**
         * 保存按钮
         */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues(); //存放要更新的属性及值
                values.put(Studnet.KEY_SBEIZHU,etCaoXing.getText().toString()+" "+
                        etCXBeiZhu.getText().toString()); //把数据库中备注更新为当前操行情况框中的值
                sqWrite.update(Studnet.TABLE,values,Studnet.KEY_SNO+"=?",
                        new String[]{tvSno.getText().toString()});
                String str = etCXBeiZhu.getText().toString();
                etBeiZhu.setText(null); //清空备注里面的
                etCXBeiZhu.setText(str); //因为备注在清空时候，操行备注会跟着清空
                Toast.makeText(getApplicationContext(),"保存成功！",Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * 下一个按钮
         */
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==c.getCount()) {
                Toast.makeText(getApplicationContext(),"此学生已为最后一名学生！",
                        Toast.LENGTH_SHORT).show();
                btnNext.setEnabled(false);
            }else {
                    c.moveToNext(); //下一行
                    tvSno.setText(c.getString(0));
                    tvSname.setText(c.getString(1));
                    tvSclass.setText(c.getString(2));
                    //显示头像
                    if(c.getBlob(4)!=null) {
                        ByteArrayInputStream baisTouXiang = new ByteArrayInputStream(c.getBlob(4));
                        ivTouXiang.setImageDrawable(Drawable.createFromStream(baisTouXiang, "touxiang"));
                    } else{
                        ivTouXiang.setImageResource(R.mipmap.unnamed);
                    }
                    //为了实时显示操行情况
                    Cursor cCaoXing = sqRead.query(Studnet.TABLE, new String[]{Studnet.KEY_SBEIZHU},
                            Studnet.KEY_SNO + "=?", new String[]{tvSno.getText().toString()}, null, null, null);
                    cCaoXing.moveToNext();
                    etCaoXing.setText(cCaoXing.getString(0));
                    index++; //显示了一行下标+1
                    btnUp.setEnabled(true); //打开“上一个”按钮
                    strCaoXing = etCaoXing.getText().toString(); //获取已经有的操行情况
                   // noChecked(); //CheckBox清空
                    rgXuanKuang.clearCheck();
                }
            }
        });
        /**
         * RadioButton 选中事件
         */
        rgXuanKuang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = "";
                String strChecked = "";
                //在勤
                if(checkedId==cbZaiQin.getId()) {
                    date = sdf.format(new java.util.Date())+"："; //获取当前时间
                    etBeiZhu.setEnabled(false);
                    strChecked = cbZaiQin.getText().toString();
                }
                //迟到
                else if(checkedId==cbChiDao.getId()) {
                    date = sdf.format(new java.util.Date())+"：";
                    etBeiZhu.setEnabled(false);
                    strChecked = cbChiDao.getText().toString();
                }
                //早退
                else if(checkedId==cbZaoTui.getId()) {
                    date = sdf.format(new java.util.Date())+"："; //获取当前时间
                    etBeiZhu.setEnabled(false);
                    strChecked = cbZaoTui.getText().toString();
                }
                //旷课
                else if(checkedId==cbKuangKe.getId()) {
                    date = sdf.format(new java.util.Date())+"："; //获取当前时间
                    etBeiZhu.setEnabled(false);
                    strChecked = cbKuangKe.getText().toString();
                }
                //病假
                else if(checkedId==cbBingJia.getId()) {
                    date = sdf.format(new java.util.Date())+"："; //获取当前时间
                    etBeiZhu.setEnabled(false);
                    strChecked = cbBingJia.getText().toString();
                }
                //备注
                else if(checkedId==cbBeiZhu.getId()){
                    date = sdf.format(new java.util.Date())+"："; //获取当前时间
                    etBeiZhu.setEnabled(true); //备注编辑框可用
                    etBeiZhu.requestFocus(); //并且焦点在这里
                }
                etCaoXing.setText(strCaoXing+" "+date+strChecked);
            }
        });

        //备注编辑框
        etBeiZhu.addTextChangedListener(new TextWatcher() {
            @Override  //改变前
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override  //改变中
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etCXBeiZhu.setText(etBeiZhu.getText().toString());
            }
            @Override  //改变后
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void sqInsert(){
        DBOpenHelper db = new DBOpenHelper(this);
        SQLiteDatabase sql_db = db.getWritableDatabase();
        for(int i=1;i<=3;i++){
            ContentValues values = new ContentValues();
            //   values.put(Student.KEY_ID,i);
            values.put(Studnet.KEY_SNO,"631406010101"+i);
            values.put(Studnet.KEY_SNAME,"莫天金"+i);
            values.put(Studnet.KEY_SCLASS,"计科一班"+i);
            values.put(Studnet.KEY_SBEIZHU,"在勤"+i);
            sql_db.insert(Studnet.TABLE,null,values);
        }
    }

}
