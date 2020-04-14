package UI;

import java.util.ArrayList;
import java.util.Arrays;

import SQL.SQLCalls;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LeaderBoards extends Application
{
	Pane pane = new Pane();
	Scene scene = new Scene(pane, 1280, 720);
	String username;
	Button cells[][];
	SQLCalls s;
	
	double WIDTH_RANK = 60;
	double WIDTH_USERNAME= 100;
	double WIDTH_KD= 60;
	double WIDTH_WL= 60;
	double WIDTH_KILLS= 60;
	double WIDTH_DEATHS= 60;
	double WIDTH_WINS= 60;
	double WIDTH_LOSSES= 60;
	double WIDTH_TIMEPLAYED= 80 ;
	
	double height = 30;
	
	public LeaderBoards(String username)
	{
		this.username = username;
	}
	
	public LeaderBoards()
	{
		this.username = "TEST";
	}
	
	@Override
	public void start(Stage primaryStage)
	{
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
			}
			else
			{
				cells[i][0] = new Button((i+1)+"");
				cells[i][2] = new Button(	(Double.parseDouble(sqlGrab.get(i)[2]) == 0)? 0+"": (Double.parseDouble(sqlGrab.get(i)[1])/Double.parseDouble(sqlGrab.get(i)[2]) )+"");
				cells[i][3] = new Button((Double.parseDouble(sqlGrab.get(i)[4]) == 0)? 0+"": (Double.parseDouble(sqlGrab.get(i)[3])/Double.parseDouble(sqlGrab.get(i)[4]))+"");
				
			}
			cells[i][4] = new Button(sqlGrab.get(i)[1]);
			cells[i][5] = new Button(sqlGrab.get(i)[2]);
			cells[i][6] = new Button(sqlGrab.get(i)[3]);
			cells[i][7] = new Button(sqlGrab.get(i)[4]);
			cells[i][8] = new Button(sqlGrab.get(i)[5]);

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
			
			pane.getChildren().addAll(cells[i]);
		}
		
		for(int i = 0; i < cells.length; i++)
		{
			for(int j = 0; j < cells[0].length; j++)
			{
				//cells[i][j].setStyle("-fx-background-color: #5a9ccc;");
			}
		}
		
		
		
		
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("LeaderBoards");
	}

	public static void main(String[] args) {launch(args);}
}
