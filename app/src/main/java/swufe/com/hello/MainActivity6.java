package swufe.com.hello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

public class MainActivity6 extends ListActivity implements Runnable{
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String str="ran";
        //  setContentView(R.layout.activity_main6);
        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            list1.add("item" + i);
        }
     //  String[] list_data = {"one", "two", "three", "four"};
      //  ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_data);
      //  setListAdapter(adapter);
        //上面是第一种listview的创建方
        //接下来是爬取网页数据然后展示
        Thread t = new Thread(this);
        t.start();
       // 获取网络数据,获取网路数据是在子线程中获取的
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==5){
                List<String> list2 = (List<String>) msg.obj;
                ListAdapter adapter = new ArrayAdapter<String>(MainActivity6.this,
                        android.R.layout.simple_list_item_1,list2);
                setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    @Override
    public void run() {
        URL url = null;
//            }
        String myre = null;
        try {
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            //所以获取的数据已经保存在这里了，html
            String html = inputStream2String(in);
            Log.i("thread", "run:html=" + html);
            //直接调用然后进行处理
            myre = useJsoup(html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析网页中的数据，我看是网页先输入，然后在解析
        //解析内容也要写在run函数里面吗
        Message msg = handler.obtainMessage(5);
        msg.obj =myre;
        handler.sendMessage(msg);

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
    public  String useJsoup(String str) {
        //先把他解析为老师的样子
        //要用list<string>
        Document doc = Jsoup.parse(str);
        Elements trs = doc.select("table").get(0).select("tr");
        //接下来写一个for循环得到一个string 数组
        // String[]  myre;
        //首先是获取长度，先获得
        int tablelen = 0;
        String cou = "ran";
        String huil = "ran";
        //  tablelen=trs.
        //一共是有28行Table表
        //for(int i=1;i<28;i++){
        Elements t1 = trs.get(1).select("td");
        cou = t1.get(1).text();
        huil = t1.get(5).text();
        String myre = cou + "==>" + huil;
        //  }
        return myre;
    }

}