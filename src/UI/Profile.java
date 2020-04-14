package UI;
import UI.GameGui;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        
        Button back = new Button();
        back.getStyleClass().add("backarrow");
        back.setAlignment(Pos.TOP_LEFT);
        profilePane.getChildren().add(back);
        
        back.setOnAction(e -> {
        	new GameGui().start(new Stage());
        	primaryStage.close();
        });
        
    	
    	primaryStage.setTitle("Account");
    	primaryStage.setScene(profileScene);
		primaryStage.show();
	}
	
    public static void main(String[] args) {
		launch(args);
	}

}
