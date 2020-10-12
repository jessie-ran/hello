package swufe.com.hello;
//汇率转化器,直接转化，并且能够通过下一个页面改变当前汇率值
//这里开始使用float类型
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity4 extends AppCompatActivity implements Runnable {
    EditText et;
    TextView tv;
    TextView tv2;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    float bd = 0.1464f;
    float be = 0.1258f;
    float bw = 171.9833f;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        et = findViewById(R.id.inp);
        tv = findViewById(R.id.tv2);
        b1 = findViewById(R.id.btn1);
        b2 = findViewById(R.id.btn2);
        b3 = findViewById(R.id.btn3);
        b4 = findViewById(R.id.next);

        //下面是爬取网页数据问题
        Thread t = new Thread(this);
        t.start();

         handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    String str = (String) msg.obj;
                    Log.i("thread", "handleMessage:getMessage=" + str);
                }
            }
        };
         Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=5;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask,1000,1000*60*60*24);
    }

    public void yd(View v) {
        //弹出提示信息
        Toast.makeText(this, "please input number", Toast.LENGTH_SHORT).show();
        //人民币转化为美元，实时的时候，就读表然后在匹配
        String str1 = et.getText().toString();
        //然后是转化为double类型
        float a = Float.parseFloat(str1);
        float b = a * bd;
        String output = String.valueOf(b);
        tv.setText(output + "元");
    }

    public void ye(View v) {

        String str1 = et.getText().toString();
        //然后是转化为double类型
        float a = Float.parseFloat(str1);
        float b = a * be;
        String output = String.valueOf(b);
        tv.setText(output + "元");
    }

    public void yw(View v) {
        String str1 = et.getText().toString();
        //然后是转化为double类型
        float a = Float.parseFloat(str1);
        float b = a * bw;
        String output = String.valueOf(b);
        tv.setText(output + "元");
    }

    //传参和转到下个页面
    //第四周加上了把数据保存到xml文件的操作
    //也就是说应该在传到下个页面之前先保存，在下个页面再修改保存内容
    public void nt(View v) {
        //第一种方法是直接输出，让后更改返回
        Intent intent = new Intent(this, MainActivity5.class);
        //放在一个盒子里面，然后传过去,接受到更改
        Bundle bdl = new Bundle();
        bdl.putFloat("b1", bd);
        bdl.putFloat("b2", be);
        bdl.putFloat("b3", bw);
        intent.putExtras(bdl);
        startActivityForResult(intent, 2);
    }

    //写一个函数能够接收到更改的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //   if(requestCode==1 && resultCode==2){
     /*   Bundle bundle = data.getExtras();
        bd= bundle.getFloat("b1",0.1f);
        be = bundle.getFloat("b2",0.1f);
        bw = bundle.getFloat("b3",0.1f);
        //上面原来是放在bundle里面，然后更改得到数据
      */
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        bd = sharedPreferences.getFloat("dollar_rate", 0.0f);
        be = sharedPreferences.getFloat("euro_rate", 0.0f);
        bw = sharedPreferences.getFloat("won_rate", 0.0f);
        //  }
        super.onActivityResult(requestCode, resultCode, data);
        tv2 = findViewById(R.id.tvv);
        String output3 = String.valueOf(bw);
        tv2.setText(output3);
    }

    //下面一个函数，是保存在xml文件之中，然后更改返回
    public void ntxml(View v) {
        Intent intent = new Intent(this, MainActivity5.class);
        SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar_rate", bd);
        editor.putFloat("euro_rate", be);
        editor.putFloat("won_rate", bw);
        editor.apply();
        startActivityForResult(intent, 2);
    }

    @Override
    public void run() {

        Message msg = handler.obtainMessage(5);
        msg.obj = "hello for run()";
        handler.sendMessage(msg);
        URL url = null;
        try {
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            //所以获取的数据已经保存在这里了，html
            String html = inputStream2String(in);
            Log.i("thread", "run:html=" + html);
            //直接调用然后进行处理
            useJsoup(html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析网页中的数据，我看是网页先输入，然后在解析
        //解析内容也要写在run函数里面吗
        // 从 URL 直接加载 HTML 文档
       // Document doc = Jsoup.parse(html);

    }

    //这里就应该是解析网页
    public void useJsoup(String str){
        Document doc =Jsoup.parse(str);
        Elements trs=doc.select("table").get(0).select("tr");

        Elements t1=trs.get(7).select("td");
        String t11=t1.get(5).text();
        be=100f/Float.parseFloat(t11);

        Elements t2=trs.get(13).select("td");
        String t22=t2.get(5).text();
        bw=100f/Float.parseFloat(t22);

        Elements t3=trs.get(26).select("td");
        String t33=t3.get(5).text();
        bd=100f/Float.parseFloat(t33);
        //把数据转为字符串，然后输出到日志之中
    }

    private String inputStream2String(InputStream inputStream) throws IOException{
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in =new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }

}