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
//使用列表进行汇率的展示
//可运行
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
      /* String[] list_data = {"one", "two", "three", "four"};
       ListAdapter adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, list_data);
        setListAdapter(adapter);

       */
        //上面是第一种listview的创建方法，主要是展示静态数据
        //接下来是爬取网页数据然后展示,首先应该把爬取回来的信息转化为字符串数组保存在list2
       Thread t1= new Thread(this);
        t1.start();
       // 获取网络数据,获取网路数据是在子线程中获取的
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==7){
                    String ran_gaigai=(String)msg.obj;
                    //处理字符串将其转化为字符串数组
//
                    String[] gaigai=ran_gaigai.split(",");

                    //之前的致命异常在这里，没有办法将String类型转化为list类型
               // List<String> list2 = (List<String>) msg.obj;
                ListAdapter adapter = new ArrayAdapter<String>(MainActivity6.this,
                        android.R.layout.simple_list_item_1,gaigai);
               setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    @Override
    public void run() {
        URL url = null;
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
        Message msg = handler.obtainMessage(7);
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

    //我本来是想写成返回字符串函数，但是好多知识忘记了，所以干脆返回字符串，用“，”划分，返回后再处理
    public  String useJsoup(String str) {
        //先把他解析为老师的样子
        //要用list<string>
        Document doc = Jsoup.parse(str);
        Elements trs = doc.select("table").get(0).select("tr");
        //接下来写一个for循环得到一个string 数组

        //首先是获取长度，先获得
        int tablelen = 0;
        String cou = "ran";
        String huil = "ran";
       String myre=null;
        //  tablelen=trs.
        //一共是有28行Table表
        for(int i=1;i<28;i++) {
            Elements t1 = trs.get(i).select("td");
            cou = t1.get(0).text();

            huil = t1.get(5).text();
            //直接汇率和间接汇率的转化
            float bw=100f/Float.parseFloat(huil);
            String er=bw+"";
            if (i == 1) {
                String mt = cou + "==>" + er;
                myre = mt;
            } else {
                String mt = cou+ "==>" + er;
                myre = myre + "," + mt;
            }
        }
        return myre;
    }

}