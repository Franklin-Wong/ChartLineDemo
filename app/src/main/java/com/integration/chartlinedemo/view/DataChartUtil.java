package com.integration.chartlinedemo.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.integration.chartlinedemo.model.ChartDataInfo;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/5/20.
 *
 此类是个绘图工具类，只是包括绘制的方法，而画布、画笔等参数需要外界传入
 1.getInstance方法，获得该类的单例（线程安全的单例）
 2.drawChart方法，是对外提供的绘图入口方法
 接收外界传参并判断合法性
 调用绘制图表背景的方法
 调用绘制图表线的方法
 3.drawBg，绘制背景方法，包含两部分：背景色、背景边框
 背景色是直接填充的方式，不用画笔
 4.drawBgAxisLine，绘制背景边框线
 横线纵线各画5+1条，每一条线，我们可认为是画笔走过的路径，
 那么，我们可以把每一条路径封装起来，放入集合中。
 我们不需要自己定义这种集合，直接使用系统提供的Path就可以了
 Path有几个常用的方法：
 MoveTo(float dx, float dy) 直接移动至某个点，中间不会产生连线；
 LineTo(float dx, float dy) 使用直线连接至某个点；
 QuadTo(float dx1, float dy1, float dx2, float dy2) 使用曲线连接至某个点（贝塞尔曲线）；
 CubicTo(float x1,float y1,float x2,float y2,float x3,float y3)
 使用曲线连接至某个点，参数更多而已；
 5.画笔的设置，方法比较多，此处只列咱们用到的
 paint = new Paint(Paint.ANTI_ALIAS_FLAG);抗锯齿，如不设置，界面粗糙有锯齿效果；
 paint.setStrokeWidth(2);设置描边的宽度
 paint.setStyle(STROKE);
 设置样式，主要包括实心、描边、实心和描边3种类型，画线一般设置成描边即可；
 paint.setColor(Color.parseColor(color_bg_line));//设置颜色
 6.drawLine画曲线，主要将数据（集合index和数值大小）分别对应到坐标系的坐标
 X轴按照集合的下标平分X轴长度；
 Y轴根据最大最小值定位数值的位置；
 画线仍然使用Path，要比每根曲线单独画要更合适一些；
 7.绘制文本
 paint.setStyle(Paint.Style.FILL);
 画笔可调整成实心，绘制文本更美观，当然也可其他类型，请根据喜好自行调整；
 float width_text = paint.measureText(end);
 通过设置画笔参数和文本内容，使用画笔的measureText方法可以精确计算出文本的实际宽度；
 文本的坐标与其他图形有差异，绘制位置是基于文本的Baseline，
 此处曲线文本的绘制时，文本位置未做精确处理；
 而日期的绘制时，文本位置是做了精确处理的；
 float y_start = canvasHeight + padding - paint.descent() - paint.ascent() +10;
 如果想对文本位置控制的更精确，请参考文章：https://www.jianshu.com/p/3e48dd0547a0


 */
public class DataChartUtil {

    private Paint mPaint;
    private Canvas mCanvas;
    private int canvasWidth;
    private int canvasHeight;
    private int padding;


    //背景色
    private final String color_bg = "#343643";
    //背景色
    private final String color_bg_line = "#999dd2";
    //线颜色
    private final String color_line = "#7176ff";
    //文本颜色
    private final String color_text = "#ffffff";

    private List<ChartDataInfo> mDataInfoList;
    public static DataChartUtil mDataChartUtil;

    private DataChartUtil() {
    }

    //获取单例
    public static DataChartUtil getInstance() {
        if (mDataChartUtil == null) {
            synchronized (DataChartUtil.class) {
                if (mDataChartUtil == null) {
                    mDataChartUtil = new DataChartUtil();
                }
            }
        }
        return mDataChartUtil;
    }


    public void drawChartMethod(Paint paint, Canvas canvas, int canvasWidth, int canvasHeight, int padding, List<ChartDataInfo> dataInfoList) {
        mPaint = paint;
        mCanvas = canvas;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.padding = padding;
        mDataInfoList = dataInfoList;
        if (mPaint == null || mCanvas == null || mDataInfoList == null || this.canvasWidth <=0
                || this.canvasHeight <=0 || this.padding <=0) {
            return;
        }
        //画背景
        drawBackground();
        //画图表线
        drawLines();

    }
    private void drawBackground() {
        //绘制背景颜色
        mCanvas.drawColor(Color.parseColor(color_bg));

        //绘制背景坐标轴
        drawBgAxisLine();

    }

    private void drawBgAxisLine() {

        //5条横线 和纵线
        int lineNum = 5;
        //计算横线的间隔
        int y_space = canvasHeight / lineNum;
        //计算纵线的间隔
        int x_space = canvasWidth / lineNum;

        //创建路线对象
        Path path = new Path();
        //画横线
        for (int i = 0; i < lineNum; i++) {
            //确定起始点
            path.moveTo(0 + padding, i * y_space + padding);
            //确定终点
            path.lineTo(canvasWidth + padding, i * y_space + padding);

        }
        //画纵线
        for (int i = 0; i < lineNum; i++) {
            //确定起始点
            path.moveTo(i * x_space + padding, 0 + padding);
            //确定终点
            path.lineTo(i * x_space,canvasHeight + padding);
        }


        //设置画笔的属性 颜色 样式
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(color_bg_line));

        //画布开始绘制路线
        mCanvas.drawPath(path,mPaint);


    }

    //绘制图表数据曲线
    private void drawLines() {

        if (mDataInfoList == null) {
            return;
        }

        int size = mDataInfoList.size();
        //画布应该自动适应图表数据
        //x轴间隔
        float x_space = canvasWidth / size;

        //y轴最大间隔和最小间隔区间
        float max = getMaxData();
        float min = getMinData();

        float pre_x = 0;
        float pre_y = 0;

        Path path = new Path();

        //从左到右
        //获取坐标数值，转化为图表
        for (int i = 0; i < size; i++) {

            int num = mDataInfoList.get(i).getNum();
            float x = (i * x_space) + ( x_space / 2) + padding;
            float y = (num - min) / (max - min) * canvasHeight + padding;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.quadTo(pre_x, pre_y, x, y);
            }

            pre_x = x;
            pre_y = y;

            drawText(String.valueOf(mDataInfoList.get(i).getNum()), x, y);

        }
        //数值画笔属性
        mPaint.setColor(Color.parseColor(color_line));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        //绘制
        mCanvas.drawPath(path, mPaint);

        //绘制文字
        drawAxisText();
    }

    private void drawAxisText() {

        //确定起始时间
        String start = mDataInfoList.get(0).getDate();
        String end = mDataInfoList.get(mDataInfoList.size() - 1).getDate();

        //画笔属性
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor(color_text));
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(40);

        //测量文字
        float textWidth = mPaint.measureText(end);
        //开始文本位置
        float x_start = padding;
        float y_start = 0;
        //绘制开始文本
        mCanvas.drawText(start, x_start, y_start, mPaint);

        //结束文本位置
        float x_end = canvasWidth + padding - textWidth;
        float y_end = canvasHeight + padding-mPaint.descent()-mPaint.ascent() +10;
        mCanvas.drawText(end, x_end, y_end, mPaint);

    }

    private void drawText(String valueOf, float x, float y) {
        mPaint.setTextSize(30);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.parseColor(color_text));
        mCanvas.drawText(valueOf, x, y, mPaint);

    }

    private float getMinData() {

        int min = mDataInfoList.get(0).getNum();
        for (ChartDataInfo data : mDataInfoList) {

            min = data.getNum() < min? data.getNum() : min;
        }
        return min;
    }

    private float getMaxData() {

        int max = mDataInfoList.get(0).getNum();
        for (ChartDataInfo info: mDataInfoList) {
            max = info.getNum() > max ? info.getNum() : max;
        }

        return max;
    }


}
