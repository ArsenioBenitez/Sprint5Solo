package views;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainBPView;
import models.BusinessPlan;

import models.MyRemoteClient;
import models.Section;

public class CompareController {
	
		BusinessPlan selectedPlan;
		MyRemoteClient client;
		BorderPane pane;
		Stage  stage;
		
	
		@FXML 
		private TableView<BusinessPlan> tableView;
	    @FXML
	    private TableColumn<BusinessPlan, String> year;

	    @FXML
	    private TableColumn<BusinessPlan, String> department;

	    @FXML
	    private TableColumn<BusinessPlan, String> editability;

	    @FXML
	    private TableColumn<BusinessPlan, String> type;

	    @FXML
	    void onClickCompare(ActionEvent event) {
	    	System.out.println("Compare");
	    	BusinessPlan otherPlan = tableView.getSelectionModel().getSelectedItem();
	    	List<ArrayList<Section>> diff = client.comparePlans(selectedPlan,otherPlan);
	    	System.out.println(diff);
	    	FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainBPView.class.getResource("../views/CompareBPsView.fxml"));
    		BorderPane pane;
			try {
				pane = loader.load();
				CompareViewController cont = loader.getController();
	    		//cont.setModel(client.getCurrentBP());
	    		cont.setModel(selectedPlan,otherPlan);
	    		cont.setPane(pane);
	    		cont.setStage(stage);
	    		Scene sc = new Scene(pane);
	    		stage.setScene(sc);
	    		stage.show();
			} catch (IOException e) {

				e.printStackTrace();
			}
	    }

		public void setModel(BusinessPlan current, MyRemoteClient client) {
			
			selectedPlan = current;
			this.client = client;
			createTable();
		}
		
		public void setStage(Stage newStage)
		{	//pass stage between different views
			stage = newStage;
		}
		public void setPane(BorderPane pane)
		{
			this.pane = pane;
			
		}
		public void createTable()
		{
			  year.setCellValueFactory(new PropertyValueFactory<BusinessPlan, String>("year"));
			  department.setCellValueFactory(new PropertyValueFactory<BusinessPlan, String>("department"));
			  editability.setCellValueFactory(new PropertyValueFactory<BusinessPlan, String>("edit"));
			  type.setCellValueFactory(new PropertyValueFactory<BusinessPlan, String>("type"));
			  ArrayList<BusinessPlan> plans = client.getServer().getStoredBP();
			  ObservableList<BusinessPlan> newPlans = FXCollections.observableArrayList();
			  for(int i = 0; i<plans.size(); i++)
			  {
				  newPlans.add(plans.get(i));
			  }
			  tableView.setItems(newPlans);
		 }
		

}



