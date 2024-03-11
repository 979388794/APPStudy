package com.MyStudy.list_recycle_view.RecyclerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.MyStudy.Basic_control_view.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTest1 extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    List<News> mNewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test1);
        mRecyclerView = findViewById(R.id.recyclerview);
        // 构造一些数据
        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.title = "标题" + i;
            news.content = "内容" + i;
            mNewsList.add(news);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(RecyclerTest1.this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void but1(View view) {
        News news = new News();
        news.title = "标题 新内容" ;
        news.content = "内容 新内容" ;
        mNewsList.add(1,news);
        mMyAdapter.notifyItemInserted(1);
    }

    public void but2(View view) {
        mNewsList.remove(0);
        mMyAdapter.notifyItemMoved(0,1);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(RecyclerTest1.this, R.layout.item_list, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = mNewsList.get(position);
            holder.mTitleTv.setText(news.title);
            holder.mTitleContent.setText(news.content);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mTitleContent;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.textView);
            mTitleContent = itemView.findViewById(R.id.textView2);
        }
    }
    public class News {
        public String title; // 标题
        public String content; //内容
    }
}