package com.mycompany.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.ui.RefineryUtilities;


import com.mycompany.logic.TwitterRestCall;

public class MainForm implements ActionListener {
	private JButton jbuttonTrends, jbuttonTraining, jbuttonAggregation,
			jButtonChangePoint, jbuttonBurstAnalysis;
	private TwitterRestCall twitterRestCall;
	public static DefaultTableModel defaultTableModel, defaultTableModelUser,
			defaultTableTime;
	private JTable jTable, jTableUser, jTableTime;
	private JScrollPane jScrollPane, jScrollPaneUser, jScroPaneTime;
	public static JComboBox<String> jComboBox;
	public static JRadioButton jRadioButtonkey;
	public static JRadioButton jRadioButtonLink;
	public static JLabel label, label1;


	public void design() {

		twitterRestCall = new TwitterRestCall();
		JFrame frame = new JFrame(
				"TopicSketch: Real-time Bursty Topic Detection from Twitter");
		// frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tab = new JTabbedPane();
		frame.add(tab, BorderLayout.CENTER);
		frame.add(createPanelLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = frame.getSize();
		screenSize.height = screenSize.height / 2;
		screenSize.width = screenSize.width / 2;
		size.height = size.height / 2;
		size.width = size.width / 2;
		frame.setSize(1025, 730);
		frame.setVisible(true);

	}

	public JPanel createPanelLayout() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		
		jPanel.setLayout(null);
		jPanel.setBorder(BorderFactory.createTitledBorder(""));
		jPanel.setForeground(Color.YELLOW);
		jPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW,1));
		jPanel.setBackground(new Color(229,253,105));
		
		jComboBox = new JComboBox<String>();
		jComboBox.setBounds(380, 130, 150, 30);
		jPanel.add(jComboBox);

		jRadioButtonkey = new JRadioButton("Clustering Based Detection", true);
		jRadioButtonkey.setFont(new Font("Verdana",Font.BOLD,10));
		jRadioButtonkey.setBackground(new Color(229,253,105));
		jRadioButtonkey.addActionListener(this);

		jRadioButtonLink = new JRadioButton("Topic Modelling Based Detection");
		jRadioButtonLink.setFont(new Font("Verdana",Font.BOLD,10));
		jRadioButtonLink.setBackground(new Color(229,253,105));
		jRadioButtonLink.addActionListener(this);

		jRadioButtonkey.setBounds(10, 40, 190, 30);
		jRadioButtonLink.setBounds(200, 40, 300, 30);
		
		jbuttonTrends = new JButton("TopicSketch");
		jbuttonTrends.setBounds(110, 80, 100, 30);
		jbuttonTrends.addActionListener(this);
		
		jbuttonTraining = new JButton("Perform Training");
		jbuttonTraining.setBounds(90, 120, 140, 30);
		jbuttonTraining.addActionListener(this);
		
		jbuttonAggregation = new JButton("Aggregation");
		jbuttonAggregation.setBounds(550, 130, 140, 30);
		jbuttonAggregation.addActionListener(this);
		
		jButtonChangePoint = new JButton("Change Point Analysis");
		jButtonChangePoint.setBounds(80, 160, 160, 30);
		jButtonChangePoint.addActionListener(this);

		jbuttonBurstAnalysis = new JButton("Burst Analysis");
		jbuttonBurstAnalysis.setBounds(720, 130, 160, 30);
		jbuttonBurstAnalysis.addActionListener(this);
		jPanel.add(jbuttonBurstAnalysis);

		jPanel.add(jbuttonTrends);
		jPanel.add(jbuttonTraining);
		jPanel.add(jbuttonAggregation);
		jPanel.add(jButtonChangePoint);
		
		label =new JLabel("Real-time Bursty Topic Detection from Twitter");
		label.setFont(new Font("Verdana",Font.BOLD,14));
		label.setBounds(400, 170, 360, 30);
		jPanel.add(label);
		
		label1 =new JLabel("TopicSketch: Real-time Bursty Topic Detection from Twitter");
		label1.setFont(new Font("Verdana",Font.BOLD,14));
		label1.setBounds(320, 10, 500, 30);
		jPanel.add(label1);
		
		defaultTableModel = new DefaultTableModel();
		defaultTableModel.addColumn("Bursty Topic");
		defaultTableModel.addColumn("URI");
		defaultTableModel.addColumn("In Time");
		defaultTableModel.addColumn("Out Time");
		defaultTableModel.addColumn("Retweet Count");
		jTable = new JTable(defaultTableModel);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(250);
		jScrollPane = new JScrollPane(jTable);
		jScrollPane.setBounds(100, 220, 850, 200);

		defaultTableModelUser = new DefaultTableModel();
		defaultTableModelUser.addColumn("Id");
		defaultTableModelUser.addColumn("User Name");
		defaultTableModelUser.addColumn("Screen Name");
		defaultTableModelUser.addColumn("Retweet");
		jTableUser = new JTable(defaultTableModelUser);
		jScrollPaneUser = new JScrollPane(jTableUser);
		jScrollPaneUser.setBounds(100, 460, 400, 200);

		defaultTableTime = new DefaultTableModel();
		defaultTableTime.addColumn("In Time");
		defaultTableTime.addColumn("out Time");
		defaultTableTime.addColumn("Diff");
		defaultTableTime.addColumn("Total Count");
		jTableTime = new JTable(defaultTableTime);
		jScroPaneTime = new JScrollPane(jTableTime);
		jScroPaneTime.setBounds(550, 460, 400, 200);

		jPanel.add(jRadioButtonkey);
		jPanel.add(jRadioButtonLink);
		jPanel.add(jScrollPane);
		jPanel.add(jScrollPaneUser);
		jPanel.add(jScroPaneTime);

		return jPanel;
	}

	public static void main(String[] args) {
		MainForm mainForm = new MainForm();
		mainForm.design();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jbuttonTrends) {
			twitterRestCall.getTrends();
		} else if (e.getSource() == jRadioButtonLink) {
			jComboBox.removeAllItems();
			defaultTableModel.setRowCount(0);
			defaultTableModelUser.setRowCount(0);
			jRadioButtonLink.setSelected(true);
			jRadioButtonkey.setSelected(false);
		} else if (e.getSource() == jRadioButtonkey) {
			jRadioButtonLink.setSelected(false);
			jRadioButtonkey.setSelected(true);
		} else if (e.getSource() == jbuttonTraining) {
			String trendName = jComboBox.getSelectedItem().toString();
			twitterRestCall.getTweetSearch(trendName);
		} else if (e.getSource() == jbuttonAggregation) {
			twitterRestCall.getAggregation();
		} else if (e.getSource() == jButtonChangePoint) {
			int rowCount = defaultTableModel.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				String trendName = defaultTableModel.getValueAt(i, 0)
						.toString();
				System.out.println(trendName);
				if (jComboBox.getSelectedItem().toString().equals(trendName)) {
					String inTime = defaultTableModel.getValueAt(i, 2)
							.toString();
					String outTime = defaultTableModel.getValueAt(i, 3)
							.toString();
					long inMilli = convertStringtoDate(inTime);
					long outMilli = convertStringtoDate(outTime);
					long diff = (outMilli - inMilli);
					Vector<String> rowData = new Vector<String>();
					rowData.add(String.valueOf(inMilli));
					rowData.add(String.valueOf(outMilli));
					rowData.add(String.valueOf(diff));
					if (jRadioButtonLink.isSelected()) {
						String retweet_count = defaultTableModel.getValueAt(i,
								4).toString();
						rowData.add(retweet_count);
					} else {
						rowData.add(String.valueOf(100));
					}

					defaultTableTime.addRow(rowData);
				}
			}
		} else if (e.getSource() == jbuttonBurstAnalysis) {
			try {
				BarChart barChart = new BarChart("Burst Detection");
				barChart.pack();
				RefineryUtilities.centerFrameOnScreen(barChart);
				barChart.setVisible(true);
			} catch (Exception ee) {
				ee.printStackTrace();
			}

		}
	}

	public long convertStringtoDate(String time) {
		String[] inSplitter = time.split(" ");
		Calendar calendar = Calendar.getInstance();
		String getInMonth = inSplitter[1].toUpperCase();
		int month = 0;
		int date = Integer.parseInt(inSplitter[2]);
		if (getInMonth.contains("JAN")) {
			month = Calendar.JANUARY;
		} else if (getInMonth.contains("FEB")) {
			month = Calendar.FEBRUARY;
		} else if (getInMonth.contains("MAR")) {
			month = Calendar.MARCH;
		} else if (getInMonth.contains("APR")) {
			month = Calendar.APRIL;
		} else if (getInMonth.contains("MAY")) {
			month = Calendar.MAY;
		} else if (getInMonth.contains("JUN")) {
			month = Calendar.JUNE;
		} else if (getInMonth.contains("JUL")) {
			month = Calendar.JULY;
		} else if (getInMonth.contains("AUG")) {
			month = Calendar.AUGUST;
		} else if (getInMonth.contains("SEP")) {
			month = Calendar.SEPTEMBER;
		} else if (getInMonth.contains("OCT")) {
			month = Calendar.OCTOBER;
		} else if (getInMonth.contains("NOV")) {
			month = Calendar.NOVEMBER;
		} else if (getInMonth.contains("DEC")) {
			month = Calendar.DECEMBER;
		}
		String timeStamp = inSplitter[3];
		calendar.set(2017, month, date);
		int hour = Integer.parseInt(timeStamp.split(":")[0]);
		int minutes = Integer.parseInt(timeStamp.split(":")[1]);
		int secs = Integer.parseInt(timeStamp.split(":")[2]);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, secs);
		System.out.println(calendar.getTime());
		return calendar.getTimeInMillis();
	}
}
