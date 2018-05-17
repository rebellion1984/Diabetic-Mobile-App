package com.example.listing;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.robel_project.R;
import com.example.serviceHandler.Model;
import com.example.serviceHandler.ServiceHandler;

public class Listing extends Activity {

	ListView list;
	private static final String TAG_date = "htc_date";
	private static final String TAG_time = "htc_time";
	private static final String TAG_cp = "htc_cp";
	private static final String TAG_bg = "htc_bg";
	private static final String TAG_qa = "htc_qa";
	private static final String TAG_bi = "htc_bi";
	private static final String TAG_comments = "htc_comments";
	JSONArray contacts = null;
	ArrayList<Model> array = new ArrayList<Model>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listing);
		list = (ListView) findViewById(R.id.listview);
		new callApi().execute();
	}

	private class callApi extends AsyncTask<Void, Void, Void> {
		ProgressDialog pDialog;
		boolean api_result = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(Listing.this);
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
			String jsonStr = sh.makeServiceCall(ServiceHandler.show_listing_url, ServiceHandler.POST);

			// get json response
			Log.i("Response: ", "" + jsonStr);

			if (jsonStr != null) {
				try {
					
					api_result=true;
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
				Toast.makeText(getApplicationContext(),
						"Records Successfully updated.", Toast.LENGTH_LONG)
						.show();

			} else {
				Toast.makeText(getApplicationContext(),
						"Please try again later.", Toast.LENGTH_LONG).show();

			}
			CustomAdapter adapter = new CustomAdapter(Listing.this, array);
			list.setAdapter(adapter);
		}

	}

}
