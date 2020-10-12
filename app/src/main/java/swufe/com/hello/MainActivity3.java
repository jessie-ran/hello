package swufe.com.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//双个积分器
public class MainActivity3 extends AppCompatActivity {
    Button a1;
    Button a2;
    Button a3;
    TextView ta;
    Button b1;
    Button b2;
    Button b3;
    TextView tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        a1=findViewById(R.id.ba1);
        a2=findViewById(R.id.ba2);
        a3=findViewById(R.id.ba3);
        ta=findViewById(R.id.tas);
        b1=findViewById(R.id.bb1);
        b2=findViewById(R.id.bb2);
        b3=findViewById(R.id.bb3);
        tb=findViewById(R.id.tbs);
    }
    public void threea(View v) {
        String str=ta.getText().toString();
        int a=Integer.parseInt(str);
        a=a+3;
        String outp=String.valueOf(a);
        ta.setText(outp);
    }
    public void threeb(View v) {
        String str=tb.getText().toString();
        int a=Integer.parseInt(str);
        a=a+3;
        String outp=String.valueOf(a);
        tb.setText(outp);
    }

    public void twoa(View v){
        String str=ta.getText().toString();
        int a=Integer.parseInt(str);
        a=a+2;
        String outp=String.valueOf(a);
        ta.setText(outp);
    }
    public void twob(View v){
        String str=tb.getText().toString();
        int a=Integer.parseInt(str);
        a=a+2;
        String outp=String.valueOf(a);
        tb.setText(outp);
    }
    public  void onea(View v){
        String str=ta.getText().toString();
        int a=Integer.parseInt(str);
        a=a+1;
        String outp=String.valueOf(a);
        ta.setText(outp);
    }
    public  void oneb(View v){
        String str=tb.getText().toString();
        int a=Integer.parseInt(str);
        a=a+1;
        String outp=String.valueOf(a);
        tb.setText(outp);
    }
}