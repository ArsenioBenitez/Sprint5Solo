package views;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.BusinessPlan;

import models.Section;

public class CompareViewController {
	
	BusinessPlan model1;
	BusinessPlan model2;
	BorderPane pane;
	Stage stage;

    @FXML
    private TreeView<Section> treeView1;

    @FXML
    private TreeView<Section> treeView2;
    
    
    @SuppressWarnings({ "unchecked" }) 
    public void setModel(BusinessPlan bp1, BusinessPlan bp2)
    {
    	model1 = bp1;
    	model2 = bp2;
    	TreeItem<Section> root = createTreeView(model1.root);
		treeView1.setRoot(root);
		TreeItem<Section> root2 = createTreeView(model2.root);
		treeView2.setRoot(root2);
		//attempt to see what items are different by coloring them different colors
		
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private TreeItem createTreeView(Section current)
    {
    	
    	//System.out.println(current);
    	if(current.children.isEmpty())
    	{	
    		TreeItem temp = new TreeItem(current);
    		return temp;
    	}
    	else
    	{
    		TreeItem temp2 = new TreeItem(current);
    		for(int i = 0; i<current.children.size(); i++)
    		{
    			temp2.getChildren().add(createTreeView(current.getChildren().get(i)));
    		}
    		return temp2;
    	}
    	
    }

	public void setPane(BorderPane pane2) {
		this.pane = pane2;
		
	}

	public void setStage(Stage stage2) {
		stage=stage2;
		
	}

}
