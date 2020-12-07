/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.persistency ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration ;
import java.util.Hashtable ;

import booksys.application.domain.Customer;
import booksys.storage.Database;

public class CustomerMapper
{
	// Implementation of hidden cache

	private Hashtable cache ;

	private PersistentCustomer getFromCache(int oid)
	{
		Integer key = new Integer(oid) ;
		return (PersistentCustomer) cache.get(key) ;
	}

	private PersistentCustomer getFromCacheByDetails(String name, String phone)
	{
		PersistentCustomer c = null ;
		Enumeration enums = cache.elements() ;
		while (c == null && enums.hasMoreElements()) {
			PersistentCustomer tmp = (PersistentCustomer) enums.nextElement() ;
			if (name.equals(tmp.getName()) && phone.equals(tmp.getPhoneNumber())) {
				c = tmp ;
			}
		}
		return c ;
	}

	private void addToCache(PersistentCustomer c)
	{
		Integer key = new Integer(c.getId()) ;
		cache.put(key, c) ;
	}

	// Constructor:

	private CustomerMapper()
	{
		cache = new Hashtable() ;
	}

	// Singleton:

	private static CustomerMapper uniqueInstance ;

	public static CustomerMapper getInstance()
	{
		if (uniqueInstance == null) {
			uniqueInstance = new CustomerMapper() ;
		}
		return uniqueInstance ;
	}

	public PersistentCustomer getCustomer(String n, String p)
	{
		PersistentCustomer c = getFromCacheByDetails(n, p) ;
		if (c == null) {
			c = getCustomer("SELECT * FROM Customer WHERE name = '" + n
					+ "' AND phoneNumber = '" + p + "'") ;
			if (c == null) {
				c = createCustomer(n, p) ;
			}
			addToCache(c) ;
		}
		return c ;
	}

	PersistentCustomer getCustomerForOid(int oid)
	{
		PersistentCustomer c = getFromCache(oid) ;
		if (c == null) {
			c = getCustomer("SELECT * FROM Customer WHERE oid ='" + oid + "'") ;
			if (c != null) {
				addToCache(c) ;
			}
		}
		return c ;
	}
	

	// Add a customer to the database

	PersistentCustomer createCustomer(String name, String phone)
	{
		PersistentCustomer c = getFromCacheByDetails(name, phone) ;
		if (c == null) {
			try {
				Statement stmt
				= Database.getInstance().getConnection().createStatement() ;
				int updateCount
				= stmt.executeUpdate("INSERT INTO Customer (oid, name, phoneNumber, count)" +
						"VALUES (customerOid.nextval,'" + name + "', '" + phone + "', 0)") ;
				stmt.close() ;
			}
			catch (SQLException e) {
				e.printStackTrace() ;
			}
			c = getCustomer(name, phone) ;
		}
		return c ;
	}

	private PersistentCustomer getCustomer(String sql)
	{
		PersistentCustomer c = null ;
		try {
			Statement stmt
			= Database.getInstance().getConnection().createStatement() ;
			ResultSet rset
			= stmt.executeQuery(sql) ;
			while (rset.next()) {
				int    oid   = rset.getInt(1) ;
				String name  = rset.getString(2) ;
				String phone = rset.getString(3) ;
				int count = rset.getInt(4);
				c = new PersistentCustomer(oid, name, phone, count) ;
			}
			rset.close() ;
			stmt.close() ;
		}
		catch (SQLException e) {
			e.printStackTrace() ;
		}
		return c ;
	}
	
	public void updateCustomer(Customer c)
	{
		int count = c.getService();
		int oid = c.getOid();
		try {
			Statement stmt
			= Database.getInstance().getConnection().createStatement() ;
			int rset
			= stmt.executeUpdate("UPDATE CUSTOMER SET COUNT = " + count + " WHERE oid = " + oid) ;

			stmt.close() ;
		}
		catch (SQLException e) {
			e.printStackTrace() ;
		}
	}	
}
