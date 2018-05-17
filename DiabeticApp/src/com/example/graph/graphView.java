package com.example.graph;

import java.util.ArrayList;
import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.robel_project.R;
import com.example.serviceHandler.Model;
import com.example.serviceHandler.ServiceHandler;

public class graphView extends Activity {

	//ListView list;
	private static final String TAG_date = "htc_date";
	private static final String TAG_time = "htc_time";
	private static final String TAG_cp = "htc_cp";
	private static final String TAG_bg = "htc_bg";
	private static final String TAG_qa = "htc_qa";
	private static final String TAG_bi = "htc_bi";
	private static final String TAG_comments = "htc_comments";
	JSONArray contacts = null;
	ArrayList<Model> array = new ArrayList<Model>();
	private View mChart;
	private String[] mMonth = new String[31];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graphview);
		// openChart();

		for (int i = 0; i < 31; i++) {
			mMonth[i] = String.valueOf(i);
		}
		
		new callApi().execute();
	}

	private class callApi extends AsyncTask<Void, Void, Void> {
		ProgressDialog pDialog;
		//Model m;
		boolean api_result = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(graphView.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response

			// add all value in NameValue pair

			// call service handler class and get response
			String jsonStr = sh.makeServiceCall(ServiceHandler.show_graph_url, ServiceHandler.POST);

			// get json response
			Log.i("Response: ", "" + jsonStr);

			if (jsonStr != null) {
				try {
					api_result = true;
					JSONObject jsonObj = new JSONObject(jsonStr);

					// Getting JSON Array node
					contacts = jsonObj.getJSONArray("list");

					// looping through All Contacts
					for (int i = 0; i < contacts.length(); i++) {
						JSONObject c = contacts.getJSONObject(i);

						String date = c.getString(TAG_date);
						String time = c.getString(TAG_time);
						String cp = c.getString(TAG_cp);
						String bg = c.getString(TAG_bg);
						Log.i("", "json b g :> " + bg);
						String qa = c.getString(TAG_qa);
						String bi = c.getString(TAG_bi);
						String commnet = c.getString(TAG_comments);
						// Phone node is JSON Object
						Model m = new Model(date, time, cp, bg, qa, bi, commnet);
						array.add(m);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}
		

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

			if (api_result) {
				openChart();

			} else {
				Toast.makeText(getApplicationContext(),
						"Please try again later.", Toast.LENGTH_LONG).show();

			}

		}

	}

	private void openChart() {

		// int[] x = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,1,1,1,1,1,1 };

		// int[] expense = { 12,14,27,12,10,11,23,0,0,0,4,6,8,3,2,19};
		int[] x = new int[array.size()];
		int[] expense = new int[array.size()];
		
		for (int i = 0; i < array.size(); i++) {
			String bg = array.get(i).get_bg();
			String date = array.get(i).get_date();
			String[] separated = date.split("-");
			Integer date_val = (Integer.valueOf(separated[2]) - 1);
			double d = Double.parseDouble(bg);
			Integer bg_val =(int) d;

			x[i] = date_val;
			expense[i] = bg_val;
			Log.i("", " x is :> " + x[i]);
			Log.i("", " expence  is :> " + expense[i]);
		}
		
		
		// Creating an XYSeries for Income
		//XYSeries incomeSeries = new XYSeries("Income");
		// Creating an XYSeries for Expense
		XYSeries expenseSeries = new XYSeries("Expense");
		// Adding data to Income and Expense Series
		for (int i = 0; i < x.length; i++) {
			expenseSeries.add(x[i], expense[i]);
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		 //dataset.addSeries(incomeSeries);
		// Adding Expense Series to dataset
		dataset.addSeries(expenseSeries);
		
	   //Creating XYSeriesRenderer to customize incomeSeries
	   //XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
	   //incomeRenderer.setColor(Color.CYAN); // color of the graph set to
	   //cyan
	   //incomeRenderer.setFillPoints(true);
	   //incomeRenderer.setLineWidth(2f);
	   //incomeRenderer.setDisplayChartValues(true);
	   //setting chart value distance
	   //incomeRenderer.setDisplayChartValuesDistance(10);
	   //setting line graph point style to circle
	   //incomeRenderer.setPointStyle(PointStyle.CIRCLE);
       //setting stroke of the line chart to solid
	   //incomeRenderer.setStroke(BasicStroke.SOLID);
		


		// Creating XYSeriesRenderer to customize expenseSeries
		XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
		expenseRenderer.setColor(Color.WHITE);
		expenseRenderer.setFillPoints(true);
		expenseRenderer.setLineWidth(2f);
		expenseRenderer.setDisplayChartValues(true);
		// setting line graph point style to circle
		expenseRenderer.setPointStyle(PointStyle.SQUARE);
		// setting stroke of the line chart to solid
		expenseRenderer.setStroke(BasicStroke.SOLID);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("Date vs BG");
		multiRenderer.setXTitle("Last 30 Days");
		multiRenderer.setYTitle("BG");

		/***
		 * Customizing graphs
		 */
		// setting text size of the title
		multiRenderer.setChartTitleTextSize(15);
		// setting text size of the axis title
		multiRenderer.setAxisTitleTextSize(15);

		// setting text size of the graph lable
		multiRenderer.setLabelsTextSize(10);
		// setting zoom buttons visiblity
		multiRenderer.setZoomButtonsVisible(true);
		// setting pan enablity which uses graph to move on both axis
		multiRenderer.setPanEnabled(true, true);
		// setting click false on graph
		multiRenderer.setClickEnabled(false);
		// setting zoom to false on both axis
		multiRenderer.setZoomEnabled(true, true);
		// setting lines to display on y axis
		multiRenderer.setShowGridY(true);
		// setting lines to display on x axis
		multiRenderer.setShowGridX(true);
		// setting legend to fit the screen size
		multiRenderer.setFitLegend(true);
		// setting displaying line on grid
		multiRenderer.setShowGrid(true);
		// setting zoom to false
		multiRenderer.setZoomEnabled(false);
		// setting external zoom functions to false
		multiRenderer.setExternalZoomEnabled(false);
		// setting displaying lines on graph to be formatted(like using
		// graphics)
		multiRenderer.setAntialiasing(true);
		// setting to in scroll to false
		multiRenderer.setInScroll(true);
		// setting to set legend height of the graph
		multiRenderer.setLegendHeight(30);
		// setting x axis label align
		multiRenderer.setXLabelsAlign(Align.CENTER);
		// setting y axis label to align
		multiRenderer.setYLabelsAlign(Align.LEFT);
		// setting text style
		multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
		// setting no of values to display in y axis
		multiRenderer.setYLabels(10);
		// setting y axis max value, Since i'm using static values inside the
		// graph so i'm setting y max value to 4000.
		// if you use dynamic values then get the max y value and set here
		multiRenderer.setYAxisMax(50);
		// setting used to move the graph on xaxiz to .5 to the right
		multiRenderer.setXAxisMin(0);
		// setting used to move the graph on xaxiz to .5 to the right

		multiRenderer.setXAxisMax(31);
		// setting bar size or space between two bars
		// multiRenderer.setBarSpacing(0.5);
		// Setting background color of the graph to transparent
		multiRenderer.setBackgroundColor(Color.BLUE);
		// Setting margin color of the graph to transparent
		multiRenderer.setMarginsColor(getResources().getColor(R.color.black));
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setScale(2f);
		// setting x axis point size
		multiRenderer.setPointSize(4f);
		// setting the margin size for the graph in the order top, left, bottom,
		// right
		multiRenderer.setMargins(new int[] { 30, 30, 30, 30 });

		for (int i = 0; i < 31; i++) {
			multiRenderer.addXTextLabel(i, mMonth[i]);
		}

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		// multiRenderer.addSeriesRenderer(incomeRenderer);
		multiRenderer.addSeriesRenderer(expenseRenderer);

		// this part is used to display graph on the xml
		LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
		// remove any views before u paint the chart
		chartContainer.removeAllViews();
		// drawing bar chart
		
		mChart = ChartFactory.getLineChartView(this, dataset, multiRenderer);
		// adding the view to the linearlayout
		chartContainer.addView(mChart);

	}

}
