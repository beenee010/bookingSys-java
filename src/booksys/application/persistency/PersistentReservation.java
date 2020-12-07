/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.persistency ;

import booksys.application.domain.* ;

class PersistentReservation
  extends Reservation implements PersistentBooking
{
  private int oid ;

  public PersistentReservation(int id, int c, java.sql.Date d, java.sql.Time t,
			       Table tab, Customer cust, java.sql.Time arr, java.sql.Time leave, String staffId)
  {
    super(c, d, t, tab, cust, arr, leave, staffId) ;
    oid = id ;
  }

  /* public because getId defined in an interface and hence public */
  
  public int getId() {
    return oid ;
  }
}
