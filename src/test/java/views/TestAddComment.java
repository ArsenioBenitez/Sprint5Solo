package views;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainBPView;
import models.BusinessPlan;
import models.CNTRAssessment;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;

@ExtendWith(ApplicationExtension.class)
public class TestAddComment {
	
	
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
		client = new MyRemoteClient(server);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(BPViewController.class.getResource("../views/addCommentBox.fxml"));
		BorderPane pane;
		Section selected = plan.root.getChildren().get(1);
		try {
			pane = loader.load();
			commentBoxController cont = loader.getController();
			//cont.setModel(client.getCurrentBP());
			cont.setModel(selected,client,plan);
			Scene sc = new Scene(pane);
			cont.setStage(stage);
			stage.setScene(sc);
			stage.show();
		} catch (IOException e) 
		{
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
	
	@Test
	void testButtons(FxRobot robot)
	{
		rest(2);
		robot.write("comment1");
		rest(2);
		robot.clickOn("#addComment");
		rest(5);
		

	}
	

	}
