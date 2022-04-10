package com.ibm.security.appscan.altoromutual.api;

import com.ibm.security.appscan.altoromutual.util.yahooUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
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
import java.util.ArrayList;
import java.util.List;

/**
 * This class draws Figure 14.1, 14.2, 14.3, 14.4, 7.1, 7.2, 7.3.
 * Generated figures are saved in plots.
 * @author Rora
 *
 */

public class ChartAPI extends ApplicationFrame {

    public ChartAPI() {
        super("Chart API");
    }

    private static ArrayList<Double> _calCumRet(ArrayList<Double> returns) {
        ArrayList<Double> cumRets = new ArrayList<>();
        double cumRet = 1;
        for (Double ret : returns) {
            cumRet *= (ret + 1);
            cumRets.add(cumRet);
        }
        return cumRets;
    }

    private static double _calBeta(ArrayList<Double> xVariable, ArrayList<Double> yVariable) {
        double xMean = xVariable.stream().mapToDouble(d -> d).average().orElse(0.0);
        double yMean = yVariable.stream().mapToDouble(d -> d).average().orElse(0.0);
        double nominator = 0;
        double denominator = 0;
        for (int i = 0; i < xVariable.size(); i++) {
            nominator += (xVariable.get(i) - xMean) * (yVariable.get(i) - yMean);
            denominator += (xVariable.get(i) - xMean) * (xVariable.get(i) - xMean);
        }
        return nominator / denominator;
    }

    private static double _calAlpha(ArrayList<Double> xVariable, ArrayList<Double> yVariable,
                                    double beta) {
        double xMean = xVariable.stream().mapToDouble(d -> d).average().orElse(0.0);
        double yMean = yVariable.stream().mapToDouble(d -> d).average().orElse(0.0);
        return yMean - (beta * xMean);
    }

    private static ArrayList<Double> _findFittedValues(ArrayList<Double> xVariable,
                                                       ArrayList<Double> yVariable) {
        double beta = _calBeta(xVariable, yVariable);
        double alpha = _calAlpha(xVariable, yVariable, beta);
        ArrayList<Double> fitted = new ArrayList<>();
        for (Double x : xVariable) {
            fitted.add(x * beta + alpha);
        }
        return fitted;
    }

    private static ArrayList<String> _truncateList(ArrayList<String> list, int minLength) {
        if (list.size() == minLength) {
            return list;
        }
        ArrayList<String> result = new ArrayList<>();
        for (int i = (list.size() - minLength); i < list.size(); i++) {
            result.add(list.get(i));
        }
        return result;
    }

    private static double[] _arrayListToList(ArrayList<Double> arrayList) {
        List<Double> yList = new ArrayList<>(arrayList);
        return yList.stream().mapToDouble(Double::doubleValue).toArray();
    }

    private static ArrayList<Double> _stringListToDouble(ArrayList<String> list) {
        ArrayList<Double> result = new ArrayList<>();
        for (String s : list) {
            result.add(Double.parseDouble(s));
        }
        return result;
    }

    private static TimeSeries createTimeSeries(ArrayList<String> dates, ArrayList<Double> yVariable,
                                               String key) {
        TimeSeries series = new TimeSeries(key);
        for (int i = 0; i < dates.size(); i++) {
            try {
                Day historicalDay = Day.parseDay(dates.get(i));
                series.add(historicalDay, yVariable.get(i));
            } catch (SeriesException e) {
                System.out.println("Error adding to series");
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
        dataset.addSeries(key, yVariable, 10);
        return dataset;
    }

    public static JFreeChart getPricePlot(String symbol) throws SQLException,
            IOException, ParseException, InterruptedException {
        ArrayList<String> dates = yahooUtil.getDate(symbol);
        ArrayList<Double> closes =yahooUtil.getStockPrice(symbol);

        TimeSeries timeSeries = createTimeSeries(dates, closes, symbol);
        XYDataset dataset = createTimeSeriesCollection(timeSeries);

        String subTitle = symbol + " Prices";
        String xName = "Dates";
        String yName = symbol + " Prices";

        return ChartFactory.createTimeSeriesChart(subTitle, xName, yName, dataset);

    }
    public static void main(final String[] args) throws
          SQLException, IOException, ParseException, InterruptedException {
          String symbol = "FB";
          ChartAPI.getPricePlot(symbol).getPlot();
          ChartUtils.saveChartAsPNG(new File("h.png"),   ChartAPI.getPricePlot(symbol), 450, 400);
//        ChartAPI.getReturnsPlot(symbol);
//        ChartAPI.getAutoCorrPlot(symbol);
//        ChartAPI.getHistPlot(symbol);
//        ChartAPI.getCumPlot(symbol);
//        ChartAPI.getPctChangePlot(symbol);
//        ChartAPI.getCAPMPlot(symbol);
    }

}