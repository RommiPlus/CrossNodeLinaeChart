package com.example.ast.customchart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class FatYAxisRenderer extends YAxisRenderer {
    public FatYAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
    }

    Paint labelBackgroundPaint = new Paint();
    Paint unitPaint = new Paint();

    @Override
    protected void drawYLabels(Canvas c, float fixedPosition, float[] positions, float offset) {
        // draw
        drawLabelBackground(c, fixedPosition);

        for (int i = 0; i < mYAxis.mEntryCount; i++) {

            String text = mYAxis.getFormattedLabel(i);

            if (!mYAxis.isDrawTopYLabelEntryEnabled() && i >= mYAxis.mEntryCount - 1)
                return;

            // 处于图表区域范围内时绘制坐标
            float y = positions[i * 2 + 1] + offset;
//            if (y >= mViewPortHandler.contentTop() && y <= mViewPortHandler.contentBottom()) {
                c.drawText(text, fixedPosition, y, mAxisLabelPaint);
//            }
        }

        drawUnit(c, fixedPosition);
    }

    /**
     * 画背景色g
     * 新建画笔,画笔宽度比文字大5号
     * 开始点:(画文字的X轴坐标，图表的上边缘坐标)，结束点:(画文字的X轴坐标，图表的下边缘坐标)
     */
    protected void drawLabelBackground(Canvas c, float fixedPosition) {
        // 画的起始点：fixedPosition - （画的线宽度 - 文字宽度）
        labelBackgroundPaint.setStrokeWidth(Utils.convertDpToPixel(25f));
        labelBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        labelBackgroundPaint.setColor(Color.parseColor("#F0ACC1"));
        labelBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        float startX = fixedPosition - (
                labelBackgroundPaint.getStrokeWidth() - mAxisLabelPaint.measureText("10")) / 2;
        c.drawLine(
                startX,
                mViewPortHandler.contentTop(),
                startX,
                mViewPortHandler.contentBottom(),
                labelBackgroundPaint);
    }

    /**
     * 文字绘制位置：(背景开始位置，图表上方位置，距离背景18dp)
     *
     * @param c
     * @param fixedPosition
     */
    protected void drawUnit(Canvas c, float fixedPosition) {
        unitPaint.setTextSize(Utils.convertDpToPixel(14f));
        unitPaint.setColor(Color.parseColor("#F0ACC1"));

        float startX = fixedPosition - (
                labelBackgroundPaint.getStrokeWidth() - mAxisLabelPaint.measureText("10")) / 2
                - unitPaint.measureText("体重(kg)") / 2;
        c.drawText("体重(kg)", startX,
                (mViewPortHandler.contentTop() - Utils.convertDpToPixel(18f)), unitPaint);
    }
}
