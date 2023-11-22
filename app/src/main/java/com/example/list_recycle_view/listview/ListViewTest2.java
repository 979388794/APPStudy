package com.example.list_recycle_view.listview;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.HomeScreen.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewTest2 extends AppCompatActivity {
    //第一步：定义对象
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_test);
        //第二步：绑定控件
        listView = (ListView) findViewById(R.id.list_view);
        //第三步：准备数据
        List<Fruit> fruitlist = new ArrayList<>();
        for (int i = 0; i <2 ; i++) {
            Fruit pineapple=new Fruit(R.drawable.pineapple,"菠萝","¥16.9 元/KG");
            fruitlist.add(pineapple);
            Fruit mango = new Fruit(R.drawable.mango, "芒果","¥29.9 元/kg");
            fruitlist.add(mango);
            Fruit pomegranate = new Fruit(R.drawable.pomegranate, "石榴","¥15元/kg");
            fruitlist.add(pomegranate);
            Fruit grape = new Fruit(R.drawable.grape, "葡萄","¥19.9 元/kg");
            fruitlist.add(grape);
            Fruit apple = new Fruit(R.drawable.apple, "苹果","¥20 元/kg");
            fruitlist.add(apple);
            Fruit orange = new Fruit(R.drawable.orange, "橙子","¥18.8 元/kg");
            fruitlist.add(orange);
            Fruit watermelon = new Fruit(R.drawable.watermelon, "西瓜","¥28.8元/kg");
            fruitlist.add(watermelon);
        }
        //第四步：设计每一个列表项的子布局
        //第五步：定义适配器 控件 -桥梁-数据
        FruitAdapter adapter=new FruitAdapter(ListViewTest2.this,R.layout.fruit_item,fruitlist);
        listView.setAdapter(adapter);
    }
}