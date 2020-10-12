package swufe.com.hello;
//摄氏度转化器
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.textView);
        et=(EditText)findViewById(R.id.inp);
        Button btn1 = (Button) findViewById(R.id.btn6);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View v) {
                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。 
                //在存放资源代码的文件夹下下， 
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                //启动 
                startActivity(i);
            }
               });

    }
    //这个是温度转化的函数
    public void btn(View v){
        String str=tv.getText().toString();
        String str2=et.getText().toString();
        //然后是转化为double类型
        double d=Double.parseDouble(str2);
        //然后是计算华氏摄氏度
        double re=d*1.8+32;
        String output=String.valueOf(re);
        tv.setText(str+output);

    }


}