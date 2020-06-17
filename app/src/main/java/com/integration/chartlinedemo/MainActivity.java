package com.integration.chartlinedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.integration.chartlinedemo.model.ChartDataInfo;
import com.integration.chartlinedemo.view.ChartView;
import com.integration.chartlinedemo.view.IChartUI;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IChartUI {


    private Presenter mPresenter;
    ChartView cv;
    Button btn;
    Button btnSurface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

        initEvent();



    }
    //初始化控件
    private void initView() {
        cv = (ChartView) findViewById(R.id.cv);
        btn = (Button) findViewById(R.id.btn);
        btnSurface = (Button) findViewById(R.id.btnSurface);
    }

    private void initEvent() {

        btnSurface.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SurfaceChartActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showChartData();
            }
        });

    }

    private void initData() {
        mPresenter = new Presenter(this);

    }

    @Override
    public void showChartData(List<ChartDataInfo> dataInfoList) {

        cv.setData(dataInfoList);


    }
}
