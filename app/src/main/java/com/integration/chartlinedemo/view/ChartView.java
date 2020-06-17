package com.integration.chartlinedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.integration.chartlinedemo.model.ChartDataInfo;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/5/18.
 */
public class ChartView extends View {

    ///定义画布宽度
    private int width;
    private int height;
    //设置边距
    private int padding = 100;

    private Paint mPaint;

    List<ChartDataInfo> mDataInfoList;


    public ChartView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }


    private void initPaint() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);

    }

    public void setData(List<ChartDataInfo> dataInfoList) {

        mDataInfoList = dataInfoList;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算获取画布的尺寸
        width = getWidth() - padding * 2;
        height = getHeight() - padding * 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //使用绘制图像的工具类
        DataChartUtil.getInstance().drawChartMethod(mPaint, canvas, width, height, padding, mDataInfoList);

    }
}
