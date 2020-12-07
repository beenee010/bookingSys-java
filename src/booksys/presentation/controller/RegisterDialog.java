package booksys.presentation.controller;

import java.io.IOException;

import booksys.application.persistency.PersistentStaff;
import booksys.application.persistency.StaffMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterDialog {

    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TextField passwordField;
    @FXML private TextField passwordField2;
    @FXML private CheckBox adminCheck;
	private Window w;
	
	public RegisterDialog() {
		w = Window.getInstance();
	}
    
    @FXML
    void quit(ActionEvent event) {
    	Stage stage = (Stage) idField.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void register(ActionEvent event) throws IOException {
    	ConfirmDialogOk d;
    	String id = idField.getText();
    	String pass = passwordField.getText();
    	String name = nameField.getText();
    	String pass2 = passwordField2.getText();
    	String check = Boolean.toString(adminCheck.isSelected());
    	Stage stage = (Stage) idField.getScene().getWindow();
    	
    	if(name.equals("") || id.equals("") || pass.equals("") || pass2.equals("")) {
    		d = new ConfirmDialogOk("���� �Է��� �ּ���.");
    		w.confirmDialogOk(d,stage);
    	}
    	else if(!passwordField.getText().equals(passwordField2.getText())) {
    		d = new ConfirmDialogOk("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
    		w.confirmDialogOk(d,stage);
    	}
    	else {
    		PersistentStaff s;
    		
    		s = StaffMapper.getInstance().searchStaff(id);
    		if(s != null) {
    			d = new ConfirmDialogOk("�̹� �����ϴ� �����Դϴ�.");
    			w.confirmDialogOk(d,stage);
    			return;
    		}
    		else {
    			s = StaffMapper.getInstance().createStaff(id, pass, name, check);
    			System.out.println(s.getName());
    		}
    		System.out.println(s.getName());
    		d = new ConfirmDialogOk(s.getName() +" ���� ��� ���� !");
    		w.confirmDialogOk(d,stage);
    	}
    }

    @FXML
    void undo(ActionEvent event) throws IOException {
    	Stage stage = (Stage) idField.getScene().getWindow();
    	w.loginUI(stage);
    }
	

	
}
