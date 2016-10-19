package com.example.calculator;

import java.util.Stack;

/**
 * Created by 莫天金 on 2016/10/19.
 */

public class ContinuousCount {
    public String reversePolish_expression(String expression){
        Stack<Object> s3 = new Stack<Object>(); //存放结果栈
        Stack<Character> s4 = new Stack<Character>(); //存放操作字符栈
        int len = expression.length(); //传入函数的字符串长度

        char c1;
        double number;
        int m = -1,n = -1;
        for(int i=0;i<len;i++){
            c1 = expression.charAt(i); //得到逐个字符
            if(isOprator(c1)||i==len-1){ //如果是云算法，将前面数的数字存入s3栈，操作符存入s4栈
                if((i==len-1)&&(!isOprator(c1))) //最后一位数不是操作符，将前面的数压栈
                    m = i+1;
                else
                    m = i;
                for(int j=i-1;j>=0;j--){ //操作数入栈，向前遍历到下一个运算符，将中间字符串转化为double
                    if(isOprator(expression.charAt(j))){
                        n = j;
                        break;
                    }
                    n = j-1;
                }
                if(m!=n+1){ //只有当这两个值不相等时，中间才有操作数
                    number = Double.parseDouble(expression.substring(n+1,m));
                    s3.push(number);
                }
                //运算符入栈
                if((i==0)&&(c1!='(')){ //当表达式第一个字符为运算符且不是左括号时，返回表达式错误
                    return "表达式错误！";
                } else if(isOprator(c1)){ //且是操作数
                    while(true){
                        if(s4.isEmpty()||s4.peek()=='('||c1=='('){
                            s4.push(c1);
                            break;
                        } else if(c1==')') {
                            while(s4.peek()!='('){
                                s3.push(s4.pop());
                                if(s4.isEmpty()){
                                    return "缺少左括号！";
                                }
                            }
                            s4.pop(); //弹出
                            break;
                        } else {
                            if(priorityCompare(c1,s4.peek())==1){ //判断优先级
                                s4.push(c1);
                                break;
                            } else {
                                s3.push(s4.pop());
                            }
                        }
                    }
                }
            } else {
                continue;
            }
        }
        while (!s4.isEmpty()){ //表达式结束后，一次将s4剩下的运算符压入s3
            if((char)s4.peek()=='(')
                return "缺少有括号！";
            s3.push(s4.pop());
        }
        return countResult(s3);
    }

    //判断优先级
    private int priorityCompare(char c1,char c2){
        switch (c1){
            case '＋':case '－':
                return (c2=='×'||c2=='÷'?-1:0);
            case '×':case '÷':
                return (c2=='＋'||c2=='－'?1:0);
        }
        return 1;
    }

    private String countResult(Stack<Object> ob){
        Stack<Object> s1 = new Stack<Object>(); //后缀表达式栈
        Stack<Double> s2 = new Stack<Double>(); //操作数栈

        while(!ob.isEmpty()){ //将传入的栈逆序压入
            s1.push(ob.pop());
        }
        while(!s1.isEmpty()){
            if(!isOprator(s1.peek())){ //遇到非操作符，压入s2栈（peek() 其返回值是一个char型的字符，其返回值是指针指向的当前字符）
                s2.push((Double)s1.pop());
            } else {
                s2.push(count(s2.pop(), s2.pop(), (char) s1.pop()));
            }
        }
        return Double.toString(s2.peek());
    }

    //判断字符书否为运算符，是为真，否为假
    private boolean isOprator(Object o){
        try {
            char c = (char)o; //转为字符
            if(c=='＋'||c=='－'||c=='×'||c=='÷')
                return true;
        } catch (Exception e){
            return false;
        }
        return false;
    }

    private Double count(double s1,double s2,char s3){
        double result = 0;
        return result;
    }
}
