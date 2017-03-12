/* ===========================================================
 * $Id: enterpriseChartAction.java 508 2009-08-19 09:14:18Z bitorb $
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.enterprise.action;

import org.gaixie.micrite.action.GenericAction;
import org.gaixie.micrite.beans.Enterprise;
import org.gaixie.micrite.enterprise.service.IEnterpriseService;
import org.gaixie.micrite.jfreechart.style.BarStyle;
import org.gaixie.micrite.jfreechart.style.LineStyle;
import org.gaixie.micrite.jfreechart.style.PieStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 用户来源图形报表
 */
public class EnterpriseChartAction extends GenericAction {
    private static final long serialVersionUID = -8118104364113464883L;
    /**
     * 折线图测试数据
     * @return
     */
    private static XYDataset createDataset()
    {
        TimeSeries timeseries = new TimeSeries("每分钟数据", org.jfree.data.time.Minute.class);
        Hour hour = new Hour();
        timeseries.add(new Minute(1, hour), 10.199999999999999D);
        timeseries.add(new Minute(3, hour), 17.300000000000001D);
        timeseries.add(new Minute(9, hour), 14.6D);
        timeseries.add(new Minute(11, hour), 11.9D);
        timeseries.add(new Minute(15, hour), 13.5D);
        timeseries.add(new Minute(19, hour), 10.9D);
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeseries);
        return timeseriescollection;
    }
    @Autowired
    private IEnterpriseService enterpriseService;
private JFreeChart chart;

    //    private Map<String,Object> resultMap = new HashMap<String,Object>();
    private Enterprise enterprise;
    
    /**
     * 2D柱图
     * @return "success"
     */
    public String getBarChart(){
        CategoryDataset dca = enterpriseService.getEnterpriseSourceBarDataset(this.getQueryBean());
        chart = ChartFactory.createBarChart(
                    getText("企业资质分类"), 
                    getText("资质类型"), 
                    getText("企业个数"),
                    dca, 
                    PlotOrientation.VERTICAL,
                    true, false, false);
        BarStyle.styleOne(chart);
        this.putChartResultList(chart);
        return SUCCESS ;
    }
    public JFreeChart getChart() {
        return chart;
    }
    public Enterprise getEnterprise() {
        return enterprise;
    }
    
    /**
     * 测试折线图
     * @return
     */
    public String getLineChart(){
        chart = ChartFactory.createTimeSeriesChart("时段 例子 10",
                "时间", 
                "数值",
                createDataset(),
                true,
                true,
                false);
        LineStyle.styleOne(chart);
        this.putChartResultList(chart);
        return SUCCESS ;
    }

    /**
     * 2D饼图
     * @return "success"
     */
    public String getPieChart(){
        PieDataset pd = enterpriseService.getEnterpriseSourcePieDataset(this.getQueryBean());
        chart = ChartFactory.createPieChart(
                    getText("企业资质分类"),
                    pd,
                    true,
                    true,
                    false);
        PieStyle.styleOne(chart);
        this.putChartResultList(chart);
        return SUCCESS ;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

}
