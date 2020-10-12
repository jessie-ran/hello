package swufe.com.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//单个积分器
public class MainActivity2 extends AppCompatActivity {
    Button b1;
    Button b2;
    Button b3;
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        b1=findViewById(R.id.button2);
        b2=findViewById(R.id.button3);
        b3=findViewById(R.id.button4);
        tx=findViewById(R.id.textView5);
        @SuppressLint("WrongViewCast") Button btn1 = (Button)findViewById(R.id.b5);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Intent是一种运行时绑定（run-time binding）机制，它能在程序运行过程中连接两个不同的组件。 
                //在存放资源代码的文件夹下下， 
                Intent i = new Intent(MainActivity2.this, MainActivity3.class);
                //启动 
                startActivity(i);
            }
        });

    }
    public void a3(View v){
        String str=tx.getText().toString();
        int a=Integer.parseInt(str);
        a=a+3;
        String outp=String.valueOf(a);
        tx.setText(outp);
    }
    public void a2(View v){
        String str=tx.getText().toString();
        int a=Integer.parseInt(str);
        a=a+2;
        String outp=String.valueOf(a);
        tx.setText(outp);
    }
    public void a1(View v){
        String str=tx.getText().toString();
        int a=Integer.parseInt(str);
        a=a+1;
        String outp=String.valueOf(a);
        tx.setText(outp);
    }

}