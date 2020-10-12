package swufe.com.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

//这个还是汇率转化的Java
public class MainActivity5 extends AppCompatActivity {
    EditText a11;
    EditText a22;
    EditText a33;
    String date_run="0000-00-00 00:00:00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
       /*  Intent intent=getIntent();
        //先从上一个页面得到，然后再从这个页面取出
          Bundle bundle = intent.getExtras();
       float a1=bundle.getFloat("b1",0.0f);
        float a2=bundle.getFloat("b2",0.0f);
        float a3=bundle.getFloat("b3",0.0f);

        */
        //上面是用一个仓库里面得到的
        //下面就应该是从xml文件里面得到的
        SharedPreferences sharedPreferences = getSharedPreferences("myrate_1", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        float a1= sharedPreferences.getFloat("dollar_rate",0.0f);
        float a2 = sharedPreferences.getFloat("euro_rate",0.0f);
        float a3 = sharedPreferences.getFloat("won_rate",0.0f);
        //输出康康
        //得到输出板上的数字
        a11=findViewById(R.id.ab1);
        a22=findViewById(R.id.ab2);
        a33=findViewById(R.id.ab3);
        String output1=String.valueOf(a1);
        a11.setText(output1);
        String output2=String.valueOf(a2);
        a22.setText(output2);
        String output3=String.valueOf(a3);
        a33.setText(output3);
    }

    //写一个方法，能够返回上一个页面，带着更改过的值
    public void re(View v){
        //得到输出板上的数字
        a11=findViewById(R.id.ab1);
        a22=findViewById(R.id.ab2);
        a33=findViewById(R.id.ab3);
        String str1=a11.getText().toString();
        String str2=a22.getText().toString();
        String str3=a33.getText().toString();
        float newd=Float.parseFloat(str1);
        float newe=Float.parseFloat(str2);
        float neww=Float.parseFloat(str3);
        Intent intent=getIntent();
        Bundle bdl=new Bundle();
        bdl.putFloat("b1",newd);
        bdl.putFloat("b2",newe);
        bdl.putFloat("b3",neww);
        intent.putExtras(bdl);
        setResult(2,intent);//设置resultCode及带回的数据
        //返回到调用页面
        finish();
    }
    public void open(View v){
        Intent op=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jd.com"));
        startActivity(op);
    }
    //把更改的数据保存到xml文件里面，然后更改掉
    //而11 获取的那一步应该在主函数里面
    public void rexml(View v){
        //得到输出板上的数字
        a11=findViewById(R.id.ab1);
        a22=findViewById(R.id.ab2);
        a33=findViewById(R.id.ab3);
        String str1=a11.getText().toString();
        String str2=a22.getText().toString();
        String str3=a33.getText().toString();
        float newd=Float.parseFloat(str1);
        float newe=Float.parseFloat(str2);
        float neww=Float.parseFloat(str3);
        Intent intent=getIntent();
        SharedPreferences sp = getSharedPreferences("myrate_1",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar_rate",newd);
        editor.putFloat("euro_rate",newe);
        editor.putFloat("won_rate",neww);
        editor.apply();
        setResult(2,intent);//设置resultCode及带回的数据
        //返回到调用页面
        finish();
    }
}