package com.henry.list_recycle_view.RecyclerView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.henry.basic.R;
import com.henry.list_recycle_view.listview.Fruit;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTest3 extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MyAdapter adapter;
    private static final String[] SAMPLE_TITLES = {
            "DrawTriangle",
            "TextureMap",
            "YUV Rendering",
            "VAO&VBO",
            "FBO Offscreen Rendering",
            "EGL Background Rendering",
            "FBO Stretching",
            "Coordinate System",
            "Basic Lighting",
            "Transform Feedback",
            "Complex Lighting",
            "Depth Testing",
            "Instancing",
            "Stencil Testing",
            "Blending",
            "Particles",
            "SkyBox",
            "Assimp Load 3D Model",
            "PBO",
            "Beating Heart",
            "Cloud",
            "Time Tunnel",
            "Bezier Curve",
            "Big Eyes",
            "Face Slender",
            "Big Head",
            "Rotary Head",
            "Visualize Audio",
            "Scratch Card",
            "3D Avatar",
            "Shock Wave",
            "MRT",
            "FBO Blit",
            "Texture Buffer",
            "Uniform Buffer",
            "RGB to YUYV",
            "Multi-Thread Render",
            "Text Render",
            "Portrait stay color",
            "GL Transitions_1",
            "GL Transitions_2",
            "GL Transitions_3",
            "GL Transitions_4",
            "RGB to NV21",
            "RGB to I420",
            "RGB to I444",
            "Copy Texture",
            "Blit Frame Buffer",
            "Binary Program"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);//设置为垂直布局，这也是默认的

        List<Fruit> fruitlist = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Fruit pineapple = new Fruit(R.drawable.pineapple, "菠萝", "¥16.9 元/KG");
            fruitlist.add(pineapple);
            Fruit mango = new Fruit(R.drawable.mango, "芒果", "¥29.9 元/kg");
            fruitlist.add(mango);
            Fruit pomegranate = new Fruit(R.drawable.pomegranate, "石榴", "¥15元/kg");
            fruitlist.add(pomegranate);
            Fruit grape = new Fruit(R.drawable.grape, "葡萄", "¥19.9 元/kg");
            fruitlist.add(grape);
            Fruit apple = new Fruit(R.drawable.apple, "苹果", "¥20 元/kg");
            fruitlist.add(apple);
            Fruit orange = new Fruit(R.drawable.orange, "橙子", "¥18.8 元/kg");
            fruitlist.add(orange);
            Fruit watermelon = new Fruit(R.drawable.watermelon, "西瓜", "¥28.8元/kg");
            fruitlist.add(watermelon);
        }

        // recyclerView.addItemDecoration( new DividerGridItemDecoration(this ));//设置分隔线
        // recyclerView.setItemAnimator( new DefaultItemAnimator());//设置增加或删除条目的动画
        // adapter = new MyAdapter(this, Arrays.asList(SAMPLE_TITLES));
        adapter = new MyAdapter(this, fruitlist);
        adapter.setSelectIndex(19);
        adapter.addOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.notifyItemChanged(2);
            }
        });
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        recyclerView.setAdapter(adapter);//设置Adapter
    }


}