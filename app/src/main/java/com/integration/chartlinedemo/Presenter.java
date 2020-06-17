package com.integration.chartlinedemo;

import com.integration.chartlinedemo.model.ChartDataImpl;
import com.integration.chartlinedemo.model.ChartDataInfo;
import com.integration.chartlinedemo.view.IChartUI;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/5/18.
 */
public class Presenter {

    private IChartUI mIChartUI;
    private ChartDataImpl mChartData;

    public Presenter(IChartUI IChartUI) {
        mIChartUI = IChartUI;
        mChartData = new ChartDataImpl();
    }


    public void showChartData() {

        int size = 50;

        List<ChartDataInfo> chartData = mChartData.getChartData(size);

        mIChartUI.showChartData(chartData);


    }
}
