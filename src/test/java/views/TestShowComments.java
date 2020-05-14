package views;
import java.io.IOException;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainBPView;
import models.BusinessPlan;
import models.CNTRAssessment;
import models.ConfirmationInterface;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;
import models.VMOSA;

@ExtendWith(ApplicationExtension.class)
public class TestShowComments {

	
	MyRemoteClient client;
	MyRemoteImpl server;
	  
	BusinessPlan plan;
	//set up
	@Start
	private void Start(Stage stage)
	{
		plan = new CNTRAssessment();
		Section current = plan.root;
		current.setContent("root");
		plan.addSection(current);
		current.getChildren().get(1).setContent("goal2");
		current = current.getChildren().get(0);
		current.setContent("goal");
		current.addChild(new Section("Program Goals and Student Learning Objective"));
		current.getChildren().get(0).setContent("objective1");
		current.getChildren().get(1).setContent("objective2");
		plan.setDepartment("CSC");
		plan.setYear("2020");
		//Registry registry = LocateRegistry.createRegistry(1099);
		server = new MyRemoteImpl();
		server.getStoredBP().add(plan);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			BPViewController cont = loader.getController();
    		//cont.setModel(client.getCurrentBP());
    		cont.setModel(plan,client);
    		cont.setPane(pane);
    		cont.setStage(stage);
    		Scene sc = new Scene(pane);
    		stage.setScene(sc);
    		stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void rest(int sec)
	  {
	    try {
	      Thread.sleep(sec*1000);
	    } catch (InterruptedException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }

	  }
	public void expandTree(FxRobot robot)
	  {
	    @SuppressWarnings("unchecked")
	    TreeView<Section> tv = (TreeView<Section>)
	        robot.lookup("#treeView").queryAs(TreeView.class);
	    TreeItem<Section> root = tv.getRoot();
	    root.setExpanded(true);
	    for(Object obj:root.getChildren().toArray())
	    {
	      @SuppressWarnings("unchecked")
	      TreeItem<Section> child = (TreeItem<Section>)obj;
	      child.setExpanded(true);
	    }
	    
	  }
	private void selectItem(FxRobot robot, int i)
	  {
	    @SuppressWarnings("unchecked")
	    TreeView<Section> tv = (TreeView<Section>)
	        robot.lookup("#treeView").queryAs(TreeView.class);
	    
	    tv.getSelectionModel().clearAndSelect(i);
	    robot.clickOn("#showCommentButton");
	    
	  }
	@Test
	void testButtons(FxRobot robot)
	{
		rest(2);
		expandTree(robot);
		rest(2);
		selectItem(robot,2);
		rest(2);
		robot.clickOn("#addCommentButton");
		rest(2);
		robot.write("comment1");
		
		
	}
	@Test
	void testComment(FxRobot robot)
	{
		rest(2);
		expandTree(robot);
		rest(2);
		StringProperty mystring= new SimpleStringProperty();
		mystring.setValue("comment1");
		plan.root.addComment(mystring);
		selectItem(robot,0);
		rest(2);
		
	}
	
	

	

}
