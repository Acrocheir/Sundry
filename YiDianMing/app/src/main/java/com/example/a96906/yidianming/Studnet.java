package com.example.a96906.yidianming;

public class Studnet {
    public static final String TABLE = "Student"; //表名

    public static final String KEY_SNO = "sno"; //学号
    public static final String KEY_SNAME = "sname"; //姓名
    public static final String KEY_SCLASS = "sclass"; //班级
    public static final String KEY_SBEIZHU = "sbeizhu"; //备注
    public static final String KEY_STOUXIANG = "stouxiang"; //头像

    //属性值
    private String sno;
    private String sname;
    private String sclass;
    private String sbeizhu;

    //取得字段名
   /* public String getTABLE(){
        return TABLE;
    }
    public String getKEY_SNO(){
        return KEY_SNO;
    }
    public String getKEY_SNAME(){
        return KEY_SNAME;
    }
    public String getKEY_SCLASS(){
        return KEY_SCLASS;
    }
    public String getKEY_SBEIZHU(){
        return KEY_SBEIZHU;
    }
*/
    //属性
    public void setSno(String sno){
        this.sno = sno;
    }
    public String getSno(){
        return sno;
    }
    public void setSname(String sname){
        this.sname = sname;
    }
    public String getSname(){
        return sname;
    }
    public void setSclass(String sclass){
        this.sclass = sclass;
    }
    public String getSclass(){
        return sclass;
    }
    public void setSbeizhu(String sbeizhu){
        this.sbeizhu = sbeizhu;
    }
    public String getSbeizhu(){
        return sbeizhu;
    }
}
