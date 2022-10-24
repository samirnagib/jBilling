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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.clientType;
import model.entities.clientes;
import model.entities.owner;
import model.exceptions.ValidationException;
import model.services.OwnerService;
import model.services.clientTypeServices;
import model.services.clientesService;

public class ClientesFormController implements Initializable {
	
	private clientes entity;
	private clientesService service;
	private clientTypeServices typeService;
	private OwnerService ownerService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtidClient;
	
	@FXML
	private TextField txtclientName;
	
	@FXML
	private TextField txtclientHostname;
	
	@FXML
	private TextField txtclientUUID;
	
	@FXML
	ComboBox<clientType> cbClientType;
	
	@FXML
	ComboBox<owner> cbOwner;
	
	@FXML
	private Button btSalvar;
	
	@FXML 
	private Button btCancelar;
	
	@FXML
	private Label lbError1;
	
	@FXML
	private Label lbError2;
	
	@FXML
	private Label lbError3;
	
	
	private ObservableList<clientType> obsListTYPE;
	
	private ObservableList<owner> obsListOWNER;

	public void setClient(clientes entity) {
		this.entity = entity;
	}
	
	public void setServices(clientesService service, clientTypeServices typeService, OwnerService ownerService) {
		this.service = service;
		this.typeService = typeService;
		this.ownerService = ownerService;
	}
	

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
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
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
		
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}	
		
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtidClient);
		Constraints.setTextFieldMaxLength(txtclientName, 200);
		Constraints.setTextFieldMaxLength(txtclientHostname, 200);
		
		initializeComboBoxClientType();
		initializeComboBoxOwner();
	}
	
	private clientes getFormData() {
		clientes obj = new clientes();
		ValidationException exception = new ValidationException("Validation error");
		
		obj.setIdClient(Utils.tryParseToInt(txtidClient.getText()));
		
		if(txtclientName.getText() == null || txtclientName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		
		if(txtclientUUID.getText() == null || txtclientUUID.getText().trim().equals("")) {
			exception.addError("uuid", "Este campo não pode estar em branco.");
		}
		
		obj.setClientName(txtclientName.getText());
		obj.setClientHostname(txtclientHostname.getText());
		obj.setUuidClient(txtclientUUID.getText().toUpperCase());
		obj.setClientType(cbClientType.getValue());
		obj.setOwner(cbOwner.getValue());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		
		return obj;
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		
		txtidClient.setText(String.valueOf(entity.getIdClient()));
		txtclientName.setText(entity.getClientName());
		txtclientHostname.setText(entity.getClientHostname());
		txtclientUUID.setText(entity.getUuidClient());
		if (entity.getClientType() == null ) {
			cbClientType.getSelectionModel().selectFirst();
		} else {
			cbClientType.setValue(entity.getClientType());
		}
		if (entity.getOwner() == null) {
			cbOwner.getSelectionModel().selectFirst();
		} else {
			cbOwner.setValue(entity.getOwner());
		}
		
		
	}

	
	public void loadAssociatedObjects() {
		if (typeService == null) {
			throw new IllegalStateException("typeService was null");
		}
		
		if (ownerService == null) {
			throw new IllegalStateException("ownerService was null");
		}
		
		// Carregar os objetos do tipo de servidor
		List<clientType> lsct = typeService.findAll();
		obsListTYPE = FXCollections.observableArrayList(lsct);
		cbClientType.setItems(obsListTYPE);
		
		// Carregar os objetos do Respons�vel 
		List<owner> lsOw = ownerService.findAll();
		obsListOWNER = FXCollections.observableArrayList(lsOw);
		cbOwner.setItems(obsListOWNER);
		
	}
	
	private void initializeComboBoxClientType() {
		Callback<ListView<clientType>, ListCell<clientType>> factory = lv -> new ListCell<clientType>() {
			@Override
			protected void updateItem(clientType item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getTypeName());
			}
		};
		cbClientType.setCellFactory(factory);
		cbClientType.setButtonCell(factory.call(null));
	}
	
	private void initializeComboBoxOwner() {
		Callback<ListView<owner>, ListCell<owner>> factory = lv -> new ListCell<owner>() {
			@Override
			protected void updateItem(owner item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getOwName());
			}
		};
		cbOwner.setCellFactory(factory);
		cbOwner.setButtonCell(factory.call(null));
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			lbError1.setText(errors.get("name"));
		}
		
		if (fields.contains("uuid")) {
			lbError3.setText(errors.get("uuid"));
		}
		
	}

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}

}
