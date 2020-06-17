package com.integration.chartlinedemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Wongerfeng on 2020/5/18.
 */
public class ChartDataImpl implements IChartData {
    public static final int MAX_NUM = 100;

    @Override
    public List<ChartDataInfo> getChartData(int size) {

        List<ChartDataInfo> dataInfoList = new ArrayList<>();
        Random random = new Random();

        random.setSeed(ChartDateUtil.getDateNow());

        //获取最大值以内的数
        for (int i = 0; i < size - 1; i--) {

            ChartDataInfo chartDataInfo = new ChartDataInfo(ChartDateUtil.getDate(i), random.nextInt(MAX_NUM));

            dataInfoList.add(chartDataInfo);
        }

        return dataInfoList;
    }
}
