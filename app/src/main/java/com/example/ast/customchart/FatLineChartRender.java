package com.example.ast.customchart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class FatLineChartRender extends LineChartRenderer {

    Paint mHighLightCirclePaint = new Paint();

    public FatLineChartRender(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        mHighLightCirclePaint.setColor(Color.parseColor("#FFFFFF"));
        mHighLightCirclePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void drawCircles(Canvas c) {
        super.drawCircles(c);
    }

    @Override
    protected void drawHighlightLines(Canvas c, float x, float y, ILineScatterCandleRadarDataSet set) {
        // set color and stroke-width
        mHighlightPaint.setColor(set.getHighLightColor());
        mHighlightPaint.setStrokeWidth(set.getHighlightLineWidth());
        mHighlightPaint.setStyle(Paint.Style.FILL);

        // 描绘选中点：先描绘白色中间圆，再描绘周边圆环线条
        c.drawCircle(x, y, Utils.convertDpToPixel(5), mHighlightPaint);
        c.drawCircle(x, y, Utils.convertDpToPixel(4), mHighLightCirclePaint);
    }
}
