package com.henry.list_recycle_view.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.henry.basic.R;

public class ListViewTest1 extends AppCompatActivity {

    ListView listView;
    String[] data = {"菠萝", "芒果", "石榴", "葡萄", "苹果", "橙子", "西瓜", "菠萝", "芒果", "石榴", "葡萄", "苹果", "橙子", "西瓜", "菠萝", "芒果", "石榴", "葡萄", "苹果", "橙子", "西瓜"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_test);
        listView = findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ListViewTest1.this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * Parent: 指定哪个AdapterView（可能会有多个ListView，区分多个ListView）
                 *
                 * View: 为你点击的Listview的某一项的内容，来源于adapter。如用((TextView)view).getText().toString()，可以取出点击的这一项的内容，转为string 类型。
                 *
                 * Position: 指的是adapter的某一项的位置，如点击了listview第2项，而第2项对应 的是adapter的第2个数值，那此时position的值就为1了。注：这些数值都是从0开 始的。
                 *
                 * Id：id的值为点击了Listview的哪一项对应的数值，点击了listview第2项，那id就等于1。一般和position相同。
                 */
                String result = ((TextView) view).getText().toString();
                Toast.makeText(ListViewTest1.this, "您选择的水果是：" + result, Toast.LENGTH_LONG).show();
            }
        });
    }
}