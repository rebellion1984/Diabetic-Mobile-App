package com.example.DiabeticApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robel_project.R;
import com.example.serviceHandler.Model;
import com.example.serviceHandler.ServiceHandler;

public class inputdata extends Activity implements OnClickListener {

	EditText etdate, ettime, etcp, etbg, etqa, etbi, etcomm, etadvice;
	Button bsave, bShowlist, badvice;
	final Calendar c = Calendar.getInstance();

	// ALL JSON node names
	private static final String TAG_date = "date";
	private static final String TAG_time = "time";
	private static final String TAG_cp = "cp";
	private static final String TAG_bg = "bg";
	private static final String TAG_qa = "qa";
	private static final String TAG_bi = "bi";
	private static final String TAG_comments = "comments";

	Model model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting strict mode policy
		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder().permitAll().build();
		// StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.datafill);

		// initialize
		etdate = (EditText) findViewById(R.id.dateText);
		ettime = (EditText) findViewById(R.id.timeText);
		etcp = (EditText) findViewById(R.id.CPText);
		etbg = (EditText) findViewById(R.id.BGText);
		etqa = (EditText) findViewById(R.id.QAText);
		etbi = (EditText) findViewById(R.id.BIText);
		etcomm = (EditText) findViewById(R.id.commentsText);
		etadvice = (EditText) findViewById(R.id.adviceText);
		bsave = (Button) findViewById(R.id.saveButton);
		badvice = (Button) findViewById(R.id.adviceButton);
		// click event of button Save
		bsave.setOnClickListener(this);

		// click event of Advice button
		badvice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				double bgTestValue = Double.parseDouble(etbg.getText().toString());

				if (bgTestValue <= 4) {
					etadvice.setText("Sugar level is LOW. Treat It ASAP!");
				} else if (bgTestValue > 4 && bgTestValue <= 7) {
					etadvice.setText("Sugar level is GOOD. Keep It Up!");
				} else if (bgTestValue > 7 && bgTestValue <= 10) {
					etadvice.setText("Sugar level is a bit HIGH");
				} else {
					etadvice.setText("Sugar level is VERY HIGH. Take insulin correction dose ");
				}
			}
		});

		// function call to current data and time
		setCurrentTimeAndDate();
	}

	// method for current date and time
	private void setCurrentTimeAndDate() {
		// sets current date
		String dateFormat = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.UK);
		etdate.setText(sdf.format(c.getTime()));
		// sets current time
		String timeFormat = "hh:mm a";
		SimpleDateFormat stf = new SimpleDateFormat(timeFormat, Locale.UK);
		ettime.setText(stf.format(c.getTime()));

	}

	// button that redirects to the 3rd page
	public void goToThirdAct(View view) {
		Intent intnt = new Intent(this, option_page.class);
		startActivity(intnt);

	}

	// perform background operations
	private class callApi extends AsyncTask<Void, Void, Void> {
		ProgressDialog pDialog;
		Model m;
		boolean api_result = false;

		public callApi(Model model) {
			// TODO Auto-generated constructor stub
			m = model;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(inputdata.this);
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
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_date, m.get_date()));
			params.add(new BasicNameValuePair(TAG_time, m.get_time()));

			params.add(new BasicNameValuePair(TAG_bg, m.get_bg()));

			params.add(new BasicNameValuePair(TAG_cp, m.get_cp()));

			params.add(new BasicNameValuePair(TAG_qa, m.get_qa()));

			params.add(new BasicNameValuePair(TAG_bi, m.get_bi()));

			params.add(new BasicNameValuePair(TAG_comments, m.get_comment()));

			// call service handler class and get response
			String jsonStr = sh.makeServiceCall(ServiceHandler.add_data_url,
					ServiceHandler.POST, params);

			// get json response
			Log.d("Response: ", "" + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);

					String result = jsonObj.getString("msg");
					if (result.equals("true")) {
						api_result = true; // get response true
					} else {
						api_result = false; // get response false

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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
				Toast.makeText(getApplicationContext(),
						"Records successfully updated.", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Please try again later.", Toast.LENGTH_LONG).show();

			}
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == bsave) {

			// get all edittext values
			String date = etdate.getText().toString();
			String time = ettime.getText().toString();
			String cp = etcp.getText().toString();
			String bg = etbg.getText().toString();
			String qa = etqa.getText().toString();
			String bi = etbi.getText().toString();
			String commnet = etcomm.getText().toString();

			// if fields are empty, display message
			if (date.equals("") || time.equals("") || cp.equals("")
					|| bg.equals("") || qa.equals("") || bi.equals("")
					|| commnet.equals("")) {
				Toast.makeText(getApplicationContext(),
						"Please fill all fields.", Toast.LENGTH_LONG).show();
			} else {
				// store all values in static class
				model = new Model(date, time, cp, bg, qa, bi, commnet);
				new callApi(model).execute(); // Create asynctask class for
												// calling Webservice.
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
