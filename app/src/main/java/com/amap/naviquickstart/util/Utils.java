package com.amap.naviquickstart.util;

import android.text.Html;
import android.text.Spanned;

import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;

import java.text.DecimalFormat;
import java.util.List;

public class Utils {
    private static DecimalFormat fnum = new DecimalFormat("##0.0");
    public static final int AVOID_CONGESTION = 4;  // 躲避拥堵
    public static final int AVOID_COST = 5;  // 避免收费
    public static final int AVOID_HIGHSPEED = 6; //不走高速
    public static final int PRIORITY_HIGHSPEED = 7; //高速优先

    public static final int START_ACTIVITY_REQUEST_CODE = 1;
    public static final int ACTIVITY_RESULT_CODE = 2;

    public static final String INTENT_NAME_AVOID_CONGESTION = "AVOID_CONGESTION";
    public static final String INTENT_NAME_AVOID_COST = "AVOID_COST";
    public static final String INTENT_NAME_AVOID_HIGHSPEED = "AVOID_HIGHSPEED";
    public static final String INTENT_NAME_PRIORITY_HIGHSPEED = "PRIORITY_HIGHSPEED";


    public static String getFriendlyTime(int s) {
        String timeDes = "";
        int h = s / 3600;
        if (h > 0) {
            timeDes += h + "小时";
        }
        int min = (int) (s % 3600) / 60;
        if (min > 0) {
            timeDes += min + "分";
        }
        return timeDes;
    }

    public static String getFriendlyDistance(int m) {
        if (m < 1000) {
            return m + "米";
        }
        float dis = m / 1000f;
        String disDes = fnum.format(dis) + "公里";
        return disDes;
    }

    public static Spanned getRouteOverView(AMapNaviPath path) {
        String routeOverView = "";
        if (path == null) {
            Html.fromHtml(routeOverView);
        }

        int cost = path.getTollCost();
        if (cost > 0) {
            routeOverView += "过路费约<font color=\"red\" >" + cost + "</font>元";
        }
        int trafficLightNumber = getTrafficNumber(path);
        if (trafficLightNumber > 0) {
            routeOverView += "红绿灯" + trafficLightNumber + "个";
        }
        return Html.fromHtml(routeOverView);
    }

    public static int getTrafficNumber(AMapNaviPath path) {
        int trafficLightNumber = 0;
        if (path == null) {
            return trafficLightNumber;
        }
        List<AMapNaviStep> steps = path.getSteps();
        for (AMapNaviStep step : steps) {
            trafficLightNumber += step.getTrafficLightNumber();
        }
        return trafficLightNumber;
    }
}
