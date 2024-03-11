package com.MyStudy.list_recycle_view.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MyStudy.Basic_control_view.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTest2 extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    List<News> mNewsList = new ArrayList<>();
    List<News> mNewsList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test1);
        mRecyclerView = findViewById(R.id.recyclerview);
        // 构造一些数据
        for (int i = 0; i < 10; i++) {
            News news = new News();
            news.title = "标题" + i;
            news.content = "内容" + i;
            mNewsList.add(news);
        }
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        /**
         * 线性布局管理器
         */
        //LinearLayoutManager layoutManager = new LinearLayoutManager(RecyclerTest2.this);

        /**
         *网格布局管理器
         *  GridLayoutManager layoutManager = new GridLayoutManager(RecyclerTest2.this,2);
         *  layoutManager.setOrientation(RecyclerView.HORIZONTAL);  //也能设置横向滚动
         */
        GridLayoutManager layoutManager = new GridLayoutManager(RecyclerTest2.this, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);  //也能设置横向滚动

        /**
         * 线性布局横向设置，默认垂直方向
         *  layoutManager.setOrientation(RecyclerView.HORIZONTAL);
         */

        /**
         * 设置间隔线
         */
        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);
        mRecyclerView.setLayoutManager(layoutManager);


        /**
         * 傻瓜式刷新
         *  notifyDataSetChanged()一键刷新，解决80%的刷新问题，刷新走一波
         *  当列表数据变更时，调用 notifyDataSetChanged() 是最省事的。无需关心变更的细节，一股脑统统刷一遍就完事了。
         *  但这样做也是最昂贵的。读完这一篇源码走查就知道为啥它这么昂贵了
         */

        /**
         * 移除刷新
         *  mMyAdapter.notifyItemRemoved(1);
         *  mNewsList.remove(1);
         *  mMyAdapter.notifyItemRangeChanged(1, mNewsList.size() - 1);
         *  //notifyItemRemoved(int):  指定移除某个item，mDataList数据一定要同步更新,否者RecyclerView会报错,
         *
         *  notifyItemRangeChanged():
         *  positionStart : 是从哪个界面位置开始Item开始变化,比如你点击界面上的第二个ItemView positionStart是1
         *  itemCount : 是以经发生变化的item的个数(包括自己,即正在点击这个),比如,你点击界面上的第二个ItemView,position [1,9] 发生变化,共计9个,
         *  因此我们上边计算是`datas.size() - position       主要作用是保证position的准确位置，刷新后的position与数据相匹配。
         */

        /**
         * 插入刷新
         * News news = new News();
         * news.title = "name:"+"插入";
         * news.content = "des:"+"插入";
         * mNewsList.add(1,news);
         * mMyAdapter.notifyItemInserted(1);
         * mMyAdapter.notifyItemRangeChanged(1, mNewsList.size() + 1);
         *
         *notifyItemInserted(int)花式插入数据，与一键移除花式刷新差不多，同时notifyItemRangeChanged(1, mDataList.size() + 1)
         * 千万别忘了，否者会有多个item同一个position（后果很严重的哦）。
         */

        /**
         * 移花接木式刷新 （交换刷新）
         //注意位置的变换 1 和 4 交换
         * News remove4 = mNewsList.remove(4);
         * News remove1 = mNewsList.remove(1);
         * mNewsList.add(1,remove4);
         * mNewsList.add(4,remove1);
         * mMyAdapter.notifyItemMoved(4,1);
         * //受影响的item都刷新position
         * mMyAdapter.notifyItemRangeChanged(Math.min(4, 1), Math.abs(4 - 1) +1);//受影响的item都刷新position
         *
         * notifyItemMoved(int fromPosition, int toPosition)带动画的移动item，从指定位置到指定位置的移动，
         * 数据更新加移动动画，同样notifyItemRangeChanged(Math.min(4, 1), Math.abs(4 - 1) +1)同样的作用，忘记调用会吃大亏。
         */

        /**
         *指定刷新
         *   News data1=new News();
         *   data1.setContent("dasdasd");
         *   mNewsList.get(1).setContent("dasdasd");
         *   mMyAdapter.notifyItemChanged(1,data1);
         *      OR
         *   mNewsList.get(1).setTitle("xuejie");
         *   mMyAdapter.notifyItemChanged(1);
         *   局部刷新notifyItemChanged(int position),notifyItemChanged(int position,Object payload),
         *   两个方法都是局部刷新的方法，RecyclerView特色刷新方式，指定位置进行刷新，比如item有进度条数据，这种数据变化频繁，或者聊天界面都会使用局部刷新。
         */

        /**
         *  自定义局部刷新
         *  //获取到viewholder的view
         *  View viewHolder = layoutManager.findViewByPosition(3);
         *  mNewsList.get(3).setTitle("xuejie");
         *  mMyAdapter.notifyItemChanged(3);
         *
         * 通过mGridLayoutManager.findViewByPosition(3)布局管理器，可以获取到指定的item，
         * 获取到指定的View，就可以实现对这个View的操作。实现刷新
         */

        Button button = findViewById(R.id.butt1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                News news = new News();
                news.title = "name:" + "插入";
                news.content = "des:" + "插入";
                mNewsList.add(1, news);
                mMyAdapter.notifyItemInserted(1);
                mMyAdapter.notifyItemRangeChanged(1, mNewsList.size() + 1);
            }
        });
        Button button1 = findViewById(R.id.butt2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyAdapter.notifyItemRemoved(1);
                mNewsList.remove(1);
                mMyAdapter.notifyItemRangeChanged(1, mNewsList.size() - 1);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(RecyclerTest2.this, R.layout.item_list, null);
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

        public News(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public News() {
            super();
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}