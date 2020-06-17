package com.integration.chartlinedemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.integration.chartlinedemo.model.ChartDataInfo;
import com.integration.chartlinedemo.view.ChartSurfaceView;
import com.integration.chartlinedemo.view.IChartUI;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SurfaceChartActivity extends AppCompatActivity implements IChartUI {

    Presenter mPresenter;
    ChartSurfaceView mChartSurfaceView;
    Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_chart);

        //初始化presenter
        mPresenter = new Presenter(this);
        //初始化控件
        initView();
        //初始化数据
        initData();
        //初始化事件
        initEvent();

    }

    private void initEvent() {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initData() {

        mPresenter.showChartData();

    }

    private void initView() {
        mChartSurfaceView = (ChartSurfaceView) findViewById(R.id.cv);
        btn = (Button) findViewById(R.id.btn);


    }

    @Override
    public void showChartData(List<ChartDataInfo> dataInfoList) {

        mChartSurfaceView.setDataSource(dataInfoList);

    }
}
