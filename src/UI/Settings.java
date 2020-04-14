package UI;
import UI.GameGui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
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
        
        Button back = new Button();
        back.getStyleClass().add("backarrow");
        back.setAlignment(Pos.TOP_LEFT);
        setPane.getChildren().add(back);
        
        back.setOnAction(e -> {
        	new GameGui().start(new Stage());
        	primaryStage.close();
        });
        
        primaryStage.setTitle("Settings");
        primaryStage.setScene(setScene);
		primaryStage.show();
	}
	
    public static void main(String[] args) {
		launch(args);
	}

}
