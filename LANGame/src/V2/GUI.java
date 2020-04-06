package V2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application
{

	
	Pane gamePane = new Pane();
	Scene scene = new Scene(gamePane, 1080, 720);
	
	Manager manager = new Manager(scene.getWidth(), scene.getHeight());
	PlayerRect playerDisplay;
	ArrayList<Rectangle> platformDisplay = new ArrayList<Rectangle>();
	boolean isServer;
	private NetworkConnection connection ;//= isServer ? createServer() : createClient();
	
	TextField nametf = new TextField("");

	ArrayList<PlayerRect> otherPlayerDisplays = new ArrayList<PlayerRect>();
	
	Text nme = new Text();

	
	/**
	 *  PLATFOMR POSITIONS
	 */
	
	public class PlayerRect extends Rectangle
	{
		Text name;
		public PlayerRect (double w, double h)
		{
			super(w, h);
			name = new Text();
		}
		
	}
	
	

	boolean left;
	boolean right;
	
	@Override
	public void init() throws Exception
	{
//		connection.startConnection();
	}
	
	
	@Override
	public void start(Stage primaryStage)
	{
		
		VBox ppane = new VBox();
		ppane.setSpacing(20);
		Scene sscene = new Scene(ppane, 500, 500);
		Stage stage = new Stage();
		stage.setScene(sscene);
		stage.show();
		
		Button hostbt = new Button("Host");
		Button connectbt = new Button("Join");
		Text name = new Text("name");
		primaryStage.setScene(scene);

		ppane.getChildren().addAll(hostbt, connectbt, name, nametf);
		
		hostbt.setOnAction(e->
		{
			connection = createServer();
			try
			{
				nme.setText(nametf.getText());
				gamePane.getChildren().add(nme);
				isServer = true;
				primaryStage.setTitle(isServer ? "Server: " : "Client: ");

				connection.startConnection();
				primaryStage.show();


			} catch (Exception e1) 
			{
				e1.printStackTrace();
			}
			stage.close();
		});
		
		connectbt.setOnAction(e->
		{
			connection = createClient();
			try
			{
				nme.setText(nametf.getText());
				gamePane.getChildren().add(nme);
				isServer = false;
				connection.startConnection();

				primaryStage.setTitle(isServer ? "Server: " : "Client: ");
				primaryStage.show();

			}
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}		
			stage.close();
			
		});
		
		
//		otherPlayerDisplays.add(new PlayerRect(manager.player.getWidth(), manager.player.getHeight()));
//		otherPlayerDisplays.get(0).setFill(Color.BLUE);
//		gamePane.getChildren().addAll(otherPlayerDisplays.get(0), otherPlayerDisplays.get(0).name);
	
		playerDisplay = new PlayerRect(manager.player.getWidth(), manager.player.getHeight());
		gamePane.getChildren().add(playerDisplay);
		
		//adding platforms
		manager.addPlatform(400+25, 400+25, 200, 50);
		platformDisplay.add(new Rectangle(200, 50));
		platformDisplay.get(platformDisplay.size()-1).relocate(400+25, 400+25);
		gamePane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
		manager.addPlatform(100+25, 250+25, 200, 50);
		platformDisplay.add(new Rectangle(200, 50));
		platformDisplay.get(platformDisplay.size()-1).relocate(100+25, 250+25);
		gamePane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
		manager.addPlatform(700+25, 250+25, 200, 50);
		platformDisplay.add(new Rectangle(200, 50));
		platformDisplay.get(platformDisplay.size()-1).relocate(700+25, 250+25);
		gamePane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
		
		manager.addPlatform(125, 600, 800, 50);
		platformDisplay.add(new Rectangle(800, 50));
		platformDisplay.get(platformDisplay.size()-1).relocate(125, 600);
		gamePane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
		
		//handling key events
		scene.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.KP_RIGHT)
				right = true;
			if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.KP_LEFT)
				left = true;
			if(e.getCode() == KeyCode.SPACE)
				manager.playerJump();
		});
		scene.setOnKeyReleased(e->{
			if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.KP_RIGHT)
				right = false;
			if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.KP_LEFT)
				left = false;
		});
		
		gamePane.setOnMouseClicked(e->{
			manager.addPlatform(e.getX(), e.getY(), 200, 50);
			platformDisplay.add(new Rectangle(200, 50));
			platformDisplay.get(platformDisplay.size()-1).relocate(e.getX(), e.getY());
			gamePane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
			
		});


		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), (ActionEvent event) -> 
		{

			if(left)
				manager.playerMoveLeft();
			if(right)
				manager.playerMoveRight();
			
			manager.update();
			playerDisplay.setLayoutX(manager.player.getPos().x);	playerDisplay.setLayoutY(manager.player.getPos().y);
			nme.setLayoutX(manager.player.getPos().x);
			nme.setLayoutY(manager.player.getPos().y - 20);
			
			try 
			{
				connection.send(isServer ? "server,"+ nametf.getText() + "," + manager.player.getPos().x + "," + manager.player.getPos().y + "," 
						: "client,"+ nametf.getText() + "," + manager.player.getPos().x + "," + manager.player.getPos().y + ",");
			}
			catch (Exception e1) 
			{
				//System.out.println("socket sending messages not working");
			}
			
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	
	private Server createServer() {
		return new Server(55555, nametf.getText(), data -> {
			Platform.runLater(() ->{
				String[] datapoints = ((String) data).split(",");

				
				if(datapoints.length == 4 && !datapoints[1].equals(nme.getText()) )
				{
					
					boolean notFound = true;
					for(int i = 0; i < otherPlayerDisplays.size(); i++)
					{
						
						if(otherPlayerDisplays.get(i).name.getText().equals(datapoints[1]))
						{
							notFound = false;
							otherPlayerDisplays.get(i).name.setLayoutX(Double.parseDouble(datapoints[2]));
							otherPlayerDisplays.get(i).name.setLayoutY(Double.parseDouble(datapoints[3])- 20);
							otherPlayerDisplays.get(i).setLayoutX(Double.parseDouble(datapoints[2])); 
							otherPlayerDisplays.get(i).setLayoutY(Double.parseDouble(datapoints[3]));				
						}

					}
					if(notFound)
					{
						otherPlayerDisplays.add(new PlayerRect(manager.player.getWidth(), manager.player.getHeight()));
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setText(datapoints[1]);
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setLayoutX(Double.parseDouble(datapoints[2]));
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setLayoutY(Double.parseDouble(datapoints[3])- 20);
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).setLayoutX(Double.parseDouble(datapoints[2])); 
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).setLayoutY(Double.parseDouble(datapoints[3]));
						gamePane.getChildren().addAll(otherPlayerDisplays.get(otherPlayerDisplays.size()-1), otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name);

					}
				}
				try 
				{
					connection.send(data);
				} 
				catch (Exception e)
				{
					//e.printStackTrace();
					//System.out.println("Had nothing to resend");
				}
			});
			
		});
	}
	private Client createClient() {
		return new Client("127.0.0.1", 55555, nametf.getText(), data -> {
			Platform.runLater(() ->{
				String[] datapoints = ((String) data).split(",");

				
				if(datapoints.length == 4 && !datapoints[1].equals(nme.getText()) )
				{
					
					boolean notFound = true;
					for(int i = 0; i < otherPlayerDisplays.size(); i++)
					{
						
						if(otherPlayerDisplays.get(i).name.getText().equals(datapoints[1]))
						{
							notFound = false;
							otherPlayerDisplays.get(i).name.setLayoutX(Double.parseDouble(datapoints[2]));
							otherPlayerDisplays.get(i).name.setLayoutY(Double.parseDouble(datapoints[3])- 20);
							otherPlayerDisplays.get(i).setLayoutX(Double.parseDouble(datapoints[2])); 
							otherPlayerDisplays.get(i).setLayoutY(Double.parseDouble(datapoints[3]));				
						}

					}
					if(notFound)
					{
						System.out.println("I HAVE BEEN RAN");
						otherPlayerDisplays.add(new PlayerRect(manager.player.getWidth(), manager.player.getHeight()));
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setText(datapoints[1]);
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setLayoutX(Double.parseDouble(datapoints[2]));
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setLayoutY(Double.parseDouble(datapoints[3])- 20);
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).setLayoutX(Double.parseDouble(datapoints[2])); 
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).setLayoutY(Double.parseDouble(datapoints[3]));
						gamePane.getChildren().addAll(otherPlayerDisplays.get(otherPlayerDisplays.size()-1), otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name);

					}
				}
				try 
				{
					//connection.send(data);
				} 
				catch (Exception e)
				{
					//e.printStackTrace();
					//System.out.println("Had nothing to resend");
				}
			});
			
		});
	}
	
	@Override
	public void stop() throws Exception
	{
		connection.closeConnection();
	}
	
	
	public static void main(String[] args) {launch(args);}
}
