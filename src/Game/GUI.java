package Game;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	boolean host = false;
	
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
	
	
	public GUI(boolean host)
	{
		this.host = host;
	}
	

	boolean left;
	boolean right;
	
//	@Override
//	public void init() throws Exception
//	{
////		connection.startConnection();
//	}
	
	
	@Override
	public void start(Stage primaryStage)
	{
		
		Pane ppane = new Pane();
		Scene sscene = new Scene(ppane, 500, 500);
		Stage stage = new Stage();
		stage.setScene(sscene);
		stage.show();
		sscene.getStylesheets().add("File:///"+new File("style.css").getAbsolutePath().replace("\\","/"));
		scene.getStylesheets().add("File:///"+new File("style.css").getAbsolutePath().replace("\\","/"));
		primaryStage.setScene(scene);

		
		ppane.setStyle("-fx-background-color: #0f1f42;");
		
		if(host)
		{
			
			Text title = new Text("Hosting");
			title.setStyle("-fx-font-size: 30;");
			title.setFill(Color.WHITE);
			title.relocate(175, 20);
			
			isServer = true;
			
			String ip = "0.0.0.0";
			try
			{
				InetAddress inetAddress = InetAddress.getLocalHost();
				ip = inetAddress.getHostAddress()+"";
			}
			catch(Exception e)
			{
				
			}
			
			Text iptxt = new Text("Your IP Adress:\n" + ip);
			iptxt.setFill(Color.WHITE);

			iptxt.relocate(175, 100);
			iptxt.setStyle("-fx-font-size: 20; -fx-text-align: center;");

			
			Button playBt = new Button("Play");
			playBt.relocate(20, 400);
			Button cancelBt = new Button("Cancel");
			cancelBt.relocate(300, 400);
			
			Text waitingtxt = new Text("Waiting for players to join ...");
			waitingtxt.relocate(175, 200);
			waitingtxt.setFill(Color.WHITE);
	
			ppane.getChildren().addAll(title, iptxt, playBt, cancelBt, waitingtxt);
			
			connection = createServer();
			try {
				
				connection.startConnection();
			}
			catch(Exception e)
			{
				System.out.println("startconn didn't work");
			}
			
			Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), (ActionEvent event) -> 
			{
				try 
				{
					connection.send(isServer ? "server,"+ nme.getText() + "," + manager.player.getPos().x + "," + manager.player.getPos().y + "," 
							: "client,"+ nme.getText() + "," + manager.player.getPos().x + "," + manager.player.getPos().y + ",");
				} 
				catch (Exception e1) 
				{
					
				}

			}));
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
			
			playBt.setOnAction(e->{
				if(otherPlayerDisplays.size() > 0)
				{
					try
					{
						timeline.stop();
						nme.setText("Player");
						gamePane.getChildren().add(nme);
						primaryStage.setTitle(isServer ? "Server: " : "Client: ");
						
						primaryStage.show();
						
						
					} 
					catch (Exception e1) 
					{
						
					}
					stage.close();
				}
				else
				{
					System.out.println(otherPlayerDisplays.size());
				}
			});
		}
		else
		{
			

			
			
			Text title = new Text("Join");
			title.setStyle("-fx-font-size: 30");
			title.relocate(175, 20);
			title.setFill(Color.WHITE);
			
			isServer = false;

			Text infotxt = new Text("Enter Host's IP");
			infotxt.relocate(150, 100);
			infotxt.setStyle("-fx-font-size: 20");
			infotxt.setFill(Color.WHITE);
			
			
			TextField tf = new TextField();
			tf.relocate(125, 150);
			tf.setStyle("-fx-background-color: #0f1f42; -fx-border-color: white; -fx-font-size: 20; -fx-text-fill: white ");
			
			Button playBt = new Button("Connect");
			playBt.relocate(20, 400);
			
			Button cancelBt = new Button("Cancel");
			cancelBt.relocate(300, 400);
			
			
			ppane.getChildren().addAll(title, tf, playBt, cancelBt, infotxt);
			

			
			playBt.setOnAction(e->{
				connection = createClient(tf.getText());
				try {
					
					connection.startConnection();
				}
				catch(Exception e1)
				{
					System.out.println("startconn didn't work");
				}
				
					Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), (ActionEvent event) -> 
					{
						try 
						{
							connection.send(isServer ? "server,"+ nme.getText() + "," + manager.player.getPos().x + "," + manager.player.getPos().y + "," 
								: "client,"+ nme.getText() + "," + manager.player.getPos().x + "," + manager.player.getPos().y + ",");
						} 
						catch (Exception e1) 
						{
							
						}

					}));
					timeline.setCycleCount(Timeline.INDEFINITE);
					timeline.play();
				
					
					try
					{
						
						
						nme.setText("Player Client");
						gamePane.getChildren().add(nme);

						primaryStage.setTitle(isServer ? "Server: " : "Client: ");
						primaryStage.show();
						timeline.stop();
						stage.close();
					}
					catch (Exception e1) 
					{
						e1.printStackTrace();
					}		
					
			});
		}
		
		
//		otherPlayerDisplays.add(new PlayerRect(manager.player.getWidth(), manager.player.getHeight()));
//		otherPlayerDisplays.get(0).setFill(Color.BLUE);
//		gamePane.getChildren().addAll(otherPlayerDisplays.get(0), otherPlayerDisplays.get(0).name);
		gamePane.setId("gamepane");
		
		
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
				connection.send(isServer ? "server,"+ nme.getText() + "," + manager.player.getPos().x + "," + manager.player.getPos().y + "," 
						: "client,"+ nme.getText() + "," + manager.player.getPos().x + "," + manager.player.getPos().y + ",");
			}
			catch (Exception e1) 
			{
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
	private Client createClient(String ipAddress) {
		return new Client(ipAddress, 55555, nametf.getText(), data -> {
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
