package com.henry.diagnosisTest.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.henry.diagnosisTest.listener.OnUIChangeListener;
import com.henry.diagnosisTest.model.DiagnosisEvent;
import com.henry.diagnosisTest.model.DiagnosisEventsItem;
import com.henry.diagnosisTest.utils.DensityUtils;

import java.util.List;

public class EventExpandenbleItemDecoration extends RecyclerView.ItemDecoration implements OnUIChangeListener {
    private List<Object> objects;
    private Context mContext;
    private Paint mBgPaint;//画背景
    private Paint mTextPaint;//画文字
    private int mTitleHeight;//Title的高度
    private Paint mLinePaint;//画底部分割线
    private int mLineWidth;//线宽
    private WindowManager wm;
    private int screenWidth;

    public EventExpandenbleItemDecoration(List<Object> objects, Context context) {
        this.objects = objects;
        mContext = context;
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.parseColor("#ffffff"));//

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(DensityUtils.sp2px(mContext, 28));
        mLineWidth = DensityUtils.dp2px(mContext, 1);
        mTitleHeight = DensityUtils.dp2px(mContext, 50);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.parseColor("#737272"));//
        mLinePaint.setStrokeWidth(mLineWidth);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = (parent.getLayoutManager()).getPosition(view);
        if (position == 0 && !(objects.get(position) instanceof DiagnosisEvent)) {
            //当前显示的第一个条目不是父级布局时需要绘制父级布局
            outRect.set(0, mTitleHeight, 0, 0);
        }
    }

    private  String convertDay(String str){
        if (str.length() == 8) str = str.substring(0,4)+"-"+str.substring(4,6)+"-" +str.substring(6,8);
        return str;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if(null != objects && objects.size() > 0) {
            //第一个可见条目的位置
            int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            View firstChild = parent.getLayoutManager().findViewByPosition(position);
            if ((position + 1) < objects.size()) {
                if (objects.get(position + 1) instanceof DiagnosisEvent) {
                    //当前第二个展示的item是父级布局时，此时再往上滑动时移动画板,产生动画效果(下面的布局把上面的顶出去的效果)
                    c.translate(0, firstChild.getTop());
                }
            }
            String c1 = "";
            if (objects.get(position) instanceof DiagnosisEvent) {
                //第一条是父级布局时，获取父级布局标题
                c1 = convertDay(((DiagnosisEvent) objects.get(position)).getDate());
            } else {
                //第一条是子级布局，获取其对应父级标题的布局
                c1 = ((DiagnosisEventsItem) objects.get(position)).getParentName();
            }
            c.drawRect(0, 0, parent.getRight(), mTitleHeight, mBgPaint);
            Rect rect = new Rect();
            mTextPaint.getTextBounds(c1, 0, 1, rect);
            c.drawText(c1, DensityUtils.dp2px(mContext, 15), mTitleHeight / 2 + rect.height() / 2, mTextPaint);
            c.drawLine(0, mTitleHeight, screenWidth, mTitleHeight, mLinePaint);
        }else {
            super.onDrawOver(c,parent,state);
        }
    }

    @Override
    public void getItems(List<Object> item) {
        //每次页面列表的adapter刷新后，都获取最新展示的数据列表
        this.objects = item;
    }
}
