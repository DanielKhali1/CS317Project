package UI;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;
import UI.Profile;
import UI.Settings;
import Game.GUI;
import SQL.SQLCalls;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
	Scene profileScene;
	Scene setScene;
	Scene leadScene;
	String user = "Test";
	String pw = "Test";
	
	SQLCalls s;
	
	private boolean alreadyLogged = false;
	
		public GameGui(String user, String pw)
		{
			this.user = user;
			this.pw = pw;
			alreadyLogged = true;
		}
		public GameGui()
		{
			alreadyLogged = false;
		}
	
	    @Override
	    public void start(Stage primaryStage) 
	    {
	    	
			s = new SQLCalls("mysql.us.cloudlogin.co", "3306", "dkhalil_cs317", "dkhalil_cs317", "6d9d6FHkfI");

	    	//set css file
	    	File f = new File("style.css");
	    	//###########################  Instantiate panes/scenes  #############################
	    	//Initial scene
	        StackPane root = new StackPane();
	        root.setId("pane");
	        loginScene = new Scene(root, 1000, 700);
	        //add css to login
	    	loginScene.getStylesheets().clear();
	    	try {
				loginScene.getStylesheets().add(f.toURI().toURL().toExternalForm());
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
	    	
	        //home scene
	        BorderPane homePane = new BorderPane();
	        homePane.setId("pane");
	        homeScene = new Scene(homePane, 1000, 700);
	        
	        if(!alreadyLogged)
	        {
	        	primaryStage.setScene(loginScene);
	        	primaryStage.setTitle("Battle Royale Free for Kids");
	        	
	        }
	        else
	        {
	        	homeScene.getStylesheets().clear();
	        	homeScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
	        	primaryStage.setScene(homeScene);	
	        }
	        
	        Button host = new Button();
	        Button join = new Button();
	        host.getStyleClass().add("host");
	        join.getStyleClass().add("join");
	        HBox play = new HBox();
	        play.getChildren().addAll(join, host);
	        play.setAlignment(Pos.BASELINE_CENTER);
	        homePane.setBottom(play);


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
	        log.setFill(Color.rgb(73, 110, 136,1));
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
        	Text confirmpw = new Text("Confirm Password: ");
	        confirmpw.setFont(Font.font("Segoe UI Semibold", FontWeight.NORMAL, 15));
	        confirmpw.setFill(Color.WHITE);
        	PasswordField confirmfield = new PasswordField();
	        
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
	        Button createAcc = new Button("Create Account");
	        createAcc.getStyleClass().add("createAcc");
	        loginButton.getStyleClass().add("logbutton");
	        createAcc.setMaxHeight(50);
	        createAcc.setMaxWidth(100);
	        loginButton.setMaxWidth(100);
	        loginButton.setMaxHeight(50);
	        Button finishButton = new Button("Finish Creation");
	        finishButton.getStyleClass().add("logbutton");
	        finishButton.setMaxHeight(50);
	        finishButton.setMaxWidth(150);
	        
	        
	        //add login info to login gridpane
	        login.add(uName, 0,0);
	        login.add(txtUserName, 1, 0);
	        login.add(pWord, 0, 1);
	        login.add(pf, 1, 1);
	        login.add(loginButton, 1, 2);
	        login.add(createAcc, 1, 3);
	        
	        //login fire on enter press (need to focus button first)
	        loginScene.setOnKeyReleased(e->{
	        	if(e.getCode() == KeyCode.ENTER)
	        	{
		        	loginAction(txtUserName.getText(), pf.getText(), primaryStage,f);
	        	}
	        });
	        
	        
	        //save input as strings and move to homeScene
	        loginButton.setOnAction(action -> {
	        	loginAction(txtUserName.getText(), pf.getText(), primaryStage, f);
	        });
	        
	        createAcc.setOnAction(action -> {
	        	log.setHeight(250);
	        	log.setWidth(350);
	        	login.getChildren().remove(createAcc);
	        	login.getChildren().remove(loginButton);
	        	login.add(confirmpw, 0, 2);
	        	login.add(confirmfield, 1, 2);
	        	login.add(finishButton, 1, 3);
	        	
	        });
	        
	        finishButton.setOnAction(action -> {
	        	String uNameInput = txtUserName.getText();
	        	String pwInput = pf.getText();
	        	String confirmPW = confirmfield.getText();

	        	ArrayList<String> users = s.getAllUsernames();
	        	
	        	boolean bad = false;
	        	
	        	for(int i = 0; i < users.size(); i++)
	        	{
	        		if(uNameInput.equals(users.get(i)))
	        		{
		        		bad = true;
	        			break;
	        		}
	        	}
	        	
	        	if(bad)
	        	{
        			Alert alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Username Already Taken Dialog");
	        		alert.setHeaderText("Error");
	        		alert.setContentText("please input a different username!");
	        		alert.showAndWait();
	        	}
	        	else if(uNameInput.length() == 0) {
	        		Alert alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Error Dialog");
	        		alert.setHeaderText("Error");
	        		alert.setContentText("please input a username!");
	        	}
	        	else if(!pwInput.equals(confirmPW)){
	        		Alert alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Error Dialog");
	        		alert.setHeaderText("Error");
	        		alert.setContentText("Your Passwords have to match!");
	        		alert.showAndWait();
	        	}
	        	else
	        	{
	        		s.newRecord(uNameInput, pwInput);
	        		
	        		Alert alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("Success");
	        		alert.setHeaderText("Successfully Created a new Acccount");
	        		Optional<ButtonType> result = alert.showAndWait();
	        		
	        		System.out.println("alert should be shown");
	        		
	        		if(result.get() == ButtonType.OK)
	        		{
	        			pw = pwInput;
			        	user = uNameInput;
			        	
			        	//test with system
			        	System.out.println(uNameInput);
			        	System.out.println(pwInput);
			        	
			        	//test user/password, change scene
			        	
				        homeScene.getStylesheets().clear();
				        homeScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
			        	primaryStage.setScene(homeScene);
	        		}
	        		
	        		alert.setOnCloseRequest(e->{
	        			pw = pwInput;
			        	user = uNameInput;
			        	
			        	//test with system
			        	System.out.println(uNameInput);
			        	System.out.println(pwInput);
			        	
			        	//test user/password, change scene
			        	
				        homeScene.getStylesheets().clear();
				        homeScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
			        	primaryStage.setScene(homeScene);
	        		});
	        		
	        		
	        	}
	        	
	        });
	        
	        
	        //########################### homeScene layout ##########################
	        homePane.setPadding(new Insets(30,20,20,20));
	        HBox hometiles = new HBox();
	        Button settings = new Button();
	        Button profile = new Button();
	        Button leader = new Button();
	        profile.getStyleClass().add("profile");
	        leader.getStyleClass().add("leaderboards");
	        settings.getStyleClass().add("settings");
	        hometiles.getChildren().addAll(settings, profile, leader);
	        homePane.setCenter(hometiles);

	        
	       host.setOnAction(e->{
	    	   new GUI(true, user, pw).start(new Stage());
	    	   primaryStage.close();
	       });
	       
	       join.setOnAction(e->{
	    	   new GUI(false, user, pw).start(new Stage());
	    	   primaryStage.close();
	       });
	       
	       profile.setOnAction(e -> {
				try {
					new Profile().start(new Stage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	   primaryStage.close();
	       });
	       
	       settings.setOnAction(e -> {
	    	   try {
				new Settings().start(new Stage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	   primaryStage.close();
	       });
	       
	       leader.setOnAction(e -> {
	    	   primaryStage.setScene(leadScene);
	       });


	        primaryStage.show();
	    }
	    
	    public static void main(String[] args) {
			launch(args);
		}
	    
	    public void loginAction(String user, String pass, Stage primaryStage, File f)
	    {
        	String uNameInput = user;
        	String pwInput = pass;
        	
        	boolean unlock = false;
        	
        	try{
        		unlock = s.getPassword(uNameInput).equals(pwInput);
        	}
        	catch(Exception e )
        	{
        		System.out.println("Error connecting to database");
        	}
        	
        	
        	if(uNameInput.length()<1 || pwInput.length()<1) {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Dialog");
        		alert.setHeaderText("Error");
        		alert.setContentText("please input a username and password");

        		alert.showAndWait();
        	}
        	else if(unlock)
        	{
	        	pw = pwInput;
	        	user = uNameInput;
	        	
	        	//test with system
	        	System.out.println(uNameInput);
	        	System.out.println(pwInput);
	        	
	        	//test user/password, change scene
	        	
		        homeScene.getStylesheets().clear();
		        homeScene.getStylesheets().add("File:///"+f.getAbsolutePath().replace("\\","/"));
	        	primaryStage.setScene(homeScene);
        	}
        	else
        	{
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error Dialog");
        		alert.setHeaderText("Error");
        		alert.setContentText("Incorrect Username or Password");

        		alert.showAndWait();
        	}
	    }

}
