package UI;

import java.io.File;

import Game.GUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* Braeden Wilson
* CS317 Project
* 3/31/2020
* 
* Battle Royale For Kids Free
* GUI creator
* 
*/


public class GameGui extends Application
{ 
	Scene loginScene;
	Scene homeScene;
	String user = "Test";
	String pw = "Test";
	
	    @Override
	    public void start(Stage primaryStage) 
	    {
	    	//set css file
	    	File f = new File("style.css");
	    	//###########################  Instantiate panes/scenes  #############################
	    	//Initial scene
	        StackPane root = new StackPane();
	        root.setId("pane");
	        loginScene = new Scene(root, 1000, 700);
	        //add css to login
	    	loginScene.getStylesheets().clear();
	    	loginScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
	        primaryStage.setScene(loginScene);
	        primaryStage.setTitle("Battle Royale Free for Kids");
	        
	        //home scene
	        Pane homePane = new Pane();
	        homePane.setId("pane");
	        homeScene = new Scene(homePane, 1000, 700);
	        
	        
	        VBox vb = new VBox();
	        homePane.getChildren().add(vb);
	        vb.setSpacing(20);
	        
	        Button host = new Button("Host");
	        Button join = new Button("Join");
	        
	        vb.getChildren().addAll(host, join);
	        
	        
	       host.setOnAction(e->{
	    	   new GUI(true).start(new Stage());
	    	   primaryStage.close();
	       });
	       
	       join.setOnAction(e->{
	    	   new GUI(false).start(new Stage());
	    	   primaryStage.close();
	       });
	        
	        //######################## login layout ########################################
	        
	        //title text
	        Text title = new Text("BATTLE ROYALE FREE FOR KIDS");
	        title.setId("titleText");
	        title.setFill(Color.WHITE);
	        VBox Titlebox = new VBox(100, title);
	        Titlebox.setAlignment(Pos.TOP_CENTER);
	        Titlebox.setPadding(new Insets(50,0,0,0));
	        
	        //login box
	        Rectangle log = new Rectangle(300, 200);
	        log.setFill(Color.rgb(50, 55, 73,1));
	        log.setArcWidth(20);
	        log.setArcHeight(20);
	        
	        //textfields and buttons
	        GridPane login = new GridPane();
	        login.setAlignment(Pos.CENTER);
	        login.setHgap(10);
	        login.setVgap(20);
	        Text uName = new Text("Username: ");
	        TextField txtUserName = new TextField();
	        uName.setFont(Font.font("Segoe UI Semibold", FontWeight.NORMAL, 15));
	        uName.setFill(Color.WHITE);
	        Text pWord = new Text("Password: ");
	        pWord.setFont(Font.font("Segoe UI Semibold", FontWeight.NORMAL, 15));
	        pWord.setFill(Color.WHITE);
	        PasswordField pf = new PasswordField();
	        
	        //login dropshadow
	        DropShadow ds = new DropShadow();
	        ds.setBlurType(BlurType.GAUSSIAN);
	        ds.setColor(Color.DARKSLATEGREY);
	        ds.setHeight(5);
	        ds.setRadius(20);
	        ds.setOffsetX(3);
	        ds.setOffsetY(2);
	        log.setEffect(ds);

	        //add title and login info to root(login scene)
	        root.getChildren().add(log);
	        root.getChildren().add(Titlebox);
	        root.getChildren().add(login);
	        
	        
	        Button loginButton = new Button("Login!");
	        loginButton.getStyleClass().add("logbutton");
	        loginButton.setMaxWidth(100);
	        loginButton.setMaxHeight(50);
	        
	        
	        //add login info to login gridpane
	        login.add(uName, 0,0);
	        login.add(txtUserName, 1, 0);
	        login.add(pWord, 0, 1);
	        login.add(pf, 1, 1);
	        login.add(loginButton, 1, 2);
	        
	        //login fire on enter press (need to focus button first)
	        loginButton.addEventHandler(KeyEvent.KEY_PRESSED, ev->{
	        	if(ev.getCode() == KeyCode.ENTER) {
	        		loginButton.fire();
	        		ev.consume();
	        	}
	        });
	        
	        //save input as strings and move to homeScene
	        loginButton.setOnAction(action -> {
	        	String uNameInput = String.valueOf(txtUserName.getText());
	        	String pwInput = String.valueOf(pf.getText());
	        	//test with system
	        	System.out.println(uNameInput);
	        	System.out.println(pwInput);
	        	
	        	//test user/password, change scene
	        	
		        homeScene.getStylesheets().clear();
		        homeScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
	        	primaryStage.setScene(homeScene);
	        });
	        
	        //########################### homeScene layout ##########################
	        

	        primaryStage.show();
	    }
	    
	    public static void main(String[] args) {
			launch(args);
		}

}