package com.example.serviceHandler;

public class Model {

	public String _date;
	public String _time;
	public String _cp;
	public String _bg;
	public String _qa;
	public String _bi;
	public String _comment;


	public Model(String date, String time, String cp, String bg, String qa, String bi,String comment) {
		_date = date;
		_time = time;
		_cp = cp;
		_bg = bg;
		_qa = qa;
		_bi = bi;
		_comment=comment;
	}

	public Model() {
		// TODO Auto-generated constructor stub
	}

	public String get_date() {
		return _date;
	}

	public void set_date(String _date) {
		this._date = _date;
	}

	public String get_time() {
		return _time;
	}

	public void set_time(String _time) {
		this._time = _time;
	}

	public String get_cp() {
		return _cp;
	}

	public void set_cp(String _cp) {
		this._cp = _cp;
	}

	public String get_bg() {
		return _bg;
	}

	public void set_bg(String _bg) {
		this._bg = _bg;
	}

	public String get_qa() {
		return _qa;
	}

	public void set_qa(String _qa) {
		this._qa = _qa;
	}

	public String get_bi() {
		return _bi;
	}

	public String get_comment() {
		return _comment;
	}

	public void set_comment(String _comment) {
		this._comment = _comment;
	}

	public void set_bi(String _bi) {
		this._bi = _bi;
	}

}
