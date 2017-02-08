package com.wavelabs.service;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wavelabs.model.Bus;
import com.wavelabs.model.Passenger;
import com.wavelabs.utility.Helper;

/**
 * Performs create and delete operations on {@link Bus}, {@link Passenger}.
 * 
 * @author gopikrishnag
 *
 */
public class PersistanceService {

	/**
	 * <h3>Persist bus and associated passengers in relational database.</h3>
	 * <ul>
	 * <li>Create transient bus object</li>
	 * <li>adds all passengers to set</li>
	 * <li>sets properties to bus</li>
	 * <li>persist bus</li>
	 * </ul>
	 * 
	 * @param id
	 *            of bus
	 * @param name
	 *            of bus
	 * @param capacity
	 *            of bus
	 * @param startPoint
	 *            of bus
	 * @param destination
	 *            of bus
	 * @param passengers
	 *            of bus
	 */
	public static void createBus(int id, String name, int capacity, String startPoint, String destination,
			Passenger[] passengers) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Bus garuda = new Bus();
		garuda.setId(id);
		garuda.setName(name);
		garuda.setCapacity(capacity);
		garuda.setDestination(destination);
		garuda.setStartPoint(startPoint);
		Set<Passenger> setOfPassenger = new HashSet<Passenger>();
		for (Passenger p : passengers) {
			setOfPassenger.add(p);
		}
		garuda.setPassenger(setOfPassenger);
		session.save(garuda);
		tx.commit();
		session.close();
	}

	/**
	 * <h3>persist Passenger and associated bus</h3>
	 * <ul>
	 * <li>create Transient passenger object</li>
	 * <li>sets associated bus to passenger</li>
	 * <li>persist passenger</li>
	 * </ul>
	 * 
	 * @param id
	 *            of passenger
	 * @param name
	 *            of passenger
	 * @param phone
	 *            of passenger
	 * @param date
	 *            traveling data of passenger
	 * @param time
	 *            traveling time of passenger
	 * @param bus
	 *            on which passenger going to travel
	 */
	public static void createPassenger(int id, String name, String phone, Date date, Time time, Bus bus) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Passenger p1 = new Passenger();
		p1.setId(id);
		p1.setName(name);
		p1.setPhone(phone);
		p1.setDate(date);
		p1.setTime(time);
		p1.setBus(bus);
		session.save(p1);
		tx.commit();
		session.close();
	}

	/**
	 * <h3>Delete bus for matching id</h3>
	 * <ul>
	 * <li>gets the bus from table</li>
	 * <li>performs delete operation</li>
	 * </ul>
	 * 
	 * @param id
	 *            of Bus
	 */
	public static void deleteBus(int id) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Bus bus = (Bus) session.get(Bus.class, id);
		session.delete(bus);
		tx.commit();
		session.close();
	}

	/**
	 * <h3>deletes the passenger for matching id</h3>
	 * <ul>
	 * <li>gets passenger from table</li>
	 * <li>performs delete operation
	 * <li>
	 * </ul>
	 * 
	 * @param id
	 *            of passenger
	 */
	public static void deletePassenger(int id) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		Passenger passenger = (Passenger) session.get(Passenger.class, id);
		session.delete(passenger);
		tx.commit();
		session.close();
	}
}
