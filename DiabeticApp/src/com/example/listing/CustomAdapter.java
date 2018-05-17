package com.example.listing;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.robel_project.R;
import com.example.serviceHandler.Model;

public class CustomAdapter extends BaseAdapter {
	Context ctx;
	private ArrayList<Model> arrRecord;

	// List content holder;

	CustomAdapter(Context c, ArrayList<Model> list) {
		arrRecord = new ArrayList<Model>();
		arrRecord = list;
		ctx = c;
	}

	public int getCount() {
		return arrRecord.size();
	}

	public Object getItem(int position) {
		return arrRecord.get(position);
	}

	public View getView(int position, View v, ViewGroup parent) {
		if (v == null) {
			System.out.println("~~~~~~~~~~~GetView Method Called");
			// LayoutInflater
			// mInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LayoutInflater mInflater = LayoutInflater.from(ctx);
			v = mInflater.inflate(R.layout.list_item, null);
		}

		TextView date = (TextView) v.findViewById(R.id.txt_date);
		TextView time = (TextView) v.findViewById(R.id.txt_time);
		TextView bg = (TextView) v.findViewById(R.id.txt_bge);
		TextView cp = (TextView) v.findViewById(R.id.txt_cp);
		TextView qa = (TextView) v.findViewById(R.id.txt_qa);
		TextView bi = (TextView) v.findViewById(R.id.txt_bi);
		TextView comment = (TextView) v.findViewById(R.id.txt_copmment);

		Model s = new Model();
		s = arrRecord.get(position);
		date.setText("Date : "+s._date);
		time.setText("Time : "+s._time);
		bg.setText("BG : "+s._bg);
		cp.setText("CP : "+s._cp);
		qa.setText("QA : "+s._qa);
		bi.setText("BI : "+s._bi);
		comment.setText("Comment : "+s._comment);

		return v;
	}

	public long getItemId(int position) {
		return position;
	}

}
