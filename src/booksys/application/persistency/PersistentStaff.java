package booksys.application.persistency;

import booksys.application.domain.Staff;

public class PersistentStaff extends Staff {

	private int oid;
	
	PersistentStaff(int oid, String id, String pass, String name, String check){
		super(id,pass,name,check);
		this.oid = oid;
	}
	
	int getOid() {
		return oid;
	}
	
}
