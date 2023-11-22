package com.example.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextViewActivity extends Activity {
    private TextView mtv;

    String Tag = "xuejie";
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_text_view1);
        LinearLayout ll = findViewById(R.id.ll);
        inflater = LayoutInflater.from(this);

        /**
         * 方式一：将布局添加成功
         */
        // inflater.inflate(R.layout.merge_layout, ll, true);//正确的

        /**
         * 方式二：报错。一个view只能有一个父亲，The specified child already has a parent. You must call removeView() on the child's parent first.
         */
//        View view = inflater.inflate(R.layout.merge_layout, ll, true);
//        ll.addView(view);

        /**
         *方式三：布局添加成功
         */
//        View view = inflater.inflate(R.layout.merge_layout, ll, false);
//        ll.addView(view);

        /**
         方式四：可以添加里面的子布局Button控件，Linearlayout控件无效，在view的绘制流程里有代码逻辑  Linearlayout控件大小完全由内部的Button决定
         */
        View view = inflater.inflate(R.layout.merge_layout, null, false);
        ll.addView(view);
//        View view = inflater.inflate(R.layout.merge_layout, null, true);
//        ll.addView(view);


        mtv = findViewById(R.id.tv_6);
        Log.d(Tag, "高度1为" + mtv.getMeasuredHeight());
        mtv.post(new Runnable() {
            @Override
            public void run() {
                Log.d(Tag, "高度2为" + mtv.getMeasuredHeight());
            }
        });

       /* ViewStub stub = findViewById(R.id.stub_import);
        stub.inflate();
        stub.setVisibility(View.VISIBLE);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag, "高度3为" + mtv.getMeasuredHeight());
    }
}