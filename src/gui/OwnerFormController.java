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
import model.entities.owner;
import model.exceptions.ValidationException;
import model.services.OwnerService;

public class OwnerFormController implements Initializable {
	
	private owner entity;
	private OwnerService service;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private Label lblError;
	
	@FXML
	private TextField txtidOwner;
	
	@FXML
	private TextField txtowName;
	
	@FXML
	private TextField txtowEmail1;
	

	@FXML
	private TextField txtowEmail2;
	

	@FXML
	private TextField txtowProjetoArea;
	

	@FXML
	private TextField txtowAR;
	

	@FXML
	private Button btSave; 
	

	@FXML
	private  Button btCancel;
	
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saverOrUpdate(entity);
			notifyDataChangeListerners();
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error Saving object", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
	}
	
	private void notifyDataChangeListerners() {
		for(DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	private owner getFormData() {
		owner obj = new owner();
		ValidationException exception = new ValidationException("Validation Error");
		obj.setIdOwner(Utils.tryParseToInt(txtidOwner.getText()));
		if (txtowName.getText() == null || txtowName.getText().trim().contentEquals("")) {
			exception.addError("name", "O Campo nome não pode ser vazio.");
		}
		
		obj.setOwName(txtowName.getText());
		obj.setOwEmail1(txtowEmail1.getText());
		obj.setOwEmail2(txtowEmail2.getText());
		obj.setOwProjetoArea(txtowProjetoArea.getText());
		obj.setOwAR(txtowAR.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setOwner(owner entity) {
		this.entity = entity;
	}
	
	public void setOwnerService(OwnerService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListerner(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtidOwner);
		Constraints.setTextFieldMaxLength(txtowName, 200);
		Constraints.setTextFieldMaxLength(txtowEmail1, 200);
		Constraints.setTextFieldMaxLength(txtowEmail2, 200);
		Constraints.setTextFieldMaxLength(txtowProjetoArea, 200);
		Constraints.setTextFieldMaxLength(txtowAR, 200);
		
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtidOwner.setText(String.valueOf(entity.getIdOwner()));
		txtowName.setText(entity.getOwName());
		txtowEmail1.setText(entity.getOwEmail1());
		txtowEmail2.setText(entity.getOwEmail2());
		txtowProjetoArea.setText(entity.getOwProjetoArea());
		txtowAR.setText(entity.getOwAR());
		
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("name")) {
			lblError.setText(errors.get("name"));
		}
	}

}
