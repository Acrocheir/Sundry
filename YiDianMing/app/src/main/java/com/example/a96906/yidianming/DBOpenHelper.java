package com.example.a96906.yidianming;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

    public DBOpenHelper(Context context) {
        super(context, "DianMing.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Studnet.TABLE+"("+Studnet.KEY_SNO+" varchar(12) primary key," +
                Studnet.KEY_SNAME+" varchar(20) not null,"+Studnet.KEY_SCLASS+" varchar(20) not null," +
                Studnet.KEY_SBEIZHU+" text,"+Studnet.KEY_STOUXIANG+" blob)");
    }

    /**
     * 当数据库的版本发生变化时，会自动执行
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Studnet.TABLE); //表存在就删去
        onCreate(db); //再次创建
    }
}
