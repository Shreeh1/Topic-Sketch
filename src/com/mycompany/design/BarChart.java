package com.mycompany.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class BarChart extends ApplicationFrame {

	/**
	 * Creates a new demo instance.
	 * 
	 * @param title
	 *            the frame title.
	 * @throws IOException 
	 */
	public BarChart(final String title) throws IOException {

		super(title);

		final CategoryDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartPanel);

	}

	/**
	 * Returns a sample dataset.
	 * 
	 * @return The dataset.
	 * @throws IOException 
	 */
	private CategoryDataset createDataset() throws IOException {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int rowCount=MainForm.defaultTableTime.getRowCount();
		Properties properties=new Properties();
		FileInputStream fileInputStream=new FileInputStream(new File("config.properties"));
		properties.load(fileInputStream);
		double count=Double.parseDouble(properties.getProperty("count").toString());
		for(int i=0;i<rowCount;i++)
		{
			Double diff=Double.parseDouble(MainForm.defaultTableTime.getValueAt(i, 2).toString());
			double totalCount=Double.parseDouble(MainForm.defaultTableTime.getValueAt(i,3).toString());
			double value=diff/totalCount;
			long burstAnalsysis=(long)(value*count);
			DecimalFormat formatter = new DecimalFormat("#0.00");
			long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(burstAnalsysis);
			if(i==0)
			{
				
				dataset.addValue(burstAnalsysis, "Clustering Based Detection", String.valueOf(formatter.format(timeSeconds)));  
			}
			else
			{
				dataset.addValue(burstAnalsysis, "Topic Modelling Based Detection", String.valueOf(formatter.format(timeSeconds)));  
			}
			     
		}
	
		return dataset;

	}

	private JFreeChart createChart(final CategoryDataset dataset) {

		final JFreeChart chart = ChartFactory.createBarChart("Result Analysis", // chart
																				// title
				"Mode", // domain axis label
				"Time", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);

		chart.setBackgroundPaint(Color.white);

		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		final GradientPaint gp0 = new GradientPaint(1.0f, 1.0f, Color.blue,
				1.0f, 1.0f, Color.yellow);
		final GradientPaint gp1 = new GradientPaint(1.0f, 1.0f, Color.red,
				1.0f, 1.0f, Color.yellow);
		final GradientPaint gp2 = new GradientPaint(1.0f, 1.0f, Color.green,
				1.0f, 1.0f, Color.yellow);
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));

		return chart;

	}

	public static void main(final String[] args) {

		/*final BarChartDemo demo = new BarChartDemo("Result Analysis");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);*/

	}

}