package com.mpe.toll_logger;

public class Event {
	private int id;
	private String toll;
	private int date;
	private boolean time_of_day;

	public Event(String where, int when, boolean when_tod) {
		this.toll = where;
		this.date = when;
		this.time_of_day = when_tod;
	}
	public Event() {
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToll() {
		return toll;
	}
	public void setToll(String where) {
		this.toll = where;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int when) {
		this.date = when;
	}
	public boolean getToD() {
		return time_of_day;
	}
	public void setToD(boolean when_tod) {
		this.time_of_day = when_tod;
	}

}