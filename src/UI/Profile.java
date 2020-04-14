package UI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;


public class Profile extends Application 
{
	
	Scene profileScene;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		File f = new File("style.css");
        BorderPane profilePane = new BorderPane();
        profileScene = new Scene(profilePane, 1000, 700);
        profilePane.setId("pane");
    	profileScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
        

		primaryStage.show();
	}
	
    public static void main(String[] args) {
		launch(args);
	}

}
