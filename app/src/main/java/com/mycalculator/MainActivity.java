package com.mycalculator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//java.util.regex是一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包。它包括两个类：Pattern和Matcher
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bsh.EvalError;
import bsh.Interpreter;
public class MainActivity extends AppCompatActivity {
    private EditText editText;          // editText 用于显示输入的内容和计算结果
    private TextView textView;          // textView 用于显示算术表达式
    //用于获取 activty_main 里所有按钮的 ID
    private Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9;     //按钮 0-9
    private Button btn_add,btn_subtract,btn_multiply,btn_divide,btn_point;          //按钮 + - × ÷ .
    private Button btn_backspace,btn_ce,btn_lr,btn_cursor;      //按钮 backspace CE ( ) |
    private Button btn_equal;       //按钮 =
    //根据按钮的 ID 设置监听器
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editText = (EditText) findViewById(R.id.editText_1);
            textView = (TextView) findViewById(R.id.textView_1);

            String str = editText.getText().toString();           //获取 editText 的字符串
            int index = editText.getSelectionStart();           //获取 editText 的光标当前位置
            Editable editable = editText.getText();
            Button btn = (Button) v;
            switch (btn.getId())
            {
                //数字 0-9
                case R.id.button_0:     // 0
                {
                    editable.insert(index, "0");
                    break;
                }
                case R.id.button_1:     // 1
                {
                    editable.insert(index, "1");
                    break;
                }
                case R.id.button_2:     // 2
                {
                    editable.insert(index, "2");
                    break;
                }
                case R.id.button_3:     // 3
                {
                    editable.insert(index, "3");
                    break;
                }
                case R.id.button_4:     // 4
                {
                    editable.insert(index, "4");
                    break;
                }
                case R.id.button_5:     // 5
                {
                    editable.insert(index, "5");
                    break;
                }
                case R.id.button_6:     // 6
                {
                    editable.insert(index, "6");
                    break;
                }
                case R.id.button_7:     // 7
                {
                    editable.insert(index, "7");
                    break;
                }
                case R.id.button_8:     // 8
                {
                    editable.insert(index, "8");
                    break;
                }
                case R.id.button_9:     // 9
                {
                    editable.insert(index, "9");
                    break;
                }

                //运算符 opt ( + - × ÷ )   规则的制定
                case R.id.button_add:       // +
                {
                    str = editText.getText().toString();
                    if(index > 0)                                                 //第一位输入不可能是 + × ÷ 运算符
                    {
                        if(str.substring(index-1,index).equals("+"))break;      //如果前一位运算符相同，则忽略
                        if(str.substring(index-1,index).equals("-") ||           //如果前一位运算符不同，则替换
                                str.substring(index-1,index).equals("×") ||
                                str.substring(index-1,index).equals("÷"))
                        {
                            editable.delete(index-1,index);
                            editable.insert(index-1, "+");
                        } else {
                            editable.insert(index, "+");                        //如果没有运算符，则正常插入
                        }
                    }
                    break;
                }
                case R.id.button_subtract:      // -
                {
                    str = editText.getText().toString();
                    if(index > 0)
                    {
                        if(str.substring(index-1,index).equals("-"))break;
                        if(str.substring(index-1,index).equals("+") ||
                                str.substring(index-1,index).equals("×") ||
                                str.substring(index-1,index).equals("÷"))
                        {
                            editable.delete(index-1,index);
                            editable.insert(index-1, "-");
                        }else {
                            editable.insert(index, "-");
                        }
                    }else {
                        editable.insert(index, "-");                            //减号是特殊符号，可以在第一位插入
                    }
                    break;
                }
                case R.id.button_multiply:      // ×
                {
                    str = editText.getText().toString();
                    if(index > 0)
                    {
                        if(str.substring(index-1,index).equals("×"))break;
                        if(str.substring(index-1,index).equals("+") ||
                                str.substring(index-1,index).equals("-") ||
                                str.substring(index-1,index).equals("÷"))
                        {
                            editable.delete(index-1,index);
                            editable.insert(index-1, "×");
                        } else {
                            editable.insert(index, "×");
                        }
                    }
                    break;
                }
                case R.id.button_divide:        // ÷
                {
                    str = editText.getText().toString();
                    if(index > 0)
                    {
                        if(str.substring(index-1,index).equals("÷"))break;
                        if(str.substring(index-1,index).equals("+") ||
                                str.substring(index-1,index).equals("-") ||
                                str.substring(index-1,index).equals("×"))
                        {
                            editable.delete(index-1,index);
                            editable.insert(index-1, "÷");
                        } else {
                            editable.insert(index, "÷");
                        }
                    }
                    break;
                }

                //点 ( . )
                case R.id.button_point:     // 点
                {
                    str = editText.getText().toString();
                    if(index > 0)                   //第一位也不可能是点号，如果前一位为数字，则允许添加点号
                    {
                        Pattern p = Pattern.compile("[0-9]*");              //Pattern类用于创建一个正则表达式
                        Matcher m = p.matcher(str.substring(index-1,index));    //Pattern.matcher(CharSequence input)用于快速匹配字符串
                        if(m.matches())
                        {
                            editable.insert(index, ".");
                        }
                    }
                    break;
                }

                //退格 Backspace ( ← )
                case R.id.button_backspace:     //  ←
                {
                    str = editText.getText().toString();
                    if(index > 0)editable.delete(index-1, index);
                    break;
                }

                //清空 ( CE )
                case R.id.button_ce:       // CE
                {
                    str = editText.getText().toString();
                    if(str.length() > 0)editText.setText("");
                    textView.setText("");
                    break;
                }

                //括号 ( () )
                case R.id.button_lr:        // ()
                {
                    str = editText.getText().toString();
                    if(index > 0)
                    {
                        Pattern p = Pattern.compile("[0-9]*");
                        Matcher m = p.matcher(str.substring(index-1,index));
                        if(m.matches())break;

                        if(str.substring(index-1,index).equals("."))break;
                    }
                    editable.insert(index, "()");
                    editText.setSelection(index+1);
                    break;
                }

                //光标
                case R.id.button_cursor:
                {
                    editText.setSelection(editText.getText().length());
                    break;
                }

                //等号 ( = ) ，运算处理方法
                case R.id.button_equal:
                {
                    String res,exp;
                    str = editText.getText().toString();
                    exp = editText.getText().toString();
                    if(str.isEmpty())break;                          //如果 editText 为空，忽略
                    exp = exp.replaceAll("×","*");                  //把 editText 的字符串中所有 × 替换成 *
                    exp = exp.replaceAll("÷","/");                  //把 editText 的字符串中所有 ÷ 替换成 /
                    exp = filterExp(exp);                             //用 filterExp(str) 函数，把 editText 的字符串中所有数字替换成小数
                    res = getResult(exp);                             //用 getResult(str) 函数，获取计算结果
                    textView.setText(str + "=" + res);
                    editText.setText(exp + "=" + res);
                    editText.setSelection(editText.getText().length());
                    break;
                }
            }
        }

        //过滤表达式，把数字全部变成小数
        public String filterExp(String exp) {
            String num[] = exp.split("");
            String temp = null;
            int begin=0,end=0;
            for (int i = 1; i < num.length; i++) {
                temp = num[i];
                if(temp.matches("[+-/()*]")){
                    if(temp.equals(".")) continue;
                    end = i - 1;
                    temp = exp.substring(begin, end);
                    if(temp.trim().length() > 0 && temp.indexOf(".")<0)
                        num[i-1] = num[i-1] + ".0";
                    begin = end + 1;
                }
            }
            temp = exp.substring(begin, num.length-1);
            if(temp.trim().length() > 0 && temp.indexOf(".")<0)
                num[num.length-1] = num[num.length-1] + ".0";

            exp = Arrays.toString(num).replaceAll("[\\[\\], ]", "");
            return exp;
        }

        //调用 bsh 包，使用 Interpreter.eval(str) 函数直接计算字符串格式的算术表达式
        public String getResult(String exp){
            Interpreter bsh = new Interpreter();
            Number result = null;
            try{
                result = (Number)bsh.eval(exp);
            }catch (EvalError ex){
                return "算术异常，算术表达式错误";
            }
            exp = result.doubleValue()+"";
            if(exp.endsWith(".0"))
                exp = exp.substring(0, exp.indexOf(".0"));
            return exp;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取按钮 ID
        btn_0 = (Button) findViewById(R.id.button_0);
        btn_1 = (Button) findViewById(R.id.button_1);
        btn_2 = (Button) findViewById(R.id.button_2);
        btn_3 = (Button) findViewById(R.id.button_3);
        btn_4 = (Button) findViewById(R.id.button_4);
        btn_5 = (Button) findViewById(R.id.button_5);
        btn_6 = (Button) findViewById(R.id.button_6);
        btn_7 = (Button) findViewById(R.id.button_7);
        btn_8 = (Button) findViewById(R.id.button_8);
        btn_9 = (Button) findViewById(R.id.button_9);
        btn_add = (Button) findViewById(R.id.button_add);
        btn_subtract = (Button) findViewById(R.id.button_subtract);
        btn_multiply = (Button) findViewById(R.id.button_multiply);
        btn_divide = (Button) findViewById(R.id.button_divide);
        btn_point = (Button) findViewById(R.id.button_point);
        btn_backspace = (Button) findViewById(R.id.button_backspace);
        btn_ce = (Button) findViewById(R.id.button_ce);
        btn_lr = (Button) findViewById(R.id.button_lr);
        btn_cursor = (Button) findViewById(R.id.button_cursor);
        btn_equal = (Button) findViewById(R.id.button_equal);

        //为按钮添加监听器
        btn_0.setOnClickListener(listener);
        btn_1.setOnClickListener(listener);
        btn_2.setOnClickListener(listener);
        btn_3.setOnClickListener(listener);
        btn_4.setOnClickListener(listener);
        btn_5.setOnClickListener(listener);
        btn_6.setOnClickListener(listener);
        btn_7.setOnClickListener(listener);
        btn_8.setOnClickListener(listener);
        btn_9.setOnClickListener(listener);
        btn_add.setOnClickListener(listener);
        btn_subtract.setOnClickListener(listener);
        btn_multiply.setOnClickListener(listener);
        btn_divide.setOnClickListener(listener);
        btn_point.setOnClickListener(listener);
        btn_backspace.setOnClickListener(listener);
        btn_ce.setOnClickListener(listener);
        btn_lr.setOnClickListener(listener);
        btn_cursor.setOnClickListener(listener);
        btn_equal.setOnClickListener(listener);
    }

}
