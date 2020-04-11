package Game;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;

import UI.GameGui;
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

	public final static double BULLET_SPEED = 15;
	Pane gamePane = new Pane();
	Scene scene = new Scene(gamePane, 1080, 720);
	
	Manager manager = new Manager(scene.getWidth(), scene.getHeight());
	PlayerRect playerDisplay;
	ArrayList<Rectangle> platformDisplay = new ArrayList<Rectangle>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	boolean isServer;
	private NetworkConnection connection ;//= isServer ? createServer() : createClient();
	
	TextField nametf = new TextField("");

	ArrayList<PlayerRect> otherPlayerDisplays = new ArrayList<PlayerRect>();
	
	Text nme = new Text();
	boolean host = false;
	boolean direction = false;
	Rectangle rect = new Rectangle(300,20);
	Rectangle Enemyrect = new Rectangle(300,20);
	Text healthbarName = new Text();
	Text enemyHealthbarName = new Text();
	
	Image p1Heart;
	Image p1;
	Image p2Heart;
	Image p2;
	
	int enemyHealth = 3;
	int playerHealth = 3;
	Timeline timeline;
	
	String username;
	String password;
	/**
	 *  PLATFOMR POSITIONS
	 */
	
	public class PlayerRect extends Rectangle
	{
		Text name;
		ImageView p2View;
		public PlayerRect (double w, double h)
		{
			super(w, h);
			name = new Text();
			name.setFill(Color.WHITE);
			
			p2View = new ImageView(p2);
			p2View.setFitHeight(manager.player.getHeight());
			p2View.setFitWidth(manager.player.getWidth());
			//gamePane.getChildren().add(p2View);
		}
	}
	
	
	public GUI(boolean host, String username, String password)
	{
		this.host = host;
		this.username = username;
		this.password = password;
	}
	

	boolean left;
	boolean right;
	
//	@Override
//	public void init() throws Exception
//	{
//		connection.startConnection();
//	}
	
	
	@Override
	public void start(Stage primaryStage)
	{
		p1 = new Image("P1.png");
		p1Heart = new Image("P1Heart.png");	
		p2 = new Image("P2.png");
		p2Heart = new Image("P2Heart.png");	
		
		
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
						healthbarName.setText("Player");
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
						healthbarName.setText("Player Client");

						nme.setFill(Color.WHITE);
						
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
		
		
		gamePane.setId("gamepane");
		
		
		Rectangle backgroundrect = new Rectangle(300,20);
		backgroundrect.relocate(20, 40);
		backgroundrect.setFill(Color.BLACK);
		gamePane.getChildren().add(backgroundrect);
		
		
		rect.relocate(20, 40);
		rect.setFill(new Color(99.0/255.0, 213.0/255.0, 255.0/255.0, 1));
		rect.setStroke(Color.WHITE);
		gamePane.getChildren().add(rect);
		
		
		healthbarName.relocate(20, 20);
		healthbarName.setStyle("-fx-font-size: 30");
		gamePane.getChildren().add(healthbarName);
		healthbarName.setFill(Color.WHITE);
		
		ImageView life1 = new ImageView(p1Heart);
		life1.setFitHeight(25);
		life1.setFitWidth(25);
		life1.relocate(20, 65);
		ImageView life2 = new ImageView(p1Heart);
		life2.setFitHeight(25);
		life2.setFitWidth(25);
		life2.relocate(50, 65);
		ImageView life3 = new ImageView(p1Heart);
		life3.setFitHeight(25);
		life3.setFitWidth(25);
		life3.relocate(80, 65);
		gamePane.getChildren().addAll(life1, life2, life3);
		
		
		Rectangle secondbackgroundrect = new Rectangle(300,20);
		secondbackgroundrect.relocate(750, 40);
		secondbackgroundrect.setFill(Color.WHITE);
		gamePane.getChildren().add(secondbackgroundrect);
		
		Enemyrect.relocate(750, 40);
		Enemyrect.setFill(new Color(255/255, 150/255, 255/255, 1));
		Enemyrect.setStroke(Color.WHITE);
		gamePane.getChildren().add(Enemyrect);
		
		ImageView Enemylife1 = new ImageView(p2Heart);
		Enemylife1.setFitHeight(25);
		Enemylife1.setFitWidth(25);
		Enemylife1.relocate(750, 65);
		ImageView Enemylife2 = new ImageView(p2Heart);
		Enemylife2.setFitHeight(25);
		Enemylife2.setFitWidth(25);
		Enemylife2.relocate(780, 65);
		ImageView Enemylife3 = new ImageView(p2Heart);
		Enemylife3.setFitHeight(25);
		Enemylife3.setFitWidth(25);
		Enemylife3.relocate(810, 65);
		gamePane.getChildren().addAll(Enemylife1, Enemylife2, Enemylife3);
		
		enemyHealthbarName.relocate(750, 20);
		enemyHealthbarName.setStyle("-fx-font-size: 30");
		enemyHealthbarName.setFill(Color.WHITE);

		gamePane.getChildren().add(enemyHealthbarName);
		
		
		
		
		playerDisplay = new PlayerRect(manager.player.getWidth(), manager.player.getHeight());
		playerDisplay.setStroke(Color.WHITE);
		playerDisplay.setStrokeWidth(3);
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
		scene.setOnKeyPressed(e->
		{
			if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.KP_RIGHT)
			{
				right = true;
				direction = true;
			}
			if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.KP_LEFT)
			{
				left = true;
				direction = false;
			}
			if(e.getCode() == KeyCode.SPACE)
				manager.playerJump();

		});
		scene.setOnKeyReleased(e->{
			if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.KP_RIGHT)
				right = false;
			if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.KP_LEFT)
				left = false;
			if(e.getCode() == KeyCode.E || e.getCode() == KeyCode.ENTER)
			{
				bullets.add(new Bullet((direction) ? new Vector(manager.player.getPos().x + manager.player.getWidth() + 10, manager.player.getPos().y + manager.player.getHeight()/2) : new Vector(manager.player.getPos().x - 10, manager.player.getPos().y + manager.player.getHeight()/2), BULLET_SPEED, direction));
				gamePane.getChildren().add(bullets.get(bullets.size()-1).getDisplay());
				try 
				{
					connection.send(manager.player.getPos().x+","+manager.player.getPos().y + "," + direction + "," + bullets.get(bullets.size()-1).getId() +",bullet");
				}
				catch (Exception e1) {}
			}
		});

		
		ImageView p1View = new ImageView(p1);
		p1View.setFitHeight(manager.player.getHeight());
		p1View.setFitWidth(manager.player.getWidth());
		gamePane.getChildren().add(p1View);
		

		
		timeline = new Timeline(new KeyFrame(Duration.millis(25), (ActionEvent event) -> 
		{
			
			if(rect.getWidth() < 0)
			{
				playerHealth --;
				rect.setWidth(300);
				manager.player.setPos(new Vector(scene.getWidth()/2, -100));
				switch(playerHealth)
				{
					case 0:
						gamePane.getChildren().remove(life3);
						timeline.stop();
						Text txt = new Text(" YOU LOST ");
						txt.setFill(Color.WHITE);
						txt.setStyle("-fx-font-size: 50; -fx-font-weight: bold");
						txt.relocate(400, 200);
						Button exitbt = new Button("Exit");
						exitbt.relocate(500,300);

						gamePane.getChildren().addAll(txt, exitbt);
						
						exitbt.setOnAction(e->{
							try 
							{
								connection.closeConnection();
							} catch (Exception e1) {}
							primaryStage.close();
							
							new GameGui(username, password).start(new Stage());
							
						});
						
						break;
					case 1:
						gamePane.getChildren().remove(life2);
						break;
					case 2:
						gamePane.getChildren().remove(life3);
						break;
				}
			}
			
			if(Enemyrect.getWidth() < 0)
			{
				Enemyrect.setWidth(300);
				
				enemyHealth --;
				switch(enemyHealth)
				{
					case 0:
						gamePane.getChildren().remove(Enemylife3);
						timeline.stop();
						Text txt = new Text(" YOU WON!!! ");
						txt.setFill(Color.WHITE);
						txt.setStyle("-fx-font-size: 50; -fx-font-weight: bold");
						txt.relocate(400, 200);
						Button exitbt = new Button("Exit");
						exitbt.relocate(500,300);
						
						gamePane.getChildren().addAll(txt, exitbt);
						
						exitbt.setOnAction(e->{
							try 
							{
								connection.closeConnection();
							} catch (Exception e1) 
							{
							}
							primaryStage.close();
							new GameGui(username, password).start(new Stage());
						});
						
						
						
						break;
					case 1:
						gamePane.getChildren().remove(Enemylife2);
						break;
					case 2:
						gamePane.getChildren().remove(Enemylife3);
						break;
				}
			}
			
			if(otherPlayerDisplays.size() > 1)
			{
				otherPlayerDisplays.get(0).relocate(-100, -100);
				otherPlayerDisplays.get(0).p2View.relocate(-100, -100);
			}
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
			
			for(int i = 0; i < bullets.size(); i++)
			{
				bullets.get(i).update();
				int index = 0;
				if(!isServer)
					index =1 ;
				
				
				
				if (otherPlayerDisplays.size() > 0 && otherPlayerDisplays.get(index).getLayoutX() < bullets.get(i).getPos().x + 10 &&
						otherPlayerDisplays.get(index).getLayoutX() + otherPlayerDisplays.get(index).getWidth() > bullets.get(i).getPos().x &&
						otherPlayerDisplays.get(index).getLayoutY() <  bullets.get(i).getPos().y + 10 &&
						otherPlayerDisplays.get(index).getLayoutY() + otherPlayerDisplays.get(index).getHeight() > bullets.get(i).getPos().y) 
				{
					Enemyrect.setWidth(Enemyrect.getWidth() - (300/10));
					try {
						connection.send("0.1");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					try {
					connection.send("DESTROY" + "," + bullets.get(i).getId());
					}
					catch(Exception e)
					{
						
					}
					gamePane.getChildren().remove(bullets.get(i).getDisplay());
					bullets.remove(i);
					i--;
					
				}
				
				
				else if(bullets.get(i).getPos().x > scene.getWidth() || bullets.get(i).getPos().x < 0)
				{
					gamePane.getChildren().remove(bullets.get(i).getDisplay());
					bullets.remove(i);
					i--;
				}
			}
			
			p1View.setLayoutX(manager.player.getPos().x);
			p1View.setLayoutY(manager.player.getPos().y);
			
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
							otherPlayerDisplays.get(i).p2View.setLayoutX(Double.parseDouble(datapoints[2])); 
							otherPlayerDisplays.get(i).p2View.setLayoutY(Double.parseDouble(datapoints[3]));
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
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).setStroke(Color.WHITE);
						enemyHealthbarName.setText(datapoints[1]);

						gamePane.getChildren().addAll(otherPlayerDisplays.get(otherPlayerDisplays.size()-1), otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name
								, otherPlayerDisplays.get(otherPlayerDisplays.size()-1).p2View);

					}
				}
				else if(datapoints[0].equals("DESTROY"))
				{
					for(int i = 0; i < bullets.size(); i++)
					{
						if(bullets.get(i).getId() == Double.parseDouble(datapoints[1]))
						{
							gamePane.getChildren().remove(bullets.get(i).getDisplay());
							bullets.remove(i);
							break;
						}
					}
				}
				else if(datapoints.length == 5)
				{
					bullets.add(new Bullet((datapoints[2].equals("true")) ? new Vector(Double.parseDouble(datapoints[0]) + manager.player.getWidth() + 10, Double.parseDouble(datapoints[1]) + manager.player.getHeight()/2) : new Vector(Double.parseDouble(datapoints[0]) - 10, Double.parseDouble(datapoints[1]) + manager.player.getHeight()/2), BULLET_SPEED, datapoints[2].equals("true"), Double.parseDouble(datapoints[3])));
					gamePane.getChildren().add(bullets.get(bullets.size()-1).getDisplay());
				}
				else if(datapoints.length == 1)
				{
					if(Double.parseDouble(datapoints[0]) == 0.1)
					rect.setWidth(rect.getWidth() - (300/10));
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
							otherPlayerDisplays.get(i).p2View.setLayoutX(Double.parseDouble(datapoints[2])); 
							otherPlayerDisplays.get(i).p2View.setLayoutY(Double.parseDouble(datapoints[3]));
						}

					}
					
					if(notFound)
					{
						System.out.println("I HAVE BEEN RAN");
						otherPlayerDisplays.add(new PlayerRect(manager.player.getWidth(), manager.player.getHeight()));
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).setStroke(Color.WHITE);
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setText(datapoints[1]);
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setLayoutX(Double.parseDouble(datapoints[2]));
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name.setLayoutY(Double.parseDouble(datapoints[3])- 20);
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).setLayoutX(Double.parseDouble(datapoints[2])); 
						otherPlayerDisplays.get(otherPlayerDisplays.size()-1).setLayoutY(Double.parseDouble(datapoints[3]));
						enemyHealthbarName.setText(datapoints[1]);
						//gamePane.getChildren().addAll(otherPlayerDisplays.get(otherPlayerDisplays.size()-1), otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name);
						gamePane.getChildren().addAll(otherPlayerDisplays.get(otherPlayerDisplays.size()-1), otherPlayerDisplays.get(otherPlayerDisplays.size()-1).name
								, otherPlayerDisplays.get(otherPlayerDisplays.size()-1).p2View);
					}
				}
				else if(datapoints[0].equals("DESTROY"))
				{
					for(int i = 0; i < bullets.size(); i++)
					{
						if(bullets.get(i).getId() == Double.parseDouble(datapoints[1]))
						{
							gamePane.getChildren().remove(bullets.get(i).getDisplay());
							bullets.remove(i);
							break;
						}
					}
				}
				else if(datapoints.length == 5)
				{
					bullets.add(new Bullet((datapoints[2].equals("true")) ? new Vector(Double.parseDouble(datapoints[0]) + manager.player.getWidth() + 10, Double.parseDouble(datapoints[1]) + manager.player.getHeight()/2) : new Vector(Double.parseDouble(datapoints[0]) - 10, Double.parseDouble(datapoints[1]) + manager.player.getHeight()/2), BULLET_SPEED, datapoints[2].equals("true"), Double.parseDouble(datapoints[3])));
					gamePane.getChildren().add(bullets.get(bullets.size()-1).getDisplay());
					
					
				}
				else if(datapoints.length == 1)
				{
					if(Double.parseDouble(datapoints[0]) == 0.1)
					rect.setWidth(rect.getWidth() - (300/10));
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
