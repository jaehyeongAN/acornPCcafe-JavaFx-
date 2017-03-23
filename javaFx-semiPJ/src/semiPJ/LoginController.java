package semiPJ;

import java.io.IOException;
 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class LoginController {
    @FXML private Label lblStatus;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private Button loginbt;
    
    public void Login(ActionEvent event) throws Exception{
        if(txtUserName.getText().equals("admin") && txtPassword.getText().equals("pass")){
            lblStatus.setText("Login Success");
            
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MainFxml.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // 로그인 되었을 때, 창 닫기
            loginbt.setOnAction(new EventHandler<ActionEvent>() {
    			
    			@Override
    			public void handle(ActionEvent event) {
    				// TODO Auto-generated method stub
    				primaryStage.close();
    			}
    		});
            
        }else{
            lblStatus.setText("Login Failed");
        }
        
        
    }
    
}


