package views;
import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainBPView;
import models.BusinessPlan;
import models.MyRemoteClient;
import models.Section;

public class commentBoxController {
	
	Section section;
	Stage stage;
	MyRemoteClient client;
	BusinessPlan bp;

    @FXML
    private TextArea textArea;
    @FXML
    private Button addComment;

    @FXML
    void AddComment(ActionEvent event) {
    	section.addComment(textArea.textProperty());
    	System.out.println("commentAdded");
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainBPView.class.getResource("../views/BPView.fxml"));
		BorderPane pane;
		try {
			pane = loader.load();
			BPViewController cont = loader.getController();
    		//cont.setModel(client.getCurrentBP());
    		cont.setModel(bp,client);
    		cont.setPane(pane);
    		cont.setStage(stage);
    		Scene sc = new Scene(pane);
    		stage.setScene(sc);
    		stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}

    }
    public void setModel(Section section,MyRemoteClient client,BusinessPlan bp)
    {
    	this.section = section;
    	this.client=client;
    	this.bp = bp;
    }
	public void setStage(Stage stage) {
		// TODO Auto-generated method stub
		this.stage=stage;
	}
	
}
