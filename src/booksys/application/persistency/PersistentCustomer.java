/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.persistency ;

import booksys.application.domain.Customer;

class PersistentCustomer extends Customer
{
  private int oid ;

  PersistentCustomer(int id, String n, String p, int c)
  {
    super(n, p, c, id) ;
    oid = id ;
  }

  int getId() {
    return oid ;
  }
}
