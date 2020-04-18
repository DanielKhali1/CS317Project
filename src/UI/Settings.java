package UI;
import UI.GameGui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;
import java.io.File;
import java.util.Optional;

import SQL.SQLCalls;


public class Settings extends Application 
{
	
	Scene setScene;
	String username,pass;
    Pane pane = new Pane();

	
	public Settings()
	{
		username = "Test";
		pass = "Test";
	}
	
	public Settings(String username, String pass)
	{
		this.username = username;
		this.pass = pass;
	}
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		SQLCalls s = new SQLCalls("mysql.us.cloudlogin.co", "3306", "dkhalil_cs317", "dkhalil_cs317", "6d9d6FHkfI");

        setScene = new Scene(pane, 1000, 700);
		setScene.getStylesheets().add(new File("style.css").toURI().toURL().toExternalForm());
        pane.setId("pane");
        
        Button back = new Button();
        back.getStyleClass().add("backarrow");
        back.setAlignment(Pos.TOP_LEFT);
        pane.getChildren().add(back);
        
        back.setOnAction(e -> {
        	new GameGui(username, pass).start(new Stage());
        	primaryStage.close();
        });
        

        Text reset = new Text("Reset Stats");
        reset.setFill(Color.WHITE);
        reset.setStyle("-fx-font-size: 35");
        Text sound = new Text("Sound Settings");
        sound.setFill(Color.WHITE);
        sound.setStyle("-fx-font-size: 35");
        Text delete= new Text("Delete Account");
        delete.setFill(Color.WHITE);
        delete.setStyle("-fx-font-size: 35");
        
        Button resetbt = new Button("RESET");
        resetbt.setStyle("-fx-background-color: #00798f; -fx-text-fill: white; -fx-border-color: white; -fx-font-size: 30;"
        		+ "-fx-border-radius: 4");
        
        resetbt.setOnMouseEntered(e->{
            resetbt.setStyle("-fx-background-color: white; -fx-text-fill: #00798f; -fx-border-color: white; -fx-font-size: 30;"
            		+ "-fx-border-radius: 4");
        });
        resetbt.setOnMouseExited(e->{
            resetbt.setStyle("-fx-background-color: #00798f; -fx-text-fill: white; -fx-border-color: white; -fx-font-size: 30;"
            		+ "-fx-border-radius: 4");
        });
        
        resetbt.setOnAction(e->{
        	Alert warning = new Alert(AlertType.CONFIRMATION);
        	warning.setTitle("RESET STATS");
        	warning.setHeaderText("Are you sure?");
        	
        	Optional<ButtonType> result = warning.showAndWait();
        	if(result.get() == ButtonType.OK)
        	{
        		System.out.println("I RESET");
        		s.setAvgLifeSpan(this.username, 0);
        		s.setTotalKills(this.username, 0);
        		s.setTotalDeaths(this.username, 0);
        		s.setTotalTimePlayed(this.username, 0);
        		s.setWins(this.username, 0);
        		s.setLosses(this.username, 0);
        		
        	}
        	
        });
        
        Slider slider = new Slider(0, 1, 0.5);
        slider.setBlockIncrement(0.1f);
        
        Pane settingsContainer = new Pane();
        
        Button deletebt = new Button("DELETE\nACCOUNT");
        deletebt.setStyle("-fx-background-color: #00798f; -fx-text-fill: white; -fx-border-color: white; -fx-font-size: 30;"
        		+ "-fx-border-radius: 4");
        
        
        deletebt.setOnMouseEntered(e->{
        	deletebt.setStyle("-fx-background-color: white; -fx-text-fill: #00798f; -fx-border-color: white; -fx-font-size: 30;"
            		+ "-fx-border-radius: 4");
        });
        deletebt.setOnMouseExited(e->{
        	deletebt.setStyle("-fx-background-color: #00798f; -fx-text-fill: white; -fx-border-color: white; -fx-font-size: 30;"
            		+ "-fx-border-radius: 4");
        });
        
        deletebt.setOnAction(e->{
        	Alert warning = new Alert(AlertType.CONFIRMATION);
        	warning.setTitle("DELETE ACCOUNT");
        	warning.setHeaderText("Are you sure?");
        	
        	Optional<ButtonType> result = warning.showAndWait();
        	if(result.get() == ButtonType.OK)
        	{
        		System.out.println("I DELETE");
        		//delete account and kill process
        	}
        	
        });
        HBox first = new HBox();
        first.getChildren().addAll(reset,resetbt);
        HBox second = new HBox();
        second.getChildren().addAll(sound, slider);
        HBox third = new HBox();
        third.getChildren().addAll(delete, deletebt);
        
        VBox container = new VBox();
        container.getChildren().addAll(first,second,third);
        
        first.setSpacing(40);
        second.setSpacing(40);
        third.setSpacing(40);
        container.setSpacing(40);
        
        container.relocate(50, 50);

        settingsContainer.setStyle("-fx-padding: 20; -fx-background-color: #2d5775; "
        		+ "-fx-border: solid; -fx-border-color: white; -fx-border-radius: 8; ");
        settingsContainer.getChildren().add(container);
        settingsContainer.setPrefSize(600, 400);
        
        settingsContainer.relocate(200, 200);
        
        
        
        
		Text title = new Text("Settings");
		title.relocate(400, 80);
		title.setStyle("-fx-font-size: 60;");
		title.setFill(Color.WHITE);
        
		pane.getChildren().addAll(title, settingsContainer);
        
        primaryStage.setTitle("Settings");
        primaryStage.setScene(setScene);
		primaryStage.show();
	}
	
    public static void main(String[] args) {
		launch(args);
	}

}
