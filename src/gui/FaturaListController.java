package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.script.Bindings;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.clientes;
import model.entities.fatura;
import model.services.faturaServices;

public class FaturaListController implements Initializable {

	@FXML
	private Button btNovo;
	
	@FXML
	private Button btProcurar;
	
	@FXML
	private RadioButton rbPesquisarData;
	
	@FXML
	private RadioButton rbPesquisarPeriodo;
	
	@FXML
	private TextField txtDtInicial;
	
	@FXML
	private TextField txtDtFinal;
	
	@FXML
	private TableView<fatura> tableViewFatura;
	
	@FXML
	private TableColumn<fatura, Integer> tableColumnSeq;
	
	@FXML
	private TableColumn<fatura, Date> tableColumnRef;
	
	@FXML
	private TableColumn<fatura, String> tableColumnCliente;
	
	@FXML
	private TableColumn<fatura, String> tableColumnTag;
	
	@FXML
	private TableColumn<fatura, String> tableColumnInstancia;
	
	@FXML
	private TableColumn<fatura, String> tableColumnSubClient;
	
	@FXML
	private TableColumn<fatura, Double> tableColumnTotal;
	
	@FXML
	private TableColumn<fatura, String> tableColumnAR;
	
	@FXML
	private TableColumn<fatura, fatura> tableColumnEDIT;

	@FXML
	private TableColumn<fatura, fatura> tableColumnREMOVE;
	
	
	private faturaServices service;
	private ObservableList<fatura> obsFatura;
	
	public void setServices(faturaServices service) {
		this.service = service;
	}
	
	@FXML
	private void btNovoOnAction() {
		System.out.println("novo");
		
	}
	
	@FXML
	private void btPesquisarOnAction(ActionEvent event) throws ParseException {
		service = new faturaServices();
		System.out.println("Status rbPesquisarData    : " + rbPesquisarData.isArmed() + " "+ rbPesquisarData.isSelected());
		System.out.println("Status rbPesquisarPeriodo : " + rbPesquisarPeriodo.isArmed() + " "+ rbPesquisarPeriodo.isSelected());
		
		if (rbPesquisarData.isSelected() ) {
			String datapesquisa = null;
			if (txtDtInicial.getText() == null || txtDtInicial.getText().trim().contentEquals("")) {
				Alerts.showAlert("ERRO", "Prencha com uma data.", "RB pesquisar por data", AlertType.ERROR);
				txtDtInicial.requestFocus();
			}
			else {
				datapesquisa = Utils.convertData(txtDtInicial.getText());
				System.out.println(datapesquisa);
				updateTableView(0, datapesquisa, null);
			}
		} else {
			String dataInicial = null;
			String dataFinal = null;
			if (txtDtInicial.getText() == null || txtDtInicial.getText().trim().contentEquals("")) {
				Alerts.showAlert("ERRO", "Prencha com uma data.", "RB pesquisar por periodo\n campo inicial em branco", AlertType.ERROR);
				txtDtInicial.requestFocus();
			} else if (txtDtFinal.getText() == null || txtDtFinal.getText().trim().contentEquals("")) {
				Alerts.showAlert("ERRO", "Prencha com uma data.", "RB pesquisar por periodo\n campo final em branco", AlertType.ERROR);
				txtDtFinal.requestFocus();
			} else {
				dataInicial = Utils.convertData(txtDtInicial.getText()); 
				dataFinal = Utils.convertData(txtDtFinal.getText());
				System.out.println(dataInicial + " a " + dataFinal);
				updateTableView(1, dataInicial, dataFinal);
				
			}

		}
		
	}
	
	@FXML
	private void rbPesquisarDataOnAction(ActionEvent event) {
		txtDtFinal.setDisable(!rbPesquisarPeriodo.isArmed());
		txtDtInicial.requestFocus();
	}
	
	@FXML
	private void rbPesquisarPeriodoOnAction(ActionEvent event) {
		txtDtFinal.setDisable(!rbPesquisarPeriodo.isArmed());
		txtDtInicial.requestFocus();
		
		
		
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		txtDtFinal.setDisable(!rbPesquisarPeriodo.isArmed());
		txtDtInicial.requestFocus();
		initializeNodes();
	}

	private void initializeNodes() {
		 // Colocando os dados na tabela
		tableColumnSeq.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<fatura,Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<fatura, Integer> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getIdInputBill());
			}
		});
		tableColumnRef.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<fatura,Date>, ObservableValue<Date>>() {
			@Override
			public ObservableValue<Date> call(CellDataFeatures<fatura, Date> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getIb_ano_mes());
			}
		});
		Utils.formatTableColumnDate(tableColumnRef, "YYYY-MM-dd");
		tableColumnCliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<fatura,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<fatura, String> arg0) {
				return new ReadOnlyObjectWrapper(arg0.getValue().getServer().getClientName());
			}
		});
		tableColumnTag.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<fatura,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<fatura, String> a) {
				return new ReadOnlyObjectWrapper(a.getValue().getTags().getBilltagName());
			}
		});
		
		tableColumnInstancia.setCellValueFactory(new PropertyValueFactory<>("cv_agent"));
		tableColumnSubClient.setCellValueFactory(new PropertyValueFactory<>("cv_subclient"));
		tableColumnTotal.setCellValueFactory(new PropertyValueFactory<>("ib_taxcalculated"));
		Utils.formatTableColumnDouble(tableColumnTotal, 2);
		
		tableColumnAR.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<fatura,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<fatura, String> a) {
				return new ReadOnlyObjectWrapper(a.getValue().getDono().getOwAR()) ;
			}
		});
		
		//Metodo para ajustar o tableview
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewFatura.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView(Integer SearchMethod, String DataIncial, String DataFinal) {
		/* Search Method
		 * 0 - to one date
		 * 1 - to period 
		 */
		if (service == null) {
			throw new IllegalStateException("ibServices was nulll");
		}
		List<fatura> list = new ArrayList<>();
		if (SearchMethod == 0) {
			list = service.findByDate(DataIncial);
		} else if (SearchMethod == 1) {
			list = service.findByPeriod(DataIncial, DataFinal);
		} else {
			throw new IllegalStateException("Parametro de seleção inválido.");
		}

		obsFatura = FXCollections.observableArrayList(list);
		tableViewFatura.setItems(obsFatura);
		
		clearFilters();
		 
	}

	private void clearFilters() {
		txtDtInicial.setText("");
		txtDtFinal.setText("");
		
	}

}
