package com.mycompany.logic;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mycompany.design.MainForm;

public class JsonConverter {
	public void convertTrendsValue(String fileName) {
		try {
			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(fileName));
			byte[] byt = new byte[fileInputStream.available()];
			fileInputStream.read(byt);
			String s = new String(byt);
			try {
				Object obj = parser.parse(s);
				JSONArray array = (JSONArray) obj;
				JSONObject obj2 = (JSONObject) array.get(0);
				JSONArray trends = (JSONArray) obj2.get("trends");
				for (int i = 0; i < trends.size(); i++) {
					JSONObject jsonObject = (JSONObject) trends.get(i);
					Vector<String> rowData = new Vector<String>();
					rowData.add(jsonObject.get("name").toString());
					rowData.add(jsonObject.get("url").toString());
					MainForm.defaultTableModel.addRow(rowData);
					MainForm.jComboBox.addItem(jsonObject.get("name")
							.toString());
				}
			} catch (Exception pe) {
				pe.printStackTrace();
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void convertSearchValue(String fileName) {
		try {
			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(
					fileName));
			byte[] byt = new byte[fileInputStream.available()];

			fileInputStream.read(byt);
			String s = new String(byt);
			try {
				Object obj = parser.parse(s);
				JSONObject jsonObject = (JSONObject) obj;
				JSONArray trends = (JSONArray) jsonObject.get("statuses");
				System.out.println(trends.size());
				for (int i = 0; i < trends.size(); i++) {
					JSONObject jsObject = (JSONObject) trends.get(i);
					JSONObject jObject = (JSONObject) jsObject.get("user");
					Vector<String> rowData = new Vector<String>();
					rowData.add(jObject.get("id").toString());
					rowData.add(jObject.get("screen_name").toString());
					rowData.add(jObject.get("name").toString());
					if (MainForm.jRadioButtonLink.isSelected()) {
						rowData.add(jsObject.get("retweet_count").toString());
					}
					MainForm.defaultTableModelUser.addRow(rowData);
				}

			} catch (Exception pe) {
				pe.printStackTrace();
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void convertAggregationValue(String fileName) {
		try {
			JSONParser parser = new JSONParser();
			FileInputStream fileInputStream = new FileInputStream(new File(
					fileName));
			byte[] byt = new byte[fileInputStream.available()];

			fileInputStream.read(byt);
			String s = new String(byt);
			try {
				Object obj = parser.parse(s);
				JSONObject jsonObject = (JSONObject) obj;
				JSONArray trends = (JSONArray) jsonObject.get("statuses");
				System.out.println(trends.size());
				JSONObject jsObject = (JSONObject) trends
						.get(trends.size() - 1);
				String inTime = jsObject.get("created_at").toString();
				jsObject = (JSONObject) trends.get(0);
				String outTime = jsObject.get("created_at").toString();
				int count = MainForm.defaultTableModelUser.getRowCount();
				int retweet_count = 0;
				if (MainForm.jRadioButtonLink.isSelected()) {
					for (int i = 0; i < count; i++) {
						retweet_count += Integer
								.parseInt(MainForm.defaultTableModelUser
										.getValueAt(i, 3).toString());
					}
				}

				int rowCount = MainForm.defaultTableModel.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					String trendName = MainForm.defaultTableModel.getValueAt(i,
							0).toString();
					System.out.println(trendName);
					if (MainForm.jComboBox.getSelectedItem().toString()
							.equals(trendName)) {
						MainForm.defaultTableModel.setValueAt(inTime, i, 2);
						MainForm.defaultTableModel.setValueAt(outTime, i, 3);
						if (MainForm.jRadioButtonLink.isSelected())
							MainForm.defaultTableModel.setValueAt(
									retweet_count, i, 4);
					}
				}

			} catch (Exception pe) {
				pe.printStackTrace();
			} finally {
				fileInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
