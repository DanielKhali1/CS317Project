package UI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.File;


public class Settings extends Application 
{
	
	Scene setScene;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		File f = new File("style.css");
        BorderPane setPane = new BorderPane();
        setScene = new Scene(setPane, 1000, 700);
    	setScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
        setPane.setId("pane");

		primaryStage.show();
	}
	
    public static void main(String[] args) {
		launch(args);
	}

}
