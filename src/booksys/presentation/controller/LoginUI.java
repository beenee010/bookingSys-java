package booksys.presentation.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import booksys.application.domain.BookingSystem;
import booksys.application.domain.Staff;
import booksys.application.persistency.PersistentStaff;
import booksys.application.persistency.StaffMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginUI {
	
    @FXML private Button loginBtn;
    @FXML private TextField idField;
    @FXML private PasswordField passwordField;
    private Window w;
    
	public LoginUI() {
		w = Window.getInstance();
	}
    
    
    @FXML
    void login(ActionEvent event) throws IOException {
    	String id = idField.getText();
    	String password = passwordField.getText();
    	PersistentStaff s = StaffMapper.getInstance().searchStaff(id);
        Stage stage = (Stage)loginBtn.getScene().getWindow();
        
    	if(s == null) {
    		ConfirmDialogOk d = new ConfirmDialogOk("로그인 실패");
    		w.confirmDialogOk(d, stage);
    	}
    	else if(id.equals(s.getId()) && password.equals(s.getPassword())) {
    		w.canvas(stage,s);
    	}

    }

    @FXML
    void quit(ActionEvent event) {
    	Stage stage = (Stage) loginBtn.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void register(ActionEvent event) throws IOException {
    	Stage stage = (Stage)loginBtn.getScene().getWindow();
    	w.adminLoginDialog(stage, loginBtn, "register");
    }
	
    @FXML
    void tableSetting(ActionEvent event) throws IOException {
    	Stage stage = (Stage)loginBtn.getScene().getWindow();
    	w.adminLoginDialog(stage, loginBtn, "table");
    }
}
