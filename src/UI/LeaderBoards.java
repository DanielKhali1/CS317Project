package UI;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import SQL.SQLCalls;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderBoards extends Application
{
	Pane pane = new Pane();
	Scene scene = new Scene(pane, 1280, 720);
	String username;
	String pass;
	Button cells[][];
	SQLCalls s;
	
	double WIDTH_RANK = 100;
	double WIDTH_USERNAME= 150;
	double WIDTH_KD= 100;
	double WIDTH_WL= 100;
	double WIDTH_KILLS= 100;
	double WIDTH_DEATHS= 100;
	double WIDTH_WINS= 100;
	double WIDTH_LOSSES= 100;
	double WIDTH_TIMEPLAYED= 150 ;
	
	double height = 45;
	
	public LeaderBoards(String username, String pass)
	{
		this.username = username;
		this.pass = pass;
	}
	
	public LeaderBoards()
	{
		this.username = "Danny";
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		try {
        	scene.getStylesheets().add(new File("style.css").toURI().toURL().toExternalForm());
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		}		pane.setId("pane");
		Pane leaderboardPane = new Pane();
		ScrollPane sp = new ScrollPane(leaderboardPane);
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sp.setPrefHeight(500);
		sp.relocate(120, 150);
		sp.setStyle("-fx-background-color: black");
		
		Text title = new Text("Rankings");
		title.relocate(500, 80);
		title.setStyle("-fx-font-size: 60;");
		title.setFill(Color.WHITE);
		pane.getChildren().add(title);
		
		Image back = new Image("back-button.png");
		ImageView backbtn = new ImageView(back);
		backbtn.relocate(20, 20);
		backbtn.setFitHeight(75);
		backbtn.setFitWidth(75);
		
		backbtn.setOnMouseEntered(e->{
			
			backbtn.setFitHeight(80);
			backbtn.setFitWidth(80);
			backbtn.setLayoutX(17);
			backbtn.setLayoutY(17);
		});
		backbtn.setOnMouseExited(e->{
			backbtn.setLayoutX(20);
			backbtn.setLayoutY(20);
			backbtn.setFitHeight(75);
			backbtn.setFitWidth(75);
		});
		
		backbtn.setOnMouseClicked(e->{
			new GameGui(username,pass).start(new Stage());
			primaryStage.close();
		});
		
		pane.getChildren().add(backbtn);
		
		s = new SQLCalls("mysql.us.cloudlogin.co", "3306", "dkhalil_cs317", "dkhalil_cs317", "6d9d6FHkfI");
		ArrayList<String[]> sqlGrab = s.getLeaderBoard();
		String titles[] = {"Username","Kills","Deaths","Wins","Losses","TimePlayed"};
		sqlGrab.add(0, titles);
		
		cells = new Button[sqlGrab.size()][9];
		
		for(int i = 0; i < cells.length; i++)
		{
			
			cells[i][1] = new Button(sqlGrab.get(i)[0]);
			if(i == 0)
			{
				cells[i][0] = new Button("Rank");
				cells[i][2] = new Button("K/D");
				cells[i][3] = new Button("W/L");
				cells[i][4] = new Button(sqlGrab.get(i)[1]);
				cells[i][5] = new Button(sqlGrab.get(i)[2]);
				cells[i][6] = new Button(sqlGrab.get(i)[3]);
				cells[i][7] = new Button(sqlGrab.get(i)[4]);
				cells[i][8] = new Button(sqlGrab.get(i)[5]);
			}
			else
			{
				cells[i][0] = new Button((i)+"");
				cells[i][2] = new Button(	(Double.parseDouble(sqlGrab.get(i)[2]) == 0)? (Math.round(Double.parseDouble(sqlGrab.get(i)[1])*100.0)/100.0+"") : Math.round((Double.parseDouble(sqlGrab.get(i)[1])/Double.parseDouble(sqlGrab.get(i)[2]))*100.0)/100.0 +"") ;
				cells[i][3] = new Button(	(Double.parseDouble(sqlGrab.get(i)[4]) == 0)? (Math.round(Double.parseDouble(sqlGrab.get(i)[3])*100.0)/100.0+"") : Math.round((Double.parseDouble(sqlGrab.get(i)[3])/Double.parseDouble(sqlGrab.get(i)[4]))*100.0)/100.0 +"")  ;
				cells[i][4] = new Button(sqlGrab.get(i)[1]);
				cells[i][5] = new Button(sqlGrab.get(i)[2]);
				cells[i][6] = new Button(sqlGrab.get(i)[3]);
				cells[i][7] = new Button(sqlGrab.get(i)[4]);
				cells[i][8] = new Button(Math.round(Double.parseDouble(sqlGrab.get(i)[5]) * 100.0)/100.0 + "");
				
			}

			cells[i][0].setPrefWidth(WIDTH_RANK);
			cells[i][1].setPrefWidth(WIDTH_USERNAME); 
			cells[i][2].setPrefWidth(WIDTH_KD);  
			cells[i][3].setPrefWidth(WIDTH_WL); 
			cells[i][4].setPrefWidth(WIDTH_KILLS); 
			cells[i][5].setPrefWidth(WIDTH_DEATHS); 
			cells[i][6].setPrefWidth(WIDTH_WINS); 
			cells[i][7].setPrefWidth(WIDTH_LOSSES); 
			cells[i][8].setPrefWidth(WIDTH_TIMEPLAYED);

			cells[i][0].relocate(0, i*height);
			cells[i][1].relocate(cells[i][0].getPrefWidth(), i*height);
			cells[i][2].relocate(cells[i][0].getPrefWidth()+cells[i][1].getPrefWidth(), i*height);
			cells[i][3].relocate(cells[i][0].getPrefWidth()+cells[i][1].getPrefWidth()+ cells[i][2].getPrefWidth(), i*height);
			cells[i][4].relocate(cells[i][0].getPrefWidth()+cells[i][1].getPrefWidth()+ cells[i][2].getPrefWidth() + cells[i][3].getPrefWidth(), i*height);
			cells[i][5].relocate(cells[i][0].getPrefWidth()+cells[i][1].getPrefWidth()+ cells[i][2].getPrefWidth() + cells[i][3].getPrefWidth() + cells[i][4].getPrefWidth(), i*height);
			cells[i][6].relocate(cells[i][0].getPrefWidth()+cells[i][1].getPrefWidth()+ cells[i][2].getPrefWidth() + cells[i][3].getPrefWidth() + cells[i][4].getPrefWidth() + cells[i][5].getPrefWidth(), i*height);
			cells[i][7].relocate(cells[i][0].getPrefWidth()+cells[i][1].getPrefWidth()+ cells[i][2].getPrefWidth() + cells[i][3].getPrefWidth() + cells[i][4].getPrefWidth() + cells[i][5].getPrefWidth() + cells[i][6].getPrefWidth(), i*height);
			cells[i][8].relocate(cells[i][0].getPrefWidth()+cells[i][1].getPrefWidth()+ cells[i][2].getPrefWidth() + cells[i][3].getPrefWidth() + cells[i][4].getPrefWidth() + cells[i][5].getPrefWidth() + cells[i][6].getPrefWidth() + cells[i][7].getPrefWidth(), i*height);
			
			leaderboardPane.getChildren().addAll(cells[i]);
		}
		
		for(int i = 0; i < cells.length; i++)
		{
			for(int j = 0; j < cells[0].length; j++)
			{
				if(i == 0)
				{
					cells[i][j].setStyle("-fx-background-color: #2d5775; -fx-font-weight: bold; -fx-font-size: 20; -fx-border-color:black; -fx-text-fill: white");
				}
				else if(j == 0)
				{
					cells[i][j].setStyle("-fx-background-color: #87c3ed; -fx-font-size: 20; -fx-border-color:black; -fx-font-weight: bold;");
				}
				else if(cells[i][1].getText().equals(username))
				{
					cells[i][j].setStyle("-fx-background-color: #a6daff; -fx-font-size: 20; -fx-border-color:black;-fx-text-fill: black;");
				}
				else
				{
					cells[i][j].setStyle("-fx-background-color: #87c3ed; -fx-font-size: 20; -fx-border-color:black;");
				}
			}
		}
		
		
		pane.getChildren().add(sp);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("LeaderBoards");
	}

	public static void main(String[] args) {launch(args);}
}
