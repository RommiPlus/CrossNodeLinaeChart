package com.example.ast.customchart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    private CustomLineChart mChart;

    private ArrayList<Entry> values = new ArrayList<>();
    private ArrayList<Entry> values2 = new ArrayList<>();

    private TextView textView;
    private float x_tmp1;
    private float y_tmp1;
    private float x_tmp2;
    private float y_tmp2;
    private LineData lineData;
    private XAxis xAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        mChart = (CustomLineChart) findViewById(R.id.test_lineChart);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawBorders(false);

        //相关设置
        //在节点绘制叉号
        mChart.setDrawCross(true);
        //设置叉号线条长度
        mChart.setCrossLength(5f);
        //设置叉号线条宽度
        mChart.setCrossWidth(1f);
        //给表格背景添加颜色
        mChart.setDrawBgColor(true);
        //设置背景颜色的属性
        mChart.setBgColor(getBg());


        xAxis = mChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(22f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(23);
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setDrawGridLines(false);
//        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(true);
        xAxis.setYOffset(20f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyYAxisValueFormatter());

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setXOffset(20f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setLabelCount(5, true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setXOffset(20f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setLabelCount(5, true);
//        leftAxis.removeAllLimitLines();
//        leftAxis.setAxisMaximum(80f);
//        leftAxis.setAxisMinimum(20f);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setYOffset(40f);
//        leftAxis.setGranularity(10f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);
//        leftAxis.setDrawAxisLine(false);
//        leftAxis.setDrawLabels(true);
//        mChart.getAxisRight().setEnabled(false);

        values.add(new Entry(0, 12));
        values.add(new Entry(1, 43));
        values.add(new Entry(2, 55));
        values.add(new Entry(3, 13));
        values.add(new Entry(4, 34));
//        values.add(new Entry(5, 33));
        values.add(new Entry(6, 65));
        values.add(new Entry(7, 25));
        values.add(new Entry(8, 34));
        values.add(new Entry(9, 8));
        values.add(new Entry(10, 34));
        values.add(new Entry(11, 33));
        values.add(new Entry(12, 75));
        values.add(new Entry(13, 34));
        values.add(new Entry(14, 33));
        values.add(new Entry(15, 100));
        values.add(new Entry(16, 33));
        values.add(new Entry(17, 14));
        values.add(new Entry(18, 56));
        values.add(new Entry(19, 76));
        values.add(new Entry(20, 46));
        values.add(new Entry(21, 33));
        values.add(new Entry(22, 46));

        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        LineDataSet set1 = new LineDataSet(values, "");

//        set1.enableDashedLine(10f, 5f, 0f);
//        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.parseColor("#7DADE6"));
        set1.setDrawCircles(false);
        set1.setLineWidth(2f);
        set1.setDrawValues(false);
        set1.setDrawFilled(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set1.setFormLineWidth(1f);
//        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//        set1.setFormSize(15.f);


        values2.add(new Entry(0, 23));
        values2.add(new Entry(1, 43));
        values2.add(new Entry(2, 55));
        values2.add(new Entry(3, 16));
        values2.add(new Entry(4, 34));
//        values2.add(new Entry(5, 33));
        values2.add(new Entry(6, 55));
        values2.add(new Entry(7, 25));
        values2.add(new Entry(8, 24));
        values2.add(new Entry(9, 78));
        values2.add(new Entry(10, 34));
        values2.add(new Entry(11, 33));
        values2.add(new Entry(12, 35));
        values2.add(new Entry(13, 34));
        values2.add(new Entry(14, 16));
        values2.add(new Entry(15, 25));
        values2.add(new Entry(16, 33));
        values2.add(new Entry(17, 75));
        values2.add(new Entry(18, 56));
        values2.add(new Entry(19, 76));
        values2.add(new Entry(20, 35));
        values2.add(new Entry(21, 12));
        values2.add(new Entry(22, 46));
        LineDataSet set2 = new LineDataSet(values2, "");
        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);

        final ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        lineData = new LineData(dataSets);

        set1.setHighlightEnabled(true); // allow highlighting for DataSet

        // set this to false to disable the drawing of highlight indicator (lines)
        set1.setDrawHighlightIndicators(false);
        set1.setHighLightColor(Color.BLACK); // color for highlight indicator
        set1.setHighlightLineWidth(10f);

        // 设置X轴滚动
        mChart.setVisibleXRangeMinimum(7);
        mChart.setVisibleXRangeMaximum(8);

        mChart.setRendererLeftYAxis(new FatYAxisRenderer(
                mChart.getViewPortHandler(), mChart.getAxisLeft(),
                mChart.getTransformer(YAxis.AxisDependency.LEFT)));
        mChart.setExtraTopOffset(40f);
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setMarker(new FatMarkerView(this, R.layout.layout_marker_view));
        mChart.setData(lineData);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Highlight highlight1 = new Highlight(e.getX(), 0, -1);
                Highlight highlight2 = new Highlight(e.getX(), 1, -1);
                Highlight[] array = new Highlight[]{highlight1, highlight2};
                mChart.highlightValues(array);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mChart.setDragDecelerationEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        mChart.setOnChartGestureListener(new OnChartGestureListener() {
            public boolean isChartLoadDataEnable;
            public boolean isInChartGesture;
            public float lastRightIndex;
            public float lastLeftIndex;
            public float lastDx;

            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                isChartLoadDataEnable = false;
                isInChartGesture = true;
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                isInChartGesture = false;
                float leftXIndex = mChart.getLowestVisibleX();    //获取可视区域中，显示在x轴最右边的index

                // 手势动作结束，加载更多数据
                if (lastPerformedGesture == ChartTouchListener.ChartGesture.DRAG) {
                    if (leftXIndex <= 0) {
                        isChartLoadDataEnable = false;
                        addData();
                    } else {
                        isChartLoadDataEnable = true;
                    }
                }
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                float leftXIndex = mChart.getLowestVisibleX();     //获取可视区域中，显示在x轴最右边的index
                float rightXIndex = mChart.getHighestVisibleX();    //获取可视区域中，显示在x轴最右边的index
                if (isChartLoadDataEnable) {
                    if (leftXIndex <= 0) {
                        isChartLoadDataEnable = false;
                        //加载更多数据的操作
                        addData();
                    }
                }

                Log.v("TAG", "leftXIndex: " + leftXIndex + " lastLeftIndex: " + lastLeftIndex
                        + "\nrightXIndex: " + rightXIndex + " lastRightIndex: " + lastRightIndex
                        + "\n dX:" + dX + " lastDx: " + lastDx);

                // 如果在一次手势动作期间按照下面逻辑走，一次手势动作结束，将lastDx重置方便重新计算
                // 每次次手势动作的流程都会通过dx来表示
                if (!isInChartGesture) {
                    lastDx = 0;
                }

                // 往左划并且图表向左移动时
                if (dX - lastDx < 0 && rightXIndex - lastRightIndex > 0) {
                    Log.v("TAG", "往左划并且图表向左移动时");
                    textView.setText(String.valueOf(leftXIndex));

                    lastDx = dX;
                    lastLeftIndex = leftXIndex;
                    lastRightIndex = rightXIndex;
                }

                // 往右划并且图表向右移动时
                if (dX - lastDx > 0 && leftXIndex - lastLeftIndex < 0) {
                    Log.v("TAG", "往右划并且图表向右移动时");
                    textView.setText(String.valueOf(leftXIndex));

                    lastDx = dX;
                    lastRightIndex = rightXIndex;
                    lastLeftIndex = leftXIndex;
                }
            }
        });
    }

    private void addData() {
        //加载更多数据的操作
        xAxis.setAxisMinimum(-10f);
        xAxis.setAxisMaximum(22f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(33);

        List<Entry> list = new ArrayList<>();
        list.add(new Entry(-1, 12));
        list.add(new Entry(-2, 12));
        list.add(new Entry(-3, 45));
        list.add(new Entry(-4, 12));
        list.add(new Entry(-5, 12));
        list.add(new Entry(-6, 45));
        list.add(new Entry(-7, 12));
        list.add(new Entry(-8, 45));
        list.add(new Entry(-9, 12));
        list.add(new Entry(-10, 23));
        values.addAll(0, list);
        Collections.sort(values, new EntryXComparator());
        lineData.notifyDataChanged(); // let the data know a dataSet changed
        mChart.notifyDataSetChanged(); // let the chart know it's data changed
        mChart.invalidate(); // refresh

        // 设置X轴滚动
        mChart.setVisibleXRangeMinimum(7);
        mChart.setVisibleXRangeMaximum(8);
    }

    /**
     * 分段背景设置
     *
     * @return 每条背景的组合
     */
    private ArrayList<BgColor> getBg() {
        ArrayList<BgColor> bgList = new ArrayList<>();

        bgList.add(new BgColor(0, 20, Color.parseColor("#FAE8E3")));//参数信息：纵坐标从0到20设置颜色为黄色
        bgList.add(new BgColor(20, 40, Color.parseColor("#F7F2DF")));//支持16进制颜色
        bgList.add(new BgColor(40, 70, Color.parseColor("#DDF4E5")));
        bgList.add(new BgColor(70, 110, Color.parseColor("#E1F2FB")));
        return bgList;
    }
}
