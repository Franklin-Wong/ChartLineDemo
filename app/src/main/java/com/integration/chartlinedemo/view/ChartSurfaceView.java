package com.integration.chartlinedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.integration.chartlinedemo.model.ChartDataInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Wongerfeng on 2020/6/16.
 */
public class ChartSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder mSurfaceHolder;

    List<ChartDataInfo> mDataInfos;
    List<ChartDataInfo> mShowData;


    ExecutorService mExecutorServicePool;
    Timer mTimer;

    Paint mPaint;
    Canvas mCanvas;

    int mCanvasWidth;
    int mCanvasHeight;
    int padding = 100;

    public ChartSurfaceView(Context context) {
        super(context);

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setTextSize(30);

    }

    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setKeepScreenOn(true);
        mExecutorServicePool = Executors.newCachedThreadPool();
    }

    public ChartSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initPaint();
    }

    public void setDataSource( List<ChartDataInfo> dataInfos) {
        mDataInfos = dataInfos;
        mShowData = new ArrayList<>();
        if (mTimer != null) {
            mTimer.cancel();
        }

        //判断画布尺寸
        if (mCanvasWidth > 0) {
            startTimer();

        }
    }
    int index;
    private void startTimer() {

        //创建timer任务

        index = 0;

        mTimer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                index += 1;
                mShowData.clear();
                mShowData.add((ChartDataInfo) mDataInfos.subList(0, index));

                //
                mExecutorServicePool.execute(new ChartRunnable());

                if (index > mDataInfos.size()) {
                    mTimer.cancel();
                }
            }
        };

        mTimer.schedule(timerTask, 0, 20);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCanvasWidth = mCanvasWidth - 2 * padding;
        mCanvasHeight = mCanvasHeight - 2 * padding;
        startTimer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private class ChartRunnable implements Runnable {
        @Override
        public void run() {

            //获取画布
            mSurfaceHolder.lockCanvas();
            //绘制曲线
            DataChartUtil.getInstance()
                    .drawChartMethod(mPaint, mCanvas, mCanvasWidth, mCanvasHeight, padding, mShowData);
            //提交画布
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);

        }
    }
}
