package com.wavelabs.model;

import java.util.Set;
/**
 * Entity represents bus table in relational database.
 * @author gopikrishnag
 *
 */
public class Bus {

	private int id;
	private String name;
	private int capacity;
	private String startPoint;
	private String destination;
	private Set<Passenger> passenger;

	public Bus() {

	}

	public Bus(int id, String name, int capacity, String startPoint, String destination) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.startPoint = startPoint;
		this.destination = destination;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Set<Passenger> getPassenger() {
		return passenger;
	}

	public void setPassenger(Set<Passenger> passenger) {
		this.passenger = passenger;
	}

}
