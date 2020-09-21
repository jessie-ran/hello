package swufe.com.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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