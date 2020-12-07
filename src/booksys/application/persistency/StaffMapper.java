package booksys.application.persistency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;

import booksys.storage.Database;

public class StaffMapper {

	// Singleton :

	private static StaffMapper uniqueInstance;
	
	public static StaffMapper getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new StaffMapper();
		}
		return uniqueInstance;
	}
	
	public PersistentStaff searchStaff(String id) {
		PersistentStaff s = null;
		
		if(s == null) {
			String sql = "SELECT * FROM STAFF WHERE id = '" + id + "'";
			s = getStaff(sql);
		}
		return s;
	}
	
	public PersistentStaff createStaff(String id, String pass, String name, String check) {
		PersistentStaff s = null;
		if(s == null) {
			try {
				Statement stmt
				= Database.getInstance().getConnection().createStatement() ;
				int createCount
				= stmt.executeUpdate("INSERT INTO STAFF (oid, id, password, name, \"check\")" + 
				"VALUES (staffoid.nextval, '" + id + "', '" + pass+ "', '" + name + "', '" + check + "')");
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			s = searchStaff(id);
		}
		return s;
	}
	
	private PersistentStaff getStaff(String sql) {
		PersistentStaff s = null;
		try {
			Statement stmt
			= Database.getInstance().getConnection().createStatement() ;
			ResultSet rset
			= stmt.executeQuery(sql) ;
			while (rset.next()) {
				int    oid   = rset.getInt(1) ;
				String id  = rset.getString(2) ;
				String pass = rset.getString(3) ;
				String name = rset.getString(4) ;
				String check = rset.getString(5) ;
				s = new PersistentStaff(oid, id, pass, name, check) ;
			}
			rset.close() ;
			stmt.close() ;
		}
		catch (SQLException e) {
		}
		return s ;
	}
	
}
