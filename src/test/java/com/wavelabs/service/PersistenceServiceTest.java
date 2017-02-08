package com.wavelabs.service;

import java.sql.Time;
import java.util.Date;

import org.hibernate.internal.util.xml.XmlDocument;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.wavelabs.metadata.CollectionAttributes;
import com.wavelabs.metadata.CollectionType;
import com.wavelabs.metadata.HbmFileMetaData;
import com.wavelabs.metadata.OneToManyAttributes;
import com.wavelabs.metadata.XmlDocumentBuilder;
import com.wavelabs.model.Bus;
import com.wavelabs.model.Passenger;
import com.wavelabs.tableoperations.CRUDTest;
import com.wavelabs.utility.Helper;

/**
 * performs unit test cases on {@link PersistanceService}.
 * <ul>
 * <li>verifies cascade values of mappings</li>
 * <li>verifies lazy attribute values on collection</li>
 * <li>verifies crud operation on {@link Bus} {@link Passenger}</li>
 * </ul>
 * 
 * @author gopikrishnag
 */
public class PersistenceServiceTest {

	HbmFileMetaData busHbm = null;
	HbmFileMetaData passengerHbm = null;
	CRUDTest crud = null;

	/**
	 * <p>
	 * Initializes {@link HbmFileMetaData}, {@link CRUDTest} Class objects. This
	 * objects useful through out all unit test cases.
	 * </p>
	 * 
	 */
	@BeforeTest
	public void intillization() {
		XmlDocumentBuilder xmb = new XmlDocumentBuilder();
		XmlDocument document = xmb.getXmlDocumentObject("src/main/resources/com/wavelabs/model/Bus.hbm.xml");
		XmlDocument document1 = xmb.getXmlDocumentObject("src/main/resources/com/wavelabs/model/Passenger.hbm.xml");
		busHbm = new HbmFileMetaData(document, Helper.getSessionFactory());
		passengerHbm = new HbmFileMetaData(document1, Helper.getSessionFactory());
		crud = new CRUDTest(Helper.getSessionFactory(), Helper.getConfiguration(), Helper.getSession());
	}

	/**
	 * <p>
	 * tests
	 * {@linkplain PersistanceService#createBus(int, String, int, String, String, Passenger[])}
	 * is successfully inserting record or not. if record is not inserted in
	 * table then test case will fail.
	 * </p>
	 */
	@SuppressWarnings("deprecation")
	@Test(description="test createBus method in PersistenceService inserting records or not.",priority=4,dependsOnMethods="testCascadeOnBus")
	public void testCreateBus() {
		PersistanceService.createBus(1, "GARUDA", 45, "Hyderabad", "Khammam",
				new Passenger[] { new Passenger(1, "Gopi", new Time(3, 24, 00), new Date(2017, 11, 11), "9032"),
						new Passenger(2, "Krishna", new Time(4, 24, 00), new Date(2017, 11, 11), "9032") });
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordInserted(Passenger.class, 1), true,"fails to insert passenger");
		Assert.assertEquals(crud.isRecordInserted(Passenger.class, 2), true,"fails to insert passenger");
		Assert.assertEquals(crud.isRecordInserted(Bus.class, 1), true,"failes to insert bus");
	}

	/**
	 * <p>
	 * tests the
	 * {@linkplain PersistanceService#createPassenger(int, String, String, Date, Time, Bus)}
	 * is successfully inserting record or not. If record is not inserted in
	 * table then test case will fail.
	 * </p>
	 */
	@SuppressWarnings("deprecation")
	@Test(priority = 2,description="verifies createBus is inserting records or not in table",dependsOnMethods="testCreateBus")
	public void testCreatePassenger() {
		PersistanceService.createPassenger(3, "RAJA", "91332", new Date(17, 12, 2017), new Time(11, 30, 00),
				(Bus) Helper.getSession().get(Bus.class, 1));
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordInserted(Passenger.class, 3), true,"fails to insert passenger");
	}

	/**
	 * <p>
	 * Tests that Passenger not the owner of relationship.
	 * </p>
	 * <ul>
	 * <li>{@code inverse="false"} at collection, then test case will fail
	 * <li>
	 * <li>{@code inverse="true"} at collection, then test case will pass.</li>
	 * </ul>
	 */
	@Test(description = "verifies inverse attribute value on bus mapping collection.",priority=1)
	public void testInverse() {
		Assert.assertEquals(busHbm.getAttributeOfSet(0, CollectionAttributes.inverse), "false",
				"inverse value should be false");
	}

	/**
	 * Checks cascade value on Set collection of Bus mapping. If cascade value
	 * is not satisfying requirement then test case will fail
	 */
	@Test(description="checks cascade values on collection",priority=3,dependsOnMethods="testLazy")
	public void testCascadeOnBus() {
		Assert.assertNotEquals(busHbm.getAttributeOfSet(0, CollectionAttributes.cascade), "save-update",
				"cascade=\"save-update\" not satisfied requirement");
		Assert.assertNotEquals(busHbm.getAttributeOfSet(0, CollectionAttributes.cascade), "delete-orphan",
				"cascade=\"delete-orphan\" not satisfied reqirement");
		Assert.assertNotEquals(busHbm.getAttributeOfSet(0, CollectionAttributes.cascade), "all",
				"cascade=\"all\" not satisfied for given requirement");
	}

	/**
	 * Checks lazy attribute value of Bus mapping collection.
	 * {@code lazy="true"} test case will pass, {@code lazy="false"} test case
	 * will fail.
	 */
	@Test(description = "checks lazy attribute value of bus mapping collection",priority=2,dependsOnMethods="testInverse")
	public void testLazy() {
		Assert.assertNotEquals(busHbm.getAttributeOfSet(0, CollectionAttributes.lazy), "false",
				"lazy=\"true\" at collection not satisfied given requirement");

	}

	/**
	 * Tests the {@link PersistanceService#deletePassenger(int)} is deleting
	 * record or not from table. If record is deleted from table then test case
	 * will pass.
	 */
	@Test(dependsOnMethods = { "testCreatePassenger",
			"testCreateBus" }, description = "checks deletePassenger(int) is deleting record or not from table")
	public void testDeletePassenger() {
		PersistanceService.deletePassenger(1);
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordDeleted(Passenger.class, 1), true);
	}

	/**
	 * Test the {@link PersistanceService#deleteBus(int)} is deleting record or
	 * not from table. If record is deleted then test case will pass.
	 */
	@Test(dependsOnMethods = "testDeletePassenger", description = "checks deleteBus(int) is deleting record or not from table")
	public void testDeleteBus() {
		PersistanceService.deleteBus(1);
		crud.setSession(Helper.getSession());
		Assert.assertEquals(crud.isRecordDeleted(Bus.class, 1), true, "fails to delete bus");
		Assert.assertEquals(crud.isRecordDeleted(Passenger.class, 1), true, "fails to delete a passenger");
		Assert.assertEquals(crud.isRecordDeleted(Passenger.class, 2), true,"failes to delete a passenger");
	}
	/**
	 * Closes SessionFactory.
	 */
	@AfterTest(description="closes all resources")
	public void closeResources() {
		Helper.getSessionFactory().close();
	}

}
