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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
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
        if (mData == null)
            return;

        // execute all drawing commands
        drawGridBackground(canvas);

        if (mAutoScaleMinMaxEnabled) {
            autoScale();
        }

        if (mAxisLeft.isEnabled())
            mAxisRendererLeft.computeAxis(mAxisLeft.mAxisMinimum, mAxisLeft.mAxisMaximum, mAxisLeft.isInverted());

        if (mAxisRight.isEnabled())
            mAxisRendererRight.computeAxis(mAxisRight.mAxisMinimum, mAxisRight.mAxisMaximum, mAxisRight.isInverted());

        if (mXAxis.isEnabled())
            mXAxisRenderer.computeAxis(mXAxis.mAxisMinimum, mXAxis.mAxisMaximum, false);

        drawBgColor(canvas);

        mXAxisRenderer.renderAxisLine(canvas);
        mAxisRendererLeft.renderAxisLine(canvas);
        mAxisRendererRight.renderAxisLine(canvas);

        if (mXAxis.isDrawGridLinesBehindDataEnabled())
            mXAxisRenderer.renderGridLines(canvas);

        if (mAxisLeft.isDrawGridLinesBehindDataEnabled())
            mAxisRendererLeft.renderGridLines(canvas);

        if (mAxisRight.isDrawGridLinesBehindDataEnabled())
            mAxisRendererRight.renderGridLines(canvas);

        if (mXAxis.isEnabled() && mXAxis.isDrawLimitLinesBehindDataEnabled())
            mXAxisRenderer.renderLimitLines(canvas);

        if (mAxisLeft.isEnabled() && mAxisLeft.isDrawLimitLinesBehindDataEnabled())
            mAxisRendererLeft.renderLimitLines(canvas);

        if (mAxisRight.isEnabled() && mAxisRight.isDrawLimitLinesBehindDataEnabled())
            mAxisRendererRight.renderLimitLines(canvas);

        // make sure the data cannot be drawn outside the content-rect
        int clipRestoreCount = canvas.save();
        canvas.clipRect(mViewPortHandler.getContentRect());

        mRenderer.drawData(canvas);

        if (!mXAxis.isDrawGridLinesBehindDataEnabled())
            mXAxisRenderer.renderGridLines(canvas);

        if (!mAxisLeft.isDrawGridLinesBehindDataEnabled())
            mAxisRendererLeft.renderGridLines(canvas);

        if (!mAxisRight.isDrawGridLinesBehindDataEnabled())
            mAxisRendererRight.renderGridLines(canvas);

        // if highlighting is enabled
        if (valuesToHighlight())
            mRenderer.drawHighlighted(canvas, mIndicesToHighlight);

        // Removes clipping rectangle
        canvas.restoreToCount(clipRestoreCount);

        mRenderer.drawExtras(canvas);

        if (mXAxis.isEnabled() && !mXAxis.isDrawLimitLinesBehindDataEnabled())
            mXAxisRenderer.renderLimitLines(canvas);

        if (mAxisLeft.isEnabled() && !mAxisLeft.isDrawLimitLinesBehindDataEnabled())
            mAxisRendererLeft.renderLimitLines(canvas);

        if (mAxisRight.isEnabled() && !mAxisRight.isDrawLimitLinesBehindDataEnabled())
            mAxisRendererRight.renderLimitLines(canvas);

        mXAxisRenderer.renderAxisLabels(canvas);
        mAxisRendererLeft.renderAxisLabels(canvas);
        mAxisRendererRight.renderAxisLabels(canvas);

        if (isClipValuesToContentEnabled()) {
            clipRestoreCount = canvas.save();
            canvas.clipRect(mViewPortHandler.getContentRect());

            mRenderer.drawValues(canvas);

            canvas.restoreToCount(clipRestoreCount);
        } else {
            mRenderer.drawValues(canvas);
        }

        mLegendRenderer.renderLegend(canvas);

        drawDescription(canvas);

        drawMarkers(canvas);
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

    @Override
    protected void drawMarkers(Canvas canvas) {
        // if there is no marker view or drawing marker is disabled
        if (mMarker == null || !isDrawMarkersEnabled() || !valuesToHighlight())
            return;

        // 三个Marker合成一个：获取最高点，在最高点上画框
        List<float[]> list = new ArrayList<>();
        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];

            IDataSet set = mData.getDataSetByIndex(highlight.getDataSetIndex());

            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
            int entryIndex = set.getEntryIndex(e);

            // make sure entry not null
            if (e == null || entryIndex > set.getEntryCount() * mAnimator.getPhaseX())
                continue;

            float[] pos = getMarkerPosition(highlight);

            // check bounds
            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                continue;

            list.add(pos);
        }

        if (list.size() > 0) {
            // 比较Y轴值，在最小值上画标记View
            float xPos = 0;
            float yPosMin = list.get(0)[1];
            for (float[] pos : list) {
                xPos = pos[0];
                if (pos[1] <= yPosMin) {
                    yPosMin = pos[1];
                }
            }

            // 确定要绘制的内容样式
            // callbacks to update the content
            // mMarker.refreshContent(e, highlight);

            // draw the marker
            mMarker.draw(canvas, xPos, yPosMin);
        }
    }
}
