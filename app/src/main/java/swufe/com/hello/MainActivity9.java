package swufe.com.hello;
//这个是自定义的adapter
import androidx.appcompat.app.AppCompatActivity;


import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;

public class MainActivity9 extends  ListActivity {
    //自定义适配器Adapter
    private static final String TAG = "MainActivity9";
    ListView listview = null;
    Handler handler;
    //从网络上提取数据的时候才用得到
 private ArrayList<HashMap<String,String>> listItem;
    //存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main9);
       // 调用

        //与页面布局文件中的listview关联
        listview = (ListView) findViewById(R.id.my_list);
        ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Rate：" + i);
            // 标题文字
            map.put("ItemDetail", "detail" + i);
            // 详情描述
            listItems.add(map);
        }
        MyAdapter myAdapter = new MyAdapter(
                this,
                R.layout.hangbuju1,
                listItems);
        //创建MyAdapter对象
        this.setListAdapter(myAdapter);
         //使用自定义的myAdapter
    }
}
