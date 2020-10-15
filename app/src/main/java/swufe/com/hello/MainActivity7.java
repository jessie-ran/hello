package swufe.com.hello;
//自定义列表未加连接网络数据部分
//可运行
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
import java.util.List;

//7和8是连在一起的，作为点击然后转化
//7是能够直接跑出来的，但是现在需要加上的是====》1、和网络数据连接起来   2、增加事件的监听
//注意转化数据类型
public class MainActivity7 extends ListActivity implements Runnable {
    private static final String TAG ="hello";
    Handler handler;
    private ArrayList<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter; // 适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main7);
        // 生成适配器的 Item 和动态数组对应的元素
       // ListView listView_r= (ListView) findViewById(R.id.my_list);
      // initListView();
      // this.setListAdapter(listItemAdapter);
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate： " + i); // 标题文字
            map.put("ItemDetail", "detail:" + i); // 详情描述
            listItems.add(map);
        }
        //实现Runnable接口，开启线程
        // 创建新线程
        Thread t = new Thread(this);
        // 开启线程
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 9) {
                    List<HashMap<String,String>>list2=(List<HashMap<String, String>>)msg.obj;
                    listItemAdapter=new SimpleAdapter(MainActivity7.this,
                            list2,//listItem数据源
                            R.layout.hangbuju1, //ListItem的XML布局实现
                            new String[]{"ItemTitle","ItemDetail"}, //数据的key
                             new int[]{R.id.itemTitle,R.id.itemDetail}//布局里的id，k与id一一匹配
                           );

               setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

    }


    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate： " + i); // 标题文字
            map.put("ItemDetail", "detail" + i); // 详情描述
            listItems.add(map);
        }
        // 生成适配器的 Item 和动态数组对应的元素
        listItemAdapter = new SimpleAdapter(
                MainActivity7.this,
                listItems, // listItems 数据源
                R.layout.hangbuju1, // ListItem 的 XML 布局实现
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );
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

    public void run() {
        URL url = null;
        String myre = null;
        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();

        try {
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            //所以获取的数据已经保存在这里了，html
            String html = inputStream2String(in);
            Log.i("thread", "run:html=" + html);
            //直接调用然后进行处理
            //      myre = useJsoup(html);
            String str=html;
            Document doc = Jsoup.parse(str);
            Elements trs = doc.select("table").get(0).select("tr");
//接下来写一个for循环得到一个string 数组

            //首先是获取长度，先获得
            int tablelen = 0;
            String cou = "ran";
            String huil = "ran";
            //  tablelen=trs.
            //一共是有28行Table表
            for(int i=1;i<28;i++) {
                Elements t1 = trs.get(i).select("td");
                cou = t1.get(0).text();

                huil = t1.get(5).text();
                //直接汇率和间接汇率的转化
                float bw=100f/Float.parseFloat(huil);
                String er=bw+"";
                HashMap<String, String> map =new HashMap<String, String>();
                map.put("ItemTitle",cou);
                map.put("ItemDetail",er);
                retList.add(map);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析网页中的数据，我看是网页先输入，然后在解析
        //解析内容也要写在run函数里面吗
        Message msg = handler.obtainMessage(9);
        msg.obj = retList;
        handler.sendMessage(msg);

    }



}