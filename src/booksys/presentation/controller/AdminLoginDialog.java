package booksys.presentation.controller;

import java.io.IOException;

import booksys.application.domain.Staff;
import booksys.application.persistency.StaffMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginDialog {

	@FXML private PasswordField adminPassTf;
	@FXML private TextField adminIdTf;
	@FXML private Button loginBtn;
	private Stage preStage;
	private String what;
	private Window w;
	
	public AdminLoginDialog(Stage preStage, String what) {
		this.preStage = preStage;
		this.what = what;
		w = Window.getInstance();
	}
	
	@FXML
	void adminLogin(ActionEvent event) throws IOException {
		String adminId = adminIdTf.getText();
		String adminPass = adminPassTf.getText();
		Staff a = StaffMapper.getInstance().searchStaff(adminId);
		Stage stage = (Stage) loginBtn.getScene().getWindow();
		
		if(a == null) {
			ConfirmDialogOk d = new ConfirmDialogOk("로그인 실패");
			w.confirmDialogOk(d,stage);
		}
		else if(adminId.equals(a.getId()) && adminPass.equals(a.getPassword())) {
			if((a.getCheck()).equals("true")) {
				stage.close();
				if(what.equals("table"))
					w.tableSetting(preStage, loginBtn);
				else if(what.equals("register"))
					w.register(preStage);
			}
			else {
				stage.close();
				w.confirmDialogOk(new ConfirmDialogOk("관리자 권한이 없습니다."), stage);
			}
		}
		else {
			w.confirmDialogOk(new ConfirmDialogOk("비밀번호가 일치하지 않습니다."), stage);
		}
	}

	@FXML
	void undo(ActionEvent event) {
		Stage stage = (Stage) loginBtn.getScene().getWindow();
		stage.close();
	}
}
