package io.sbed.common.utils.ppt;

import java.util.List;

/**
 * @author heguoliang
 * @Description: TODO(图表系列数据)
 * @date 2017-11-30 11:07
 */
public class GraphData {

    // 圆饼图
    public static final String GRAPH_PIE = "pie";
    // 柱状图
    public static final String GRAPH_BAR = "bar";
    // 线性图
    public static final String GRAPH_LINE = "line";
    // 面积图
    public static final String GRAPH_AREA = "area";
    // 雷达图
    public static final String GRAPH_RADAR = "radar";

    // 图形标题
    private String title;

    // 系列值
    private List<SeriesData> serList;

    public GraphData(String title, List<SeriesData> serList) {
        this.title = title;
        this.serList = serList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SeriesData> getSerList() {
        return serList;
    }

    public void setSerList(List<SeriesData> serList) {
        this.serList = serList;
    }

}

class SeriesData {
    // 系列名称
    private String serName;

    // 系列值
    private double serVal;

    public SeriesData(String serName, double serVal) {
        this.serName = serName;
        this.serVal = serVal;
    }

    public String getSerName() {
        return serName;
    }

    public void setSerName(String serName) {
        this.serName = serName;
    }

    public double getSerVal() {
        return serVal;
    }

    public void setSerVal(double serVal) {
        this.serVal = serVal;
    }

}
