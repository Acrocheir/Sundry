package com.example.calculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btn_zero; //数字按钮0
    Button btn_one; //数字按钮1
    Button btn_two; //数字按钮2
    Button btn_three; //数字按钮3
    Button btn_four; //数字按钮4
    Button btn_five; //数字按钮5
    Button btn_six; //数字按钮6
    Button btn_seven; //数字按钮7
    Button btn_eight; //数字按钮8
    Button btn_nine; //数字按钮9
    Button btn_plus; //按钮＋
    Button btn_minus; //按钮－
    Button btn_multiple; //按钮×
    Button btn_division; //按钮÷
    Button btn_point; //按钮.
    Button btn_clear; //按钮清除
    Button btn_del; //按钮退格
    Button btn_equal; //按钮等号
    Button btn_conversion; //进制
    TextView tv_input;  //显示区域

    boolean flag;  //清空标识
    String result_jinzhi = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化按钮
        btn_zero = (Button) findViewById(R.id.btn_zero);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_four = (Button) findViewById(R.id.btn_four);
        btn_five = (Button) findViewById(R.id.btn_five);
        btn_six = (Button) findViewById(R.id.btn_six);
        btn_seven = (Button) findViewById(R.id.btn_seven);
        btn_eight = (Button) findViewById(R.id.btn_eight);
        btn_nine = (Button) findViewById(R.id.btn_nine);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_multiple = (Button) findViewById(R.id.btn_multiple);
        btn_division = (Button) findViewById(R.id.btn_division);
        btn_point = (Button) findViewById(R.id.btn_point);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_conversion = (Button) findViewById(R.id.btn_coversion);
        tv_input = (TextView) findViewById(R.id.tv_input); //实例化显示屏


        btn_zero.setOnClickListener(MainActivity.this);
        btn_one.setOnClickListener(MainActivity.this);
        btn_two.setOnClickListener(MainActivity.this);
        btn_three.setOnClickListener(MainActivity.this);
        btn_four.setOnClickListener(MainActivity.this);
        btn_five.setOnClickListener(MainActivity.this);
        btn_six.setOnClickListener(MainActivity.this);
        btn_seven.setOnClickListener(MainActivity.this);
        btn_eight.setOnClickListener(MainActivity.this);
        btn_nine.setOnClickListener(MainActivity.this);
        btn_plus.setOnClickListener(MainActivity.this);
        btn_minus.setOnClickListener(MainActivity.this);
        btn_multiple.setOnClickListener(MainActivity.this);
        btn_division.setOnClickListener(MainActivity.this);
        btn_point.setOnClickListener(MainActivity.this);
        btn_clear.setOnClickListener(MainActivity.this);
        btn_del.setOnClickListener(MainActivity.this);
        btn_equal.setOnClickListener(MainActivity.this);
        btn_conversion.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View v) {

        String str = tv_input.getText().toString();  //在显示屏上的字符串
        switch (v.getId()) {
            case R.id.btn_zero:
            case R.id.btn_one:
            case R.id.btn_two:
            case R.id.btn_three:
            case R.id.btn_four:
            case R.id.btn_five:
            case R.id.btn_six:
            case R.id.btn_seven:
            case R.id.btn_eight:
            case R.id.btn_nine:
                //   case R.id.btn_point:
                if (flag) {   //计算过
                    flag = false;
                    str = ""; //计算过设置为空
                    tv_input.setText("");
                }
                tv_input.setText(str + ((Button) v).getText());  //显示屏上已有的内容加上输入的内容
                break;
            case R.id.btn_point:
                try {
                    if (str == "") {
                        tv_input.setText(str + ((Button) v).getText());
                    } else {
                        String str1 = str.substring(str.length()-1);
                        if (!str1.equals(".")) {
                            tv_input.setText(str + ((Button) v).getText()); //显示屏上已有的内容加上输入的内容
                        } else {}
                    }
                } catch (Exception e) {}
                break;

            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiple:
            case R.id.btn_division:
                if (flag) {
                    flag = false;
                    str = "";
                    tv_input.setText("");
                }
               // tv_input.setText(str + " " + ((Button) v).getText() + " ");
                try{
                    if(str == "") {
                        tv_input.setText(str + " " + ((Button) v).getText() + " ");
                    }else{
                        String str11 = str.substring(str.length()-1); //最后一个字符
                        if(!str11.equals(" ")){ //最后一个字符不为空格
                            tv_input.setText(str + " " + ((Button) v).getText() + " ");
                        } else {}
                    }
                }catch (Exception e) {}
                break;

            case R.id.btn_clear:
                flag = false;
                str = "";
                tv_input.setText("");
                break;
            case R.id.btn_del:
                if (flag) {
                    flag = false;
                    str = "";
                    tv_input.setText("");
                } else if (str != null && !str.equals(""))   //存在不为空的字符
                {
                    tv_input.setText(str.substring(0, str.length() - 1)); //当前长度减1
                }
                break;
            case R.id.btn_equal:
                try {
                    getResult();
                } catch (Exception e) {}
                break;
            case R.id.btn_coversion:
                Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle(
                        "进制转换").setMessage("选择你要转换的进制(只能转换结果)").setNeutralButton("二进制",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            String str_conersion2 = tv_input.getText().toString();
                            double dou_2 = Double.valueOf(str_conersion2);
                            int int_2 = (int)dou_2;
                            dto_Int(int_2,2);
                            result_jinzhi = result_jinzhi + ".";
                            dto_Doc(dou_2-int_2,2);
                            tv_input.setText(result_jinzhi);
                        } catch (Exception e){}
                    }
                }).setNegativeButton("八进制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            String str_conersion8 = tv_input.getText().toString();
                            double dou_8 = Double.valueOf(str_conersion8);
                            int int_8 = (int)dou_8;
                            dto_Int(int_8,8);
                            result_jinzhi = result_jinzhi + ".";
                            dto_Doc(dou_8-int_8,8);
                            tv_input.setText(result_jinzhi);
                        } catch (Exception e){}
                    }
                }).setPositiveButton("十六进制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            String str_conersion16 = tv_input.getText().toString();
                            double dou_16 = Double.valueOf(str_conersion16);
                            int int_16 = (int)dou_16;
                            dto_Int(int_16,16);
                            result_jinzhi = result_jinzhi + ".";
                            dto_Doc(dou_16-int_16,16);
                            tv_input.setText(result_jinzhi);
                        } catch (Exception e){}
                    }
                }).create();
                dialog.show();
                break;
            default:
                break;
        }
    }

    //进制转换整数部分
    private void dto_Int(int n,int r)
    {
      //  result_jinzhi = tv_input.getText().toString();
        int m;
        char ch;
        if(n>0)
        {
            m=n%r;
            n=n/r;
            dto_Int(n,r);
            if(m<10)
                ch = (char)(m+'0');
            else
                ch = (char)('A'+m-10);
            result_jinzhi = result_jinzhi + ch;
        }
    }
    //进制转换小数部分
    private void dto_Doc(double n, int r) {
     //   result_jinzhi = tv_input.getText().toString();
        int m;
        char ch;
        for (int i = 0; i < 5; i++) {
            if(n==0)
                break;
            n = n * r;
            m = (int) n;
            n = n - m;
            if(m<10)
                ch = (char)(m+'0');
            else
                ch = (char)('A'+m-10);
            result_jinzhi = result_jinzhi + ch;
        }
    }

    //计算结果
    private void getResult() {
        String exp = tv_input.getText().toString();
        double Result = 0;
        if (exp == null || exp.equals(""))
            //  return;
            if (exp.contains(" ")) {
                //  return;
            }
        if (flag) {   //防止连续点等号
            flag = false;
            //   return;
        }
        flag = true;
        String s1 = exp.substring(0, exp.indexOf(" ")); //运算符前面的字符串
        String op = exp.substring(exp.indexOf(" ") + 1, exp.indexOf(" ") + 2); //截取到的运算符
        String s2 = exp.substring(exp.indexOf(" ") + 3); //运算符后面的字符串
        if (!s1.equals("") && !s2.equals("")) //都不为空,强制转换
        {
            double d1 = Double.parseDouble(s1);
            double d2 = Double.parseDouble(s2);
            if (op.equals("＋")) {
                Result = d1 + d2;
            } else if (op.equals("－")) {
                Result = d1 - d2;
            } else if (op.equals("×")) {
                Result = d1 * d2;
            } else if (op.equals("÷")) {
                if (d2 == 0)
                    Result = 0;
                else
                    Result = d1 / d2;
            }
            if (!s1.contains(".") && !s2.contains(".") && !op.equals("÷")) {   //没有有小数点
                int r = (int) Result;  //整数型
                tv_input.setText(r + "");
            } else {
                tv_input.setText(Result + "");
            }
        }
        else if (!s1.equals("") && s2.equals("")) //s1不为空，s2为空
        {
            tv_input.setText(exp);
        }
        else if (s1.equals("") && !s2.equals(""))  //s1为空，s2不为空
        {
            double d2 = Double.parseDouble(s2);
            if (op.equals("＋")) {
                Result = 0 + d2;
            } else if (op.equals("－")) {
                Result = 0 - d2;
            } else if (op.equals("×")) {
                Result = 0;
            } else if (op.equals("÷")) {
                Result = 0;
            }
            if (!s2.contains(".")) {   //s2有小数点
                int r = (int) Result;  //整数型
                tv_input.setText(r + "");
            } else {
                tv_input.setText(Result + "");
            }
        } else //都为空
        {
            tv_input.setText("");
        }
    }
}
