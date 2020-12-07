/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation.controller ;
import java.sql.Date ;

import booksys.application.domain.BookingObserver;
import booksys.application.domain.BookingSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;


public class DateDialog
{	
	@FXML
	private TextField tf;
	boolean confirmed ;

	CanvasUI c;
	
	public DateDialog(CanvasUI c) {
		this.c = c;
	}
	
	@FXML
	void cancel(ActionEvent event) {
		confirmed = false ;
		tf.getScene().getWindow().hide();
	}

	@FXML
	void ok(ActionEvent event) {
		confirmed = true ;
		tf.getScene().getWindow().hide();
		c.displayDateCheck(this);
	}
	
	boolean isConfirmed()
	{
		return confirmed ;
	}

	Date getDate()
	{
		return Date.valueOf(tf.getText()) ;
	}

}
