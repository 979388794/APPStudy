package com.example.linechart;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;


public class LineChartFunction {

    private Mylinechart lineChart;  //定义一个chart类型的数据
    private YAxis leftAxis;       //定义左y轴
    private YAxis rightAxis;      //定义右y轴
    private XAxis xAxis;          //定义x 轴
    private LineDataSet lineDataSet;
    private LineData lineData;
    private Description description=new Description();
    //一条曲线
    public LineChartFunction(Mylinechart mLineChart, String name, int color) {
        this.lineChart = mLineChart;
        leftAxis = lineChart.getAxisLeft();
        rightAxis = lineChart.getAxisRight();
        xAxis = lineChart.getXAxis();
        initLineChart();
        initLineDataSet(name, color);
    }


    /**
     * 初始化LineChar
     */
    private void initLineChart() {
        description.setText("");
        lineChart.setDescription(description);
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //设置XY轴动画效果
//        lineChart.animateY(2500);
//        lineChart.animateX(1500);
//
//        lineChart.setScaleEnabled(true); // 是否可以缩放
//        lineChart.setDragEnabled(false); //是否可以拖动
        //显示边界线，边界线被加粗
        lineChart.setDrawBorders(false);
        //折线图例 标签 设置
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);// 设置图例形状
        legend.setTextColor(Color.BLACK); //设置Legend 文本颜色
        legend.setTextSize(11f);//折线的对应名称字的大小
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);//设置图例的摆放位置 水平底部
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);  //垂直 居左
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//设置图例条目的排列方向
        legend.setDrawInside(false);//是否绘制在内部
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);//设置粒度  x轴间隔
        xAxis.setLabelCount(50);//x轴刻度数量
        //设置x轴最大值、最小值
//        xAxis.setAxisMinimum(0);
//        xAxis.setAxisMaximum(20);
        //取消右边y轴的显示
        rightAxis.setEnabled(false); //右侧Y轴不显示
        //设置网格颜色
        leftAxis.setGridColor(Color.WHITE); //网格线颜色
        leftAxis.setAxisLineColor(Color.BLACK); //Y轴颜色
        xAxis.setGridColor(Color.WHITE); //网格线颜色
        xAxis.setAxisLineColor(Color.BLACK); //X轴颜色
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
        //左侧y轴添加"%"样式
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return value+"%";
//            }
//        });
    }
    /**
     * 初始化折线(一条线)
     *
     * @param name
     * @param color
     */
    private void initLineDataSet(String name, int color) {

        lineDataSet = new LineDataSet(null, name);
        lineDataSet.setLineWidth(0.5f);//线宽
        lineDataSet.setDrawCircles(false);
        lineDataSet.setCircleRadius(5f);//圆形半径

        lineDataSet.setColor(color);//设置除了点之外折线的颜色
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);//false  实心  true  空心
        lineDataSet.setCircleColor(color);//设置折线上点的颜色也为绿色
        lineDataSet.setHighLightColor(color);
        //设置曲线填充
        lineDataSet.setDrawFilled(false);//就是曲线下面的颜色
        lineDataSet.setFillColor(color);//填充颜色
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.valueOf(value);
            }
        });
        lineDataSet.setDrawValues(true);//曲线上每个点的值，false取消显示
       // lineDataSet.set
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setValueTextSize(5f);//设置显示值的字体大小
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);//线模式为圆滑曲线（默认折线）

        //添加一个空的 LineData
        lineData = new LineData();
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
    /**
     * 动态添加数据（一条折线图）
     *
     * @param number
     */
    public void addEntry(int number) {

        //最开始的时候才添加 lineDataSet（一个lineDataSet 代表一条线）
        if (lineDataSet.getEntryCount() == 0) {
            lineData.addDataSet(lineDataSet);
        }
        lineChart.setData(lineData);
        Entry entry = new Entry(lineDataSet.getEntryCount(), number);
        lineData.addEntry(entry, 0);
        //通知数据已经改变
       lineData.notifyDataChanged();
      // lineChart.notifyDataSetChanged();

        //设置在曲线图中显示的最大数量
        if (lineDataSet.getEntryCount()<=30){
            lineChart.notifyDataSetChangedNew();
            lineChart.moveViewToX(0);
        }else{
            lineChart.notifyDataSetChangedNew();
            lineChart.moveViewToX(lineData.getEntryCount()-50);
//            lineChart.notifyDataSetChanged();
//            lineChart.moveViewToX(lineData.getEntryCount()-30);
        }
       lineChart.setVisibleXRangeMaximum(50);
        //移到某个位置
       // lineChart.moveViewToX(4);
       // lineChart.moveViewToAnimated(lineData.getEntryCount(),(float) number,YAxis.AxisDependency.LEFT, 500 );
        Log.d("xuejie",""+lineData.getEntryCount());
    }
    /**
     * 动态添加数据
     * 在一个LineChart中存放的折线，其实是以索引从0开始编号的
     *
     * @param yValues y值
     */
    public void addEntry( float yValues, int index) {

        // 通过索引得到一条折线，之后得到折线上当前点的数量

//最开始的时候才添加 lineDataSet（一个lineDataSet 代表一条线）
        if (lineDataSet.getEntryCount() == 0) {
            lineData.addDataSet(lineDataSet);
        }
        lineChart.setData(lineData);

        Entry entry = new Entry(lineDataSet.getEntryCount(), yValues); // 创建一个点
        lineData.addEntry(entry, index); // 将entry添加到指定索引处的折线中

        //通知数据已经改变
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.setVisibleXRangeMaximum(16);
        //把yValues移到指定索引的位置
        lineChart.moveViewToX(lineData.getEntryCount() - 5);
        //lineChart.moveViewToAnimated(xCount - 4, yValues, YAxis.AxisDependency.LEFT, 1000);// TODO: 2019/5/4 内存泄漏，异步 待修复
        lineChart.invalidate();
    }

    /**
     * 设置Y轴值
     *
     * @param max
     * @param min
     * @param labelCount
     */
    public void setYAxis(float max, float min, int labelCount) {
        if (max < min) {
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(labelCount, false);

        rightAxis.setAxisMaximum(max);
        rightAxis.setAxisMinimum(min);
        rightAxis.setLabelCount(labelCount, false);
        lineChart.invalidate();
    }
}
