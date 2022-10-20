package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.clientType;
import model.exceptions.ValidationException;
import model.services.clientTypeServices;

public class ClientTypeFormController implements Initializable {

	private clientType entity;
	private clientTypeServices service;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtidType;
	
	@FXML
	private TextField txttypeName;
	
	@FXML 
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Label lblError;
	
	public void setClientType(clientType entity) {
		this.entity = entity;
	}
	
	public void setClientTypeService (clientTypeServices service) {
		this.service = service;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	
	@FXML
	private void btSaveOnAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null.");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null.");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}	
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private clientType getFormData() {
		clientType obj = new clientType();
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setIdClientType(Utils.tryParseToInt(txtidType.getText()));
		obj.setTypeName(txttypeName.getText());
		if (txttypeName.getText() == null || txttypeName.getText().trim().equals("")) {
			exception.addError("name", "O Campo nome não pode estar vazio");
		}
		if (exception.getErrors().size() > 0 ) {
			throw exception;
		}
		
		return obj;
	}
	
	@FXML
	private void btCancelOnAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtidType);
		Constraints.setTextFieldMaxLength(txttypeName, 200);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtidType.setText(String.valueOf(entity.getIdClientType()));
		txttypeName.setText(entity.getTypeName());
	}
	
	private void setErrorMessages(Map<String, String> error ) {
		Set<String> fields = error.keySet();
		
		if (fields.contains("name")) {
			lblError.setText(error.get("name"));
		}


	}

}
