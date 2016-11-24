package com.example.a96906.xiaodouemail;

import android.app.Application;

import java.io.InputStream;
import java.util.ArrayList;

import javax.mail.Session;
import javax.mail.Store;

/**
 * Created by 96906 on 2016/11/17.
 */

public class MyApplication extends Application {
    /**
     * Session 它可以将用户正确登录后的信息记录到服务器的内存中，当用户以此身份访问网站的管理后台时，
     * 无需再次登录即可得到身份确认。而没有正确登录的用户则不分配session空间，即便输入了管
     * 理后台的访问地址也不能看到页面内容。
     */
    public static Session session = null;
    public static MailInfo info=new MailInfo();
    private static Store store;
    private ArrayList<InputStream> attachmentsInputStreams;

    public static void setStore(Store store) {
        MyApplication.store = store;
    }
    public static Store getStore() {
        return store;
    }

    public void setAttachmentsInputStreams(ArrayList<InputStream> attachmentsInputStreams) {
        this.attachmentsInputStreams = attachmentsInputStreams;
    }
    public ArrayList<InputStream> getAttachmentsInputStreams() {
        return attachmentsInputStreams;
    }


}
