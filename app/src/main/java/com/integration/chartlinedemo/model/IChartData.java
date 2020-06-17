package com.integration.chartlinedemo.model;

import java.util.List;

/**
 * Created by Wongerfeng on 2020/5/18.
 */
public interface IChartData {

    List<ChartDataInfo> getChartData(int size);
}
