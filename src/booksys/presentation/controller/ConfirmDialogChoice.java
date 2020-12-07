/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.presentation.controller ;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import booksys.application.domain.BookingSystem;
import booksys.application.persistency.TableMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfirmDialogChoice implements Initializable
{
	private Window w;
	protected boolean confirmed ;
	private String text;
	private String what;
	private int oid;
	private BookingSystem bs;
	@FXML private Label label;

	public ConfirmDialogChoice(String text, String what) {
		this.text = text;
		this.what = what;
		bs = BookingSystem.getInstance();
		w = Window.getInstance();
	}

	public ConfirmDialogChoice(String text, String what, String oid) {
		this(text,what);
		this.oid = Integer.parseInt(oid);
	}


	@FXML
	void windowAction(WindowEvent event) {
		confirmed = false;
		Stage stage = (Stage) label.getScene().getWindow();
		stage.close();
	}

	@FXML
	void cancel(ActionEvent event) throws IOException {
		Stage stage = (Stage) label.getScene().getWindow();
		confirmed = false ;
		stage.close();
		switch(what) {
		case "overflow":
			bs.cancel();
			break;
		case "double":
			break;
		case "record":
			break;
		case "cancel":
			break;
		case "deleteTable":
			break;
		}
	}

	@FXML
	void ok(ActionEvent event) throws IOException {
		Stage stage = (Stage) label.getScene().getWindow();
		confirmed = true ;
		stage.close();
		switch(what) {
		case "overflow":
			break;
		case "double":
			break;
		case "record":
			break;
		case "cancel":
			bs.cancelExec();
			break;
		case "deleteTable":
			TableMapper.getInstance().deleteTable(oid);
			ConfirmDialogOk d = new ConfirmDialogOk("삭제가 완료되었습니다 !");
			w.confirmDialogOk(d,stage);
			break;
		}
	}


	boolean isConfirmed()
	{
		return confirmed ;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		label.setText(text);
	}
}
