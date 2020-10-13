package swufe.com.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.HashMap;
//7和8是连在一起的，作为点击然后转化
//7是能够直接跑出来的，但是现在需要加上的是====》1、和网络数据连接起来   2、增加事件的监听
//注意转化数据类型
public class MainActivity7 extends AppCompatActivity implements Runnable{
    Handler handler;
    private ArrayList<HashMap<String, String>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        //上面应该先是从网络中爬取数据相关
        //注意，难点在类型的转化


        //下面是布局相关
        ListView listView = (ListView) findViewById(R.id.my_list);
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, String> map = new HashMap<String,
                    String>();
            map.put("ItemTitle", "Rate： " + i); // 标题文字
            map.put("ItemDetail", "detail" + i); // 详情描述
            listItems.add(map);
        }

        // 生成适配器的 Item 和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,
                listItems, // listItems 数据源
                R.layout.hangbuju1, // ListItem 的 XML 布局实现
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );

        listView.setAdapter(listItemAdapter );


    }

//上一个的代码复用
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