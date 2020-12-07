/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import booksys.application.persistency.* ;

import java.sql.Date ;
import java.sql.Time ;
import java.util.Vector ;

class Restaurant
{
  BookingMapper  bm = BookingMapper.getInstance() ;
  CustomerMapper cm = CustomerMapper.getInstance() ;
  TableMapper    tm = TableMapper.getInstance() ;
  StaffMapper	 sm = StaffMapper.getInstance();
  
  static Staff getStaff(String id, String pass) {
	  return StaffMapper.getInstance().searchStaff(id);
  }
  
  Vector getBookings(Date date)
  {
    return bm.getBookings(date) ;
  }
  
  static Vector getSpecificBookings(String booking, String subject, String object) {
	  return BookingMapper.getInstance().getSpecificBookings(booking, subject, object);
  }

  Customer getCustomer(String name, String phone)
  {
    return cm.getCustomer(name, phone) ;
  }
  
  Table getTable(int n)
  {
    return tm.getTable(n) ;
  }

  static Vector getTableNumbers()
  {
    return TableMapper.getInstance().getTableNumbers() ;
  }
  
  static Vector getTableCovers() {
	  return TableMapper.getInstance().getTablecovers();
  }

  public Booking makeReservation(int covers, Date date, Time time,
				     int tno, String name, String phone, int count, String staffId)
  {
    Table t = getTable(tno) ;
    Customer c = getCustomer(name, phone) ;

    return bm.createReservation(covers, date, time, t, c, null, null, staffId) ;
  }

  public Booking makeWalkIn(int covers, Date date,
			   Time time, int tno, String staffId)
  {
    Table t = getTable(tno) ;
    return bm.createWalkIn(covers, date, time, t, null, staffId) ;
  }

  public void updateBooking(Booking b)
  {
    bm.updateBooking(b) ;
  }
  
  public void removeBooking(Booking b) {
    bm.deleteBooking(b) ;
  }
  
  public void createStaff(String id, String pass, String name, String check) {
	  sm.createStaff(id, pass, name, check);
  }
}
