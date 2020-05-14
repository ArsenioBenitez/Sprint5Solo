package views;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.BusinessPlan;
import models.CNTRAssessment;
import models.ConfirmationInterface;
import models.MyRemoteClient;
import models.MyRemoteImpl;
import models.Section;
import models.VMOSA;

@ExtendWith(ApplicationExtension.class)
public class TestCompareBPs {
	
	int createCalled = 0;
	int viewCalled = 0;
	MyRemoteClient client;
	MyRemoteImpl server;
	  
	BusinessPlan plan;
	BusinessPlan plan2;
	
	@Start
	private void Start(Stage stage)
	{
		plan = new CNTRAssessment();
		Section current = plan.root;
		current.setContent("root");
		plan.addSection(current);
		current.getChildren().get(1).setContent("goal2");;
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
		
		plan2 = new CNTRAssessment();
		Section current2 = plan2.root;
		current2.setContent("root");
		plan2.addSection(current2);
		current2.getChildren().get(1).setContent("goal2");;
		current2 = current2.getChildren().get(0);
		current2.setContent("goal");
		current2.addChild(new Section("Program Goals and Student Learning Objective"));
		current2.getChildren().get(0).setContent("objective1");
		current2.getChildren().get(1).setContent("objective2");
		plan2.setDepartment("CSC");
		plan2.setYear("2021");
		plan2.isEditable = false;
		plan2.setEdit("No");
		server.getStoredBP().add(plan2);	
		client = new MyRemoteClient(server);
		server.addPerson("X", "1", "CSC", true);
 
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(TestSelectorView.class.getResource("../views/CompareBPsView.fxml"));
	
		BorderPane pane;
		try {
			pane = loader.load();
			CompareViewController cont = loader.getController();
			cont.setModel(plan,plan2);	
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
	        robot.lookup("#treeView1").queryAs(TreeView.class);
	    TreeItem<Section> root = tv.getRoot();
	    root.setExpanded(true);
	    for(Object obj:root.getChildren().toArray())
	    {
	      @SuppressWarnings("unchecked")
	      TreeItem<Section> child = (TreeItem<Section>)obj;
	      child.setExpanded(true);
	    }
	    @SuppressWarnings("unchecked")
		TreeView<Section> tv2 = (TreeView<Section>)
		        robot.lookup("#treeView2").queryAs(TreeView.class);
		    TreeItem<Section> root2 = tv2.getRoot();
		    root2.setExpanded(true);
		    for(Object obj:root2.getChildren().toArray())
		    {
		      @SuppressWarnings("unchecked")
		      TreeItem<Section> child = (TreeItem<Section>)obj;
		      child.setExpanded(true);
		    }
	    
	    
	    
	    
	  }
	@Test
	void testButtons(FxRobot robot)
	{
		rest(1);
		expandTree(robot);
		rest(2);
	}
}
