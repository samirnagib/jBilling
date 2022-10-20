package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ConnectionViewController implements Initializable {
	
	@FXML
	private Button btLoad;
	
	@FXML
	private Button btSave;
	
	@FXML
	private TextField txtDBAdress;

	@FXML
	private TextField txtDBName;
	
	
	@FXML
	private TextField txtDBUserName;
	
	@FXML
	private PasswordField txtDBPasswd;
	
	@FXML
	private Label lblStatus;
	
	@FXML
	private void btLoadOnAction() {
		Properties dbdata = new Properties();
		try {
			FileInputStream fileconfig = new FileInputStream(Utils.loadFileConfig());
			dbdata.load(fileconfig);
			
		}
		catch (IOException e) {
			 
			e.printStackTrace();
		}
		txtDBAdress.setText(dbdata.getProperty("DB_URL"));
		txtDBName.setText(dbdata.getProperty("DB_NAME"));
		txtDBUserName.setText(dbdata.getProperty("DB_USER"));
		txtDBPasswd.setText(dbdata.getProperty("DB_PASS"));
		
		
	}
	
	@FXML
	private void btSaveOnAction() {
		Properties prop = new Properties();
		String sFileParameter="config.properties";
		String sSubFolder = System.getProperty("file.separator")+"config"+System.getProperty("file.separator");
		String appPath = System.getProperty("user.dir");
		String dbAdress;
		String dbName;
		String dbUsername;
		String dbPassword;
		
		if ( txtDBAdress.getText().isEmpty() != false|| txtDBAdress.getText().trim().equals("") ) {
			Alerts.showAlert("AVISO", "O Campo endereço do banco, não pode estar vazio.", "Preencha com o endereço ou ip do banco", AlertType.ERROR);
			txtDBAdress.requestFocus();
		}
		else if ( txtDBName.getText().isEmpty() != false || txtDBName.getText().trim().equals("") ) {
				Alerts.showAlert("AVISO", "O Campo nome do banco, não pode estar vazio.", "Preencha com o nome do banco", AlertType.ERROR);
				txtDBName.requestFocus();
		}
		else if ( txtDBUserName.getText().isEmpty() != false || txtDBUserName.getText().trim().equals("") ) {
			Alerts.showAlert("AVISO", "O Campo usuário do banco, não pode estar vazio.", "Preencha com o usuario do database", AlertType.ERROR);
			txtDBUserName.requestFocus();
		}
		else if ( txtDBPasswd.getText().isEmpty() != false || txtDBPasswd.getText().trim().equals("") ) {
			Alerts.showAlert("AVISO", "O Campo senha, não pode estar vazio.", "Preencha com a senha do usuario do database", AlertType.ERROR);
			txtDBUserName.requestFocus();
		} else {
		
			dbAdress = txtDBAdress.getText().toLowerCase();
			dbName = txtDBName.getText().toLowerCase();
			dbUsername = txtDBUserName.getText();
			dbPassword = txtDBPasswd.getText();
			
			prop.put("DB_URL", dbAdress );
			prop.put("DB_NAME", dbName );
			prop.put("DB_USER", dbUsername );
			prop.put("DB_PASS", dbPassword );
		
			try {
				Path path = Paths.get(appPath+sSubFolder);
				if ( Files.notExists(path) ) {
					Files.createDirectory(path); 
				}
				File novoFile = new File(appPath+sSubFolder+sFileParameter);
				novoFile.createNewFile();
				FileOutputStream outputStrem = new FileOutputStream(appPath+sSubFolder+sFileParameter);
				
		  		//Storing the properties file
				prop.store(outputStrem, "ARQUIVO DE CONFIGURAÇÃO");
				lblStatus.setText("Status: Arquivo de configuração criado.");
				
				outputStrem.close();
				btLoad.setDisable(false);
				txtDBAdress.clear();
				txtDBName.clear();
				txtDBUserName.clear();
				txtDBPasswd.clear();

			} catch (IOException e) {
				 
				e.printStackTrace();
			}
		}
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldMaxLength(txtDBAdress, 255);
		Constraints.setTextFieldMaxLength(txtDBName, 40);
		Constraints.setTextFieldMaxLength(txtDBUserName, 255);
		Constraints.setTextFieldMaxLength(txtDBPasswd, 255);
		if ( Utils.getFileStatus() ) {
			lblStatus.setText("Status: Arquivo de configuração existe.");
		} else {
			lblStatus.setText("Status: Arquivo de configuração não encontrado.");
			btLoad.setDisable(true);
		}
	}
	


}
