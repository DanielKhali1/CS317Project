import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GUI extends Application
{

	
	Pane pane = new Pane();
	Scene scene = new Scene(pane, 1080, 720);
	
	Manager manager = new Manager(scene.getWidth(), scene.getHeight());
	Rectangle playerDisplay;
	ArrayList<Rectangle> platformDisplay = new ArrayList<Rectangle>();
	
	
	
	/**
	 *  PLATFOMR POSITIONS
	 */
	
	
	
	

	boolean left;
	boolean right;
	
	@Override
	public void start(Stage stage)
	{
		playerDisplay = new Rectangle(manager.player.getWidth(), manager.player.getHeight());
		pane.getChildren().add(playerDisplay);
		
		//adding platforms
		manager.addPlatform(400+25, 400+25, 200, 50);
		platformDisplay.add(new Rectangle(200, 50));
		platformDisplay.get(platformDisplay.size()-1).relocate(400+25, 400+25);
		pane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
		manager.addPlatform(100+25, 250+25, 200, 50);
		platformDisplay.add(new Rectangle(200, 50));
		platformDisplay.get(platformDisplay.size()-1).relocate(100+25, 250+25);
		pane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
		manager.addPlatform(700+25, 250+25, 200, 50);
		platformDisplay.add(new Rectangle(200, 50));
		platformDisplay.get(platformDisplay.size()-1).relocate(700+25, 250+25);
		pane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
		
		manager.addPlatform(125, 600, 800, 50);
		platformDisplay.add(new Rectangle(800, 50));
		platformDisplay.get(platformDisplay.size()-1).relocate(125, 600);
		pane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
		
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
		
		pane.setOnMouseClicked(e->{
			manager.addPlatform(e.getX(), e.getY(), 200, 50);
			platformDisplay.add(new Rectangle(200, 50));
			platformDisplay.get(platformDisplay.size()-1).relocate(e.getX(), e.getY());
			pane.getChildren().add(platformDisplay.get(platformDisplay.size()-1));
			
			
			System.out.println("Platform = " + e.getX() + " " + e.getY());
		});
		
		
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), (ActionEvent event) -> 
		{

			if(left)
				manager.playerMoveLeft();
			if(right)
				manager.playerMoveRight();
			
			manager.update();
			playerDisplay.setLayoutX(manager.player.getPos().x);	playerDisplay.setLayoutY(manager.player.getPos().y);
			
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		stage.setScene(scene);
		stage.setTitle("Game Client");
		stage.show();
	}
	public static void main(String[] args) {launch(args);}
}
