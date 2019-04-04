package com.dan.testday3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dan.adapter.Test1Adapter;
import com.dan.entity.Test1Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan on 2018/9/17 10:14
 */
public class ListViewActivity extends AppCompatActivity {

    private final static int Max_Size = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_view);

        /*ArrayList<String> dataList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {

            dataList.add("平头" + String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ListViewActivity.this, android.R.layout.simple_list_item_1, dataList.toArray(new String[0]));
        ((ListView) findViewById(R.id.list_1)).setAdapter(adapter);*/

        //上下文,listView绑定的数据,listView子条目布局id,data数据里面的Map集合(key),map里面的key数据对应的显示id(如：TextView组件id)
        /*List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "");
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                ListViewActivity.this, data, R.layout.test1_main, new String[]{""}, new int[]{1});*/


        final List<Test1Entity> test1EntityList = getTest1EntityList();
        Test1Adapter test1Adapter = new Test1Adapter(ListViewActivity.this, R.layout.test1_main, test1EntityList);

        ListView view = (ListView) findViewById(R.id.list_1);
        //设置点击事件
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Test1Entity test1Entity = test1EntityList.get(position);
                Toast.makeText(ListViewActivity.this, test1Entity.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setAdapter(test1Adapter);
    }

    private List<Test1Entity> getTest1EntityList() {
        List<Test1Entity> test1EntityList = new ArrayList<>();
        Test1Entity test1Entity = null;
        for (int i = 1; i <= Max_Size; i++) {
            test1Entity = new Test1Entity();
            test1Entity.setTitle("标题：" + i);
            test1Entity.setEpisode(i);
            test1Entity.setImageId(R.drawable.tu1);
            test1Entity.setScore(BigDecimal.valueOf(3.8));
            test1EntityList.add(test1Entity);
        }

        return test1EntityList;
    }


}
