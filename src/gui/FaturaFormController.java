package gui;

import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import model.entities.BillTags;
import model.entities.clientes;
import model.entities.fatura;
import model.services.BillTagsServices;
import model.services.OwnerService;
import model.services.clientTypeServices;
import model.services.clientesService;
import model.services.faturaServices;

public class FaturaFormController implements Initializable {
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	private Button btCalcular;
	
	
	@FXML
	private ComboBox<BillTags> cbTags;
	
	@FXML
	private ComboBox<clientes> cbServer;
	
	@FXML
	private TextField txtSeq;

	@FXML
	private TextField txtRef;

	@FXML
	private TextField txtAgente;

	@FXML
	private TextField txtInstancia;

	@FXML
	private TextField txtBackupSet;

	@FXML
	private TextField txtSubCliente;

	@FXML
	private TextField txtStoragePolicy;

	@FXML
	private TextField txtCopia;

	@FXML
	private TextField txtVRFaixa;

	@FXML
	private TextField txtFEB;

	@FXML
	private TextField txtFEA;

	@FXML
	private TextField txtPriApp;

	@FXML
	private TextField txtProtApp;

	@FXML
	private TextField txtMedia;
	
	@FXML
	private Label lblVrTotal;

	@FXML
	private void btSaveOnAction(ActionEvent event) throws ParseException {
		System.out.println("Save me!");
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (fatService == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			fatService.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	@FXML
	private void btCalcularOnAction() {
		valorTotal = calculaSoma();
		System.out.println("btCalcular");
		System.out.println("ValorSoma: " + valorTotal);
		System.out.println("calcula(): " + calculaSoma());

		if ( valorTotal != 0 ) {
			System.out.println("btCalcular set new");
			System.out.println("ValorSoma: " + valorTotal);
			lblVrTotal.setText(String.format("%,.2f", valorTotal));
		}else {
			Alerts.showAlert("Erro ao calcular o valor total", "Verifique os campos de valor", "Faixa de Cobraça, Front End Backup e Archive,\n Apps e Media Size", AlertType.ERROR);
		}
	}
	
	@FXML
	private void btCancelOnAction(ActionEvent event) {
		//System.out.println("Cancel, me. Please.");
		Utils.currentStage(event).close();
	}
	
	private ObservableList<clientes> obsServer;
	private ObservableList<BillTags> obsTags;
	
	private faturaServices fatService;
	private clientesService clServices;
	private clientTypeServices ctServices;
	private OwnerService owServices;
	private BillTagsServices btServices;
	private fatura entity;
	
	private double vrUnitario;
	private double valorTotal;
	
	public void setFatura(fatura entity) {
		this.entity = entity;
	}
	
	public void setAllServices(faturaServices fatService, clientesService clServices, clientTypeServices ctServices, OwnerService owServices, BillTagsServices btServices) {
		
		this.fatService = fatService;
		this.clServices = clServices;
		this.ctServices = ctServices;
		this.owServices = owServices;
		this.btServices = btServices;
	}
	

	@FXML
	private void cbTagsOnAction() {
		vrUnitario = (double) cbTags.getValue().getBillPriceTB();
		txtVRFaixa.setText(String.valueOf(vrUnitario));
		valorTotal = calculaSoma();
		lblVrTotal.setText(String.format("%,.2f", valorTotal));
		System.out.println("combobox");
		System.out.println("ValorSoma: " + valorTotal);
		System.out.println("calcula(): " + calculaSoma());
	}
	
	@FXML
	private void cbServerOnAction() {
		System.out.println("Server UUID: " + cbServer.getValue().getUuidClient());
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		initializeComboBoxServidores();
		initializeComboBoxBillTags();
		Constraints.setTextFieldMaxLength(txtAgente, 200);
		Constraints.setTextFieldMaxLength(txtInstancia, 200);
		Constraints.setTextFieldMaxLength(txtBackupSet, 200);
		Constraints.setTextFieldMaxLength(txtSubCliente, 200);
		Constraints.setTextFieldMaxLength(txtStoragePolicy, 200);
		Constraints.setTextFieldMaxLength(txtCopia, 200);
		Constraints.setTextFieldDouble(txtVRFaixa);
		Constraints.setTextFieldDouble(txtFEB);
		Constraints.setTextFieldDouble(txtFEA);
		Constraints.setTextFieldDouble(txtPriApp);
		Constraints.setTextFieldDouble(txtProtApp);
		Constraints.setTextFieldDouble(txtMedia);
		
		
		
	}

	private void initializeComboBoxServidores() {
		Callback<ListView<clientes>, ListCell<clientes>> factory = lv -> new ListCell<clientes>() {
			@Override
			protected void updateItem(clientes item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getClientName());
			}
		};
		cbServer.setCellFactory(factory);
		cbServer.setButtonCell(factory.call(null));
		
	}

	private void initializeComboBoxBillTags() {
		Callback<ListView<BillTags>, ListCell<BillTags>> factory = lv -> new ListCell<BillTags>() {
			@Override
			protected void updateItem(BillTags item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getBilltagName());
			}
		};
		cbTags.setCellFactory(factory);
		cbTags.setButtonCell(factory.call(null));
		
	}
	
	public void loadAssociatedObjects() {
		// Carrega as tags de cobrança
		if (btServices == null) {
			throw new IllegalStateException("btServices was null");
		}
		List<BillTags> listaTags = btServices.findAll();
		obsTags = FXCollections.observableArrayList(listaTags);
		cbTags.setItems(obsTags);
		// carrega a lista de servidores
		if (clServices == null) {
			throw new IllegalStateException("clServices was null");
		}
		List<clientes> listaServer = clServices.findAll();
		obsServer = FXCollections.observableArrayList(listaServer);
		cbServer.setItems(obsServer);
		
	}
	
	public void updateFormdata() throws ParseException {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (entity.getIdInputBill() == null) {
			txtSeq.setText("");
		}else {
			txtSeq.setText(String.valueOf(entity.getIdInputBill()));
		}
		if (entity.getIb_ano_mes() != null) {
			txtRef.setText(Utils.convertDataBR(entity.getIb_ano_mes()));
		}
		if (entity.getTags() == null) {
			cbTags.getSelectionModel().selectFirst();
			txtVRFaixa.setText("0.00");
		}else {
			cbTags.setValue(entity.getTags());
			txtVRFaixa.setText(String.format("%.2f", entity.getTags().getBillPriceTB()));
		}
		if (entity.getServer() == null){
			cbServer.getSelectionModel().selectFirst();
		} else {
			cbServer.setValue(entity.getServer());
		}
		
		txtAgente.setText(entity.getCv_agent());
		txtInstancia.setText(entity.getCv_instance());
		txtBackupSet.setText(entity.getCv_backupset());
		txtSubCliente.setText(entity.getCv_subclient());
		txtStoragePolicy.setText(entity.getCv_storagepolicy());
		txtCopia.setText(entity.getCv_copyname());
		txtFEB.setText(String.format("%.6f", entity.getCv_febackupsize()));
		txtFEA.setText(String.format("%.6f", entity.getCv_fearchivesize()));
		txtPriApp.setText(String.format("%.6f", entity.getCv_primaryappsize()));
		txtProtApp.setText(String.format("%.6f", entity.getCv_protectedappsize()));
		txtMedia.setText(String.format("%.6f", entity.getCv_mediasize()));
		valorTotal = entity.getIb_taxcalculated();
		lblVrTotal.setText(String.format("%,.2f", entity.getIb_taxcalculated()));
		
	}
	
	private fatura getFormData() throws ParseException {
		fatura obj = new fatura();
		obj.setIdInputBill(Utils.tryParseToInt(txtSeq.getText()));
		obj.setIb_ano_mes(Date.valueOf(Utils.convertData(txtRef.getText())));
		obj.setTags(cbTags.getValue());
		obj.setServer(cbServer.getValue());
		obj.setCv_agent(txtAgente.getText());
		obj.setCv_instance(txtInstancia.getText());
		obj.setCv_backupset(txtBackupSet.getText());
		obj.setCv_subclient(txtSubCliente.getText());
		obj.setCv_storagepolicy(txtStoragePolicy.getText());
		obj.setCv_copyname(txtCopia.getText());
		obj.setCv_febackupsize(Utils.tryParseToDouble(txtFEB.getText()));
		obj.setCv_fearchivesize(Utils.tryParseToDouble(txtFEA.getText()));
		obj.setCv_primaryappsize(Utils.tryParseToDouble(txtPriApp.getText()));
		obj.setCv_protectedappsize(Utils.tryParseToDouble(txtProtApp.getText()));
		obj.setCv_mediasize(Utils.tryParseToDouble(txtMedia.getText()));
		System.out.println("get text do lbl toral: " + lblVrTotal.getText());
		obj.setIb_taxcalculated(valorTotal);
		return obj;
	}
	
	private double calculaSoma() {
		double tag;
		double feb;
		double fea;
		double pap;
		double pro;
		double msz;
		double tot;
		
		if ( txtVRFaixa.getText() == null || txtVRFaixa.getText().trim().equals("") ) {
			tag = 0f;
			txtVRFaixa.setText("0.00");
		} else {
			tag = Utils.tryParseToDouble(txtVRFaixa.getText());
		}

		if ( txtFEB.getText() == null || txtFEB.getText().trim().equals("") ) {
			feb = 0f;
			txtFEB.setText("0.00");
		} else {
			feb = Utils.tryParseToDouble(txtFEB.getText());
		} 
		if ( txtFEA.getText() == null || txtFEA.getText().trim().equals("") ) {
			fea = 0f;
			txtFEA.setText("0.00");
		} else {
			fea = Utils.tryParseToDouble(txtFEA.getText());
		}
		if ( txtPriApp.getText() == null || txtPriApp.getText().trim().equals("") ) {
			pap = 0f;
			txtPriApp.setText("0.00");
		} else {
			pap = Utils.tryParseToDouble(txtPriApp.getText());
		}
		if ( txtProtApp.getText() == null || txtProtApp.getText().trim().equals("") ) {
			pro = 0f;
			txtProtApp.setText("0.00");
		} else {
			pro = Utils.tryParseToDouble(txtProtApp.getText());
		}
		if ( txtMedia.getText() == null || txtMedia.getText().trim().equals("") ) {
			msz = 0f;
			txtMedia.setText("0.00");
		} else {
			msz = Utils.tryParseToDouble(txtMedia.getText());
		}
		tot = (feb+fea+pap+pro+msz)*tag;
		return tot;
	}


	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

}
