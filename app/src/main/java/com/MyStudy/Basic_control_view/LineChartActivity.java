package com.MyStudy.Basic_control_view;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.MyStudy.linechart.LineChartFunction;
import com.MyStudy.linechart.Mylinechart;

public class LineChartActivity extends AppCompatActivity {

    //定义画图框变量
    private LineChartFunction dynamicLineChartManager1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        //寻找控件ID
        final Mylinechart mChart1 = findViewById(R.id.dynamic_chart1);
        final Button button_data1 = findViewById(R.id.button_data1);

        dynamicLineChartManager1 = new LineChartFunction(mChart1, "数据", Color.GREEN);
        dynamicLineChartManager1.setYAxis(100, 0, 10);//最大值，最小值，中间刻度值的数量
        //按下按键实现的功能
        button_data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据传入端口
                dynamicLineChartManager1.addEntry((int) (Math.random() * 100));
               // dynamicLineChartManager1.addEntry((float) 75.65,0);
            }
        });
    }
}