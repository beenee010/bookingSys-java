/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import booksys.application.persistency.CustomerMapper;

public class Customer
{
  private String name ;
  private String phoneNumber ;
  private int service;
  private int oid;
  
  public Customer(String n, String p, int c, int id)
  {
    name = n ;
    phoneNumber = p ;
    service = c;
    oid = id;
  }

  public String getName()
  {
    return name ;
  }

  public String getPhoneNumber()
  {
    return phoneNumber ;
  }
  
  public int getService()
  {
	  return service;
  }
  
  public int getOid() {
	  return oid;
  }
  
  public void upCount() {
	  service += 1;
	  CustomerMapper.getInstance().updateCustomer(this);
  }
  
  public void downCount() {
	  service -= 1;
	  CustomerMapper.getInstance().updateCustomer(this);
  }
}
