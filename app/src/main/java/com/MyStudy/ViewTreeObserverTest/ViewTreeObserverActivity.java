package com.MyStudy.ViewTreeObserverTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.MyStudy.Basic_control_view.R;

public class ViewTreeObserverActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener,
        ViewTreeObserver.OnPreDrawListener, ViewTreeObserver.OnGlobalFocusChangeListener,
        ViewTreeObserver.OnTouchModeChangeListener, View.OnClickListener {
    ViewTreeObserver vto;
    EditText et_1, et_2;
    TextView tv_show;
    int viewWidth;
    int viewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tree_observer);
        vto = findViewById(R.id.layout).getViewTreeObserver();
        et_1 = (EditText) findViewById(R.id.et_1);
        et_2 = (EditText) findViewById(R.id.et_2);
        vto.addOnGlobalLayoutListener(this);
        vto.addOnPreDrawListener(this);
        vto.addOnGlobalFocusChangeListener(this);
        vto.addOnTouchModeChangeListener(this);
        findViewById(R.id.btn).setOnClickListener(this);
        tv_show = (TextView) findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v) {
        if (et_1.isShown()) {
            et_1.setVisibility(View.GONE);
            Toast.makeText(this, "宽度为" + viewWidth + " " + "长度为" + viewHeight, Toast.LENGTH_SHORT).show();
        } else {
            et_1.setVisibility(View.VISIBLE);
            Toast.makeText(this, "宽度为" + viewWidth + " " + "长度为" + viewHeight, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        if (oldFocus != null) {
            tv_show.setText("Focus change from " + oldFocus.getTag() + " to " + newFocus.getTag());
        } else {
            tv_show.setText(newFocus.getTag() + "get focus");
        }
    }

    @Override
    public void onGlobalLayout() {
        //获得宽高
        viewWidth = findViewById(R.id.layout).getMeasuredWidth();
        viewHeight = findViewById(R.id.layout).getMeasuredHeight();
        if (et_1.isShown()) {
            tv_show.setText("EditText1 显示");
        } else {
            tv_show.setText("EditText1 隐藏");
        }
    }

    @Override
    public boolean onPreDraw() {
        et_1.setHint("set hint on onPreDraw ");
        //Return true to proceed with the current drawing pass, or false to cancel.
        //返回 true 继续绘制，返回false取消。
        return true;
    }

    @Override
    public void onTouchModeChanged(boolean isInTouchMode) {

    }


}