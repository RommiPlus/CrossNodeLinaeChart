package com.example.ast.customchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoniu on 2017/4/10.
 * 自定义的LineChart，添加了在节点绘制叉号和绘制背景的功能
 */

public class CustomLineChart extends LineChart {

    private static final String TAG = "CustomLineChart";

    private List<Entry> list;

    private boolean enableDrawCross = false;
    private float crossWidth = 3;
    private float crossLength = 30;

    private boolean enableDrawBgColor = false;
    private ArrayList<BgColor> bgList = new ArrayList<>();

    public CustomLineChart(Context context) {
        super(context);
    }

    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new FatLineChartRender(this, mAnimator, mViewPortHandler);
    }

    /**
     * 重写onDraw方法，注意绘制顺序，先绘制背景色，再绘制叉号，最后绘制图表
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        drawBgColor(canvas);
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
    }

    public void setDrawCross(boolean enabled) {
        enableDrawCross = enabled;
    }

    /**
     * 设置绘制叉号线条的宽度
     *
     * @param width 单位dp
     */
    public void setCrossWidth(float width) {
        crossWidth = Utils.convertDpToPixel(width);
    }

    /**
     * 设置绘制叉号线条的长度
     *
     * @param length 单位dp
     */
    public void setCrossLength(float length) {
        crossLength = Utils.convertDpToPixel(length);
    }

    public void setDrawBgColor(boolean enabled) {
        enableDrawBgColor = enabled;
    }

    /**
     * 设置背景时应以BgColor为单位，将多个BgColor添加到一个ArrayList中
     * 最后通过此方法给图表加上颜色背景
     *
     * @param arrayList 背景色集合
     */
    public void setBgColor(ArrayList<BgColor> arrayList) {
        bgList = arrayList;
    }

    private void drawBgColor(Canvas canvas) {
        if (enableDrawBgColor) {
            if (!bgList.isEmpty()) {
                Paint paint = new Paint();
                for (BgColor r : bgList) {
                    MPPointD pStart = this.getPixelForValues(this.getXChartMin(), r.getStart(), YAxis.AxisDependency.LEFT);
                    MPPointD pStop = this.getPixelForValues(this.getXChartMax(), r.getStop(), YAxis.AxisDependency.LEFT);

                    paint.setColor(r.getColor());

                    double startY = correctCoordinates(pStart.y);
                    double stopY = correctCoordinates(pStop.y);
                    canvas.drawRect(new RectF(mViewPortHandler.contentLeft(), (float) startY, mViewPortHandler.contentRight(), (float) stopY), paint);
                }
            } else {
                Log.i(TAG, "No BgColor to Draw");
            }
        }
    }

    private double correctCoordinates(double y) {
        if (y <= mViewPortHandler.contentTop()) {
            y = mViewPortHandler.contentTop();
        } else if (y >= mViewPortHandler.contentBottom()) {
            y = mViewPortHandler.contentBottom();
        }
        return y;
    }
}
