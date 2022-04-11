package com.ibm.security.appscan.altoromutual.api;

import com.ibm.security.appscan.altoromutual.util.yahooUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.ApplicationFrame;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class generate the seven required Charts and 2 additional Charts .
 * Generated figures are saved in plots.
 * @author Zhichao
 *
 */

public class ChartAPI extends ApplicationFrame {

    public ChartAPI() {
        super("Chart API");
    }

    private static ArrayList<Double> calCumRet(ArrayList<Double> returns) {
        ArrayList<Double> CumRets = new ArrayList<>();
        double CumRet = 1;
        for (Double ret : returns) {
            CumRet *= (ret + 1);
            CumRets.add(CumRet);
        }
        return CumRets;
    }


    private static double[] arrayListToList(ArrayList<Double> arrayList) {
        List<Double> yList = new ArrayList<>(arrayList);
        return yList.stream().mapToDouble(Double::doubleValue).toArray();
    }


    private static TimeSeries createTimeSeries(ArrayList<String> dates, ArrayList<Double> yVariable,
                                               String key) {
        TimeSeries series = new TimeSeries(key);
        for (int i = 0; i < dates.size(); i++) {
            try {
                Day historicalDay = Day.parseDay(dates.get(i));
                series.add(historicalDay, yVariable.get(i));
            } catch (SeriesException e) {
                System.out.println("FAIL to add to series");
            }
        }
        return series;
    }

    private static XYDataset createTimeSeriesCollection(TimeSeries... args) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries timeSeries : args) {
            dataset.addSeries(timeSeries);
        }
        return dataset;
    }

    private static XYSeries createXYSeries(ArrayList<Double> xVariable, ArrayList<Double> yVariable,
                                           String key) {
        XYSeries series = new XYSeries(key);
        for (int i = 0; i < xVariable.size(); i++) {
            series.add(xVariable.get(i), yVariable.get(i));
        }
        return series;
    }

    private static XYDataset createXYSeriesCollection(XYSeries... args) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (XYSeries series : args) {
            dataset.addSeries(series);
        }
        return dataset;
    }

    private static HistogramDataset createHistDataset(double[] yVariable, String key) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries(key, yVariable, 15);
        return dataset;
    }

    public static JFreeChart getPricePlot(String ticker) throws SQLException,
            IOException, ParseException, InterruptedException {
        ArrayList<String> dates = yahooUtil.getDate(ticker);
        ArrayList<Double> closes =yahooUtil.getStockP(ticker);

        TimeSeries timeSeries = createTimeSeries(dates, closes, ticker);
        XYDataset dataset = createTimeSeriesCollection(timeSeries);

        String subTitle = ticker + " Prices";
        String xName = "Dates";
        String yName =ticker + " Prices";
        ChartUtils.saveChartAsPNG(new File("priceplot.png"), ChartFactory.createTimeSeriesChart(subTitle, xName, yName, dataset), 800, 300);
        return ChartFactory.createTimeSeriesChart(subTitle, xName, yName, dataset);

    }

    public static JFreeChart getReturnsPlot(String ticker) throws SQLException,
            IOException, ParseException, InterruptedException {
        ArrayList<String> dates = yahooUtil.getDate(ticker);
        ArrayList<Double> returns = yahooUtil.getReturns(ticker);
        TimeSeries timeSeries = createTimeSeries(dates, returns, ticker);

        XYDataset dataset = createTimeSeriesCollection(timeSeries);
        String subTitle = ticker + " Returns";
        String xName = "Dates";
        String yName = ticker + " Returns";
        XYPlot xy=ChartFactory.createScatterPlot(subTitle, xName, yName, dataset).getXYPlot();
        ValueAxis axis = xy.getDomainAxis();
        DateAxis dateAxis = new DateAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));
        xy.setDomainAxis(dateAxis);
        ChartUtils.saveChartAsPNG(new File("returnplot.png"),   xy.getChart(), 800, 300);

        return ChartFactory.createScatterPlot(subTitle, xName, yName, dataset);
    }

    public static JFreeChart getPrevCurrPlot(String ticker) throws
            SQLException, IOException, ParseException, InterruptedException {
        ArrayList<Double> returns = yahooUtil.getReturns(ticker);

        ArrayList<Double> prev = new ArrayList<>(returns);
        ArrayList<Double> curr = new ArrayList<>(returns);

        prev.remove(prev.size()-1);
        curr.remove(0);

        XYSeries series = createXYSeries(prev, curr, ticker);
        XYDataset dataset = createXYSeriesCollection(series);

        String subTitle = ticker + " Current Return Vs. Prev Return";
        String xName = ticker + " Prev Returns";
        String yName = ticker + " Curr Returns";
        ChartUtils.saveChartAsPNG(new File("prevVScurrRetunplot.png"),   ChartFactory.createScatterPlot(subTitle, xName, yName, dataset), 800, 300);
        return ChartFactory.createScatterPlot(subTitle, xName, yName, dataset);
    }


    public static JFreeChart getHistPlot(String ticker) throws SQLException,
            IOException, ParseException, InterruptedException {
        ArrayList<Double> returns = yahooUtil.getReturns(ticker);

        double[] y = arrayListToList(returns);
        HistogramDataset dataset = createHistDataset(y, ticker);

        String subTitle = " Histogram of "+ticker + " Returns";
        String xName = "Return Bins (%)";
        String yName = "Frequency";

        ChartUtils.saveChartAsPNG(new File("Histplot.png"),   ChartFactory.createHistogram(subTitle, xName, yName, dataset,
                PlotOrientation.VERTICAL, false, false, false), 800, 300);

        return ChartFactory.createHistogram(subTitle, xName, yName, dataset,
                PlotOrientation.VERTICAL, false, false, false);
    }

    public static JFreeChart getCompRetPlot(String ticker) throws SQLException,
            IOException, ParseException, InterruptedException {
        ArrayList<String> stkDays = yahooUtil.getDate(ticker);
        ArrayList<Double> stkReturns = yahooUtil.getReturns(ticker);
        ArrayList<String> idxDays = yahooUtil.getDate("SPY");
        ArrayList<Double> idxReturns = yahooUtil.getReturns("SPY");
        TimeSeries stkTS = createTimeSeries(stkDays, stkReturns, ticker);
        TimeSeries idxTS = createTimeSeries(idxDays, idxReturns, "SPY");
        XYDataset dataset = createTimeSeriesCollection(stkTS, idxTS);
        String subTitle = ticker+" Returns Vs. SPY Returns";
        String xName = "Dates";
        String yName ="Returns";
        ChartUtils.saveChartAsPNG(new File("RetCompplot.png"),  ChartFactory.createTimeSeriesChart(subTitle, xName, yName, dataset), 800, 300);
        return ChartFactory.createTimeSeriesChart(subTitle, xName, yName, dataset);
    }

    public static JFreeChart getCompPricePlot(String ticker) throws SQLException,
            IOException, ParseException, InterruptedException {
        ArrayList<String> stkDays = yahooUtil.getDate(ticker);
        ArrayList<Double> stkprice = yahooUtil.getStockP(ticker);
        ArrayList<String> idxDays = yahooUtil.getDate("SPY");
        ArrayList<Double> idxprice = yahooUtil.getStockP("SPY");
        TimeSeries stkTS = createTimeSeries(stkDays, stkprice, ticker);
        TimeSeries idxTS = createTimeSeries(idxDays, idxprice, "SPY");
        XYDataset dataset = createTimeSeriesCollection(stkTS, idxTS);
        String subTitle = ticker+" Prices Vs. SPY Prices";
        String xName = "Dates";
        String yName = "Price";
        ChartUtils.saveChartAsPNG(new File("PriceCompplot.png"),  ChartFactory.createTimeSeriesChart(subTitle, xName, yName, dataset), 800, 300);
        return ChartFactory.createTimeSeriesChart(subTitle, xName, yName, dataset);
    }

    public static JFreeChart getComCumRetAndStkCumRetPlot(String ticker) throws SQLException,
            IOException, ParseException, InterruptedException {
        ArrayList<String> stkDays = yahooUtil.getDate(ticker);
        ArrayList<Double> stkReturns = yahooUtil.getReturns(ticker);
        ArrayList<String> idxDays = yahooUtil.getDate("SPY");
        ArrayList<Double> idxReturns = yahooUtil.getReturns("SPY");


        int minLength = Math.min(stkReturns.size(), idxReturns.size());
        ArrayList<Double> stkCumRet = calCumRet(stkReturns);
        ArrayList<Double> idxCumRet = calCumRet(idxReturns);
        ArrayList<String> days = stkDays.size() <= idxDays.size() ? stkDays : idxDays;

        TimeSeries stkTS = createTimeSeries(days, stkCumRet, ticker);
        TimeSeries idxTS = createTimeSeries(days, idxCumRet, "SPY");
        XYDataset dataset = createTimeSeriesCollection(stkTS, idxTS);
        XYDataset STK = createTimeSeriesCollection(stkTS);


        String subTitle = ticker + " Cumulative Returns";
        String xName = "Dates";
        String yName = " Cumulative Returns";
        String subTitle2= ticker+" Cumulative Returns vs SPY Cumulative Returns";

        ChartUtils.saveChartAsPNG(new File("CumRetplot.png"),  ChartFactory.createTimeSeriesChart(subTitle, xName, yName, STK), 800, 300);
        ChartUtils.saveChartAsPNG(new File("ComCumRetplot.png"),  ChartFactory.createTimeSeriesChart(subTitle2, xName, yName, dataset), 800, 300);
        return ChartFactory.createTimeSeriesChart(subTitle2, xName, yName, dataset);
    }

    public static JFreeChart getScatterCompRetPlot(String ticker) throws SQLException,
            IOException, ParseException, InterruptedException {
        ArrayList<String> stkDays = yahooUtil.getDate(ticker);
        ArrayList<Double> stkReturns = yahooUtil.getReturns(ticker);
        ArrayList<String> idxDays = yahooUtil.getDate("SPY");
        ArrayList<Double> idxReturns = yahooUtil.getReturns("SPY");
        TimeSeries stkTS = createTimeSeries(stkDays, stkReturns, ticker);
        TimeSeries idxTS = createTimeSeries(idxDays, idxReturns, "SPY");
        XYDataset dataset = createTimeSeriesCollection(stkTS, idxTS);

        String subTitle = ticker+" Returns Vs. SPY Returns";
        String xName = "Dates";
        String yName ="Returns";

        XYPlot xy=ChartFactory.createScatterPlot(subTitle, xName, yName, dataset).getXYPlot();
        ValueAxis axis = xy.getDomainAxis();
        DateAxis dateAxis = new DateAxis();
        dateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));
        xy.setDomainAxis(dateAxis);
        ChartUtils.saveChartAsPNG(new File("ScatterCompRetplot.png"),   xy.getChart(), 800, 300);
        return xy.getChart();
    }


    public static void main(final String[] args) throws
          SQLException, IOException, ParseException, InterruptedException {
          String symbol = "AAPL";
          ChartAPI.getPricePlot(symbol).getPlot();
          ChartAPI.getReturnsPlot(symbol);
          ChartAPI.getPrevCurrPlot(symbol);
          ChartAPI.getHistPlot(symbol);
          ChartAPI.getCompRetPlot(symbol);
          ChartAPI.getCompPricePlot(symbol);
          ChartAPI.getComCumRetAndStkCumRetPlot(symbol);
          ChartAPI.getScatterCompRetPlot(symbol);
    }




}