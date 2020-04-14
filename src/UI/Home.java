package UI;
import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Home extends Application{
	Scene homeScene;
	@Override
	public void start(Stage arg0) throws Exception {
		File f = new File("style.css");
        BorderPane homePane = new BorderPane();
        homeScene = new Scene(homePane, 1000, 700);
        homePane.setId("pane");
    	homeScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
		
	}
	
    public static void main(String[] args) {
		launch(args);
	}


}
