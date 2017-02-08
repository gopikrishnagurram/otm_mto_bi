package com.wavelabs.model;

import java.sql.Time;
import java.util.Date;
/**
 * Entity represents passenger table in relational database.
 * @author gopikrishnag
 *
 */
public class Passenger {

	private int id;
	private String name;
	private Time time;
	private Date date;
	private String phone;
	private Bus bus;

	public Passenger() {

	}

	public Passenger(int id, String name, Time time, Date date, String phone) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.date = date;
		this.phone = phone;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
