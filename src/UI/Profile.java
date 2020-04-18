package UI;
import UI.GameGui;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;

import SQL.SQLCalls;


public class Profile extends Application 
{
	
	Scene profileScene;
	String username,pass;
	Pane profilePane = new Pane();
	
	public Profile()
	{
		username = "hi";
		pass = "hi";
	}
	
	public Profile(String username, String pass)
	{
		this.username = username;
		this.pass = pass;
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		SQLCalls s = new SQLCalls("mysql.us.cloudlogin.co", "3306", "dkhalil_cs317", "dkhalil_cs317", "6d9d6FHkfI");
        profileScene = new Scene(profilePane, 1000, 700);
        profilePane.setId("pane");
        profileScene.getStylesheets().add(new File("style.css").toURI().toURL().toExternalForm());
        Image profpic = new Image("P1.png");
        ImageView profilePicture= new ImageView(profpic);
        profilePicture.setFitHeight(150);
        profilePicture.setFitWidth(150);
        profilePicture.relocate(50, 50);
        
        Text usernameTitle = new Text("Account");
        usernameTitle.relocate(400, 80);
        usernameTitle.setStyle("-fx-font-size: 60;");
        usernameTitle.setFill(Color.WHITE);
        profilePane.getChildren().add(usernameTitle);
        Pane rect = new Pane();
        rect.setPrefSize(250, 250);
        rect.relocate(100, 150);
        rect.setStyle("-fx-background-color: #c4f6ff;-fx-border: solid; -fx-border-color: white; -fx-border-radius: 8;");
        rect.getChildren().add(profilePicture);
        
        Pane MatchStats = new Pane();
        MatchStats.setStyle("-fx-padding: 20; -fx-background-color: #2d5775; "
        		+ "-fx-border: solid; -fx-border-color: white; -fx-border-radius: 8; ");
        MatchStats.setPrefSize(400, 250);
        MatchStats.relocate(400, 150);
       
        Text kills = new Text("Kills: " + s.getTotalKills(this.username));
        Text deaths = new Text("Deaths: " + s.getTotalDeaths(this.username));
        Text Games = new Text("Games: " + (s.getTotalLosses(this.username) + s.getTotalWins(this.username)));
        Text Wins = new Text("Wins: " + s.getTotalWins(this.username));
        Text Losses = new Text("Losses: " + s.getTotalLosses(this.username));
        
        
        kills.setStyle("-fx-font-size: 20");
        deaths.setStyle("-fx-font-size: 20");
        Games.setStyle("-fx-font-size: 20");
        Wins.setStyle("-fx-font-size: 20");
        Losses.setStyle("-fx-font-size: 20");
        kills.setFill(Color.WHITE);
        deaths.setFill(Color.WHITE);
        Games.setFill(Color.WHITE);
        Wins.setFill(Color.WHITE);
        Losses.setFill(Color.WHITE);
        
        
        VBox vb = new VBox();
        vb.getChildren().addAll(kills,deaths,Games,Wins,Losses);
        vb.setSpacing(20);
        vb.relocate(20, 15);
        
        MatchStats.getChildren().add(vb);
        
        
        Pane TimeStats = new Pane();
        TimeStats.setStyle("-fx-padding: 20; -fx-background-color: #2d5775; "
        		+ "-fx-border: solid; -fx-border-color: white; -fx-border-radius: 8; ");
        TimeStats.setPrefSize(400, 150);
        TimeStats.relocate(100, 500);
       
        Text avgLifespan = new Text("Average Life Span: " + (s.getAvgLifespan(this.username) ));
        Text totalPlayTime = new Text("Total Play Time: " + s.getTotalTimePlayed(this.username));
        Text avgMatchLength = new Text("Average Match Length: " + s.getAvgMatchLength(this.username));
        
        
        avgLifespan.setStyle("-fx-font-size: 20");
        totalPlayTime.setStyle("-fx-font-size: 20");
        avgMatchLength.setStyle("-fx-font-size: 20");
        avgLifespan.setFill(Color.WHITE);
        totalPlayTime.setFill(Color.WHITE);
        avgMatchLength.setFill(Color.WHITE);
        
        
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(avgLifespan,totalPlayTime,avgMatchLength);
        vb1.setSpacing(20);
        vb1.relocate(20, 15);
        
        TimeStats.getChildren().add(vb1);
        
        
        Text usernametext = new Text("User: " + this.username);
        usernametext.relocate(100, 160+250);
        usernametext.setFill(Color.WHITE);
        usernametext.setStyle("-fx-font-size: 20");
        Text displaytext = new Text("Display: " +s.getDisplayName( this.username));
        displaytext.relocate(100, 190+250);
        displaytext.setFill(Color.WHITE);
        displaytext.setStyle("-fx-font-size: 20");
        
        
        Pane DisplayNameChange = new Pane();
        DisplayNameChange .setStyle("-fx-padding: 20; -fx-background-color: #2d5775; "
        		+ "-fx-border: solid; -fx-border-color: white; -fx-border-radius: 8; ");
        DisplayNameChange .setPrefSize(275, 200);
        DisplayNameChange .relocate(525, 450);
        
        VBox vb2 = new VBox();
        vb2.relocate(20, 20);
        Text displaynameTitle = new Text("Change Display Name");
        displaynameTitle.setStyle("-fx-font-size: 20");
        displaynameTitle.setFill(Color.WHITE);
        TextField tf = new TextField("Display Name");
        tf.setStyle("-fx-background-color: #2d5775; -fx-border-color: white; -fx-text-fill: white");
        Button apply = new Button("Apply");
        apply.setStyle("-fx-background-color: #00798f; -fx-text-fill: white; -fx-border-color: white;"
        		+ "-fx-border-radius: 4");
        
        
        apply.setOnMouseEntered(e->{
        	apply.setStyle("-fx-background-color: white; -fx-text-fill: #00798f; -fx-border-color: white;"
            		+ "-fx-border-radius: 4");
        });
        apply.setOnMouseExited(e->{
        	apply.setStyle("-fx-background-color: #00798f; -fx-text-fill: white; -fx-border-color: white;"
            		+ "-fx-border-radius: 4");
        });
        
        apply.setOnAction(e->{
        	s.setDisplayName(this.username, tf.getText());
        	displaytext.setText("Display: " + tf.getText());
        });
        
        vb2.setSpacing(30);
        vb2.getChildren().addAll(displaynameTitle, tf, apply);
        
        
        
        DisplayNameChange.getChildren().add(vb2);
        
        profilePane.getChildren().addAll(rect, MatchStats, usernametext, displaytext, TimeStats,DisplayNameChange);
        
        Button back = new Button();
        back.getStyleClass().add("backarrow");
        back.setAlignment(Pos.TOP_LEFT);
        profilePane.getChildren().add(back);
        
        back.setOnAction(e -> {
        	new GameGui(username, pass).start(new Stage());
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
