package gui;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.entities.fatura;
import model.services.BillTagsServices;
import model.services.OwnerService;
import model.services.clientTypeServices;
import model.services.clientesService;
import model.services.faturaServices;

public class FaturaListController implements Initializable, DataChangeListener {

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
	
	
	private faturaServices fatService;

	private clientesService clServices;
	private clientTypeServices ctServices;
	private OwnerService owServices;
	private BillTagsServices btServices;
	
	private int retornoProcura;
	private String retornoDataInicial;
	private String retornoDataFinal;
	
	private ObservableList<fatura> obsFatura;
	
	public void setServices(faturaServices service) {
		this.fatService = service;
	}
	
	public void setAllServices(faturaServices service, clientesService clServices, clientTypeServices ctServices, OwnerService owServices, BillTagsServices btServices) {
		
		this.fatService = service;
		this.clServices = clServices;
		this.ctServices = ctServices;
		this.owServices = owServices;
		this.btServices = btServices;
	}
	
	
	@FXML
	private void btNovoOnAction(ActionEvent event) throws ParseException {
		fatura fat = new fatura();
		Stage parentStage = Utils.currentStage(event);
		createDialogForm(fat, "/gui/FaturaForm.fxml", parentStage);
		
	}
	
	@FXML
	private void btPesquisarOnAction(ActionEvent event) throws ParseException {
		fatService = new faturaServices();
		if (rbPesquisarData.isSelected() ) {
			String datapesquisa = null;
			if (txtDtInicial.getText() == null || txtDtInicial.getText().trim().contentEquals("")) {
				Alerts.showAlert("ERRO", "Prencha com uma data.", "RB pesquisar por data", AlertType.ERROR);
				txtDtInicial.requestFocus();
			}
			else {
				datapesquisa = Utils.convertData(txtDtInicial.getText());
				
				retornoDataInicial = datapesquisa;
				retornoProcura = 0;
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
				
				retornoProcura =1;
				retornoDataInicial = dataInicial;
				retornoDataFinal = dataFinal;
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
		if (fatService == null) {
			throw new IllegalStateException("ibServices was nulll");
		}
		List<fatura> list = new ArrayList<>();
		if (SearchMethod == 0) {
			list = fatService.findByDate(DataIncial);
		} else if (SearchMethod == 1) {
			list = fatService.findByPeriod(DataIncial, DataFinal);
		} else {
			throw new IllegalStateException("Parametro de seleção inválido.");
		}

		obsFatura = FXCollections.observableArrayList(list);
		tableViewFatura.setItems(obsFatura);
		initEditButtons();
		initRemoveButtons();
		clearFilters();
		 
	}

	private void clearFilters() {
		txtDtInicial.setText("");
		txtDtFinal.setText("");
		
	}
	
	private void createDialogForm(fatura obj, String absoluteName, Stage parentStage) throws ParseException {
		try {
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			FaturaFormController controller = loader.getController();
			controller.setFatura(obj);;
			controller.setAllServices(new faturaServices(), new clientesService(), new clientTypeServices(), new OwnerService(), new BillTagsServices());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormdata();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Alteração de dados do movimento:");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}
		catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<fatura, fatura>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(fatura obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						                          
						event -> {
							try {
								createDialogForm(obj, "/gui/FaturaForm.fxml", Utils.currentStage(event));
								
							} catch (ParseException e) {
								e.printStackTrace();
							}
						});
						
			}
		});
	}

	

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<fatura, fatura>() {
			private final Button button = new Button("Apagar");

			@Override
			protected void updateItem(fatura obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
			
		});
	} 
	
	private void removeEntity(fatura obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (fatService == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				fatService.remove(obj);
				if (retornoProcura == 0 ) {
					updateTableView(0, retornoDataInicial, null);
					//System.out.println("updatetb 0"+ retornoDataInicial);
				} else if (retornoProcura == 1 ) {
					//System.out.println("updatetb 1"+ retornoDataInicial +" " +retornoDataFinal );
					updateTableView(1, retornoDataInicial, retornoDataFinal);
				}
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	
	@Override
	public void onDataChanged() {
		if (retornoProcura == 0 ) {
			updateTableView(0, retornoDataInicial, null);
			//System.out.println("updatetb 0"+ retornoDataInicial);
		} else if (retornoProcura == 1 ) {
			//System.out.println("updatetb 1"+ retornoDataInicial +" " +retornoDataFinal );
			updateTableView(1, retornoDataInicial, retornoDataFinal);
		}
			
		
		
	}

}
