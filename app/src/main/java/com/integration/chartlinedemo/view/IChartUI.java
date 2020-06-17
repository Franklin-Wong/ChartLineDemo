package com.integration.chartlinedemo.view;

import com.integration.chartlinedemo.model.ChartDataInfo;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/5/19.
 */
public interface IChartUI {

    void showChartData(List<ChartDataInfo> dataInfoList);
}
