package V4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatClient extends Application
{

	Pane pane = new Pane();
	VBox vb = new VBox();
	Scene scene = new Scene(pane, 400, 600);
	
	Client c;
	Server s;
	TextArea messages = new TextArea();
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		pane.getChildren().add(vb);
		
		
		TextField Nametf = new TextField("Name");
		TextField IPAdress = new TextField("0.0.0.0");
		
		Button hostbt = new Button("Host");
		Button joinbt = new Button("Join");
		
		vb.getChildren().addAll(Nametf, IPAdress, hostbt, joinbt);
		
		hostbt.setOnAction(e->{
			primaryStage.close();
			
			Pane chatPane = new Pane();
			Scene chatScene = new Scene(chatPane, 400, 600);
			Stage chatStage = new Stage();
			
			s = new Server();
			c = new Client(data ->{
				Platform.runLater(()->{
					try
					{
						messages.appendText(data.toString() + "\n");
					}
					catch(Exception a)
					{
						
					}
			});
		});
			
			Platform.runLater(() ->{
				s.start();
			});
			Platform.runLater(() ->{
				c.start();
			});
			

			VBox vb2 = new VBox();
			chatPane.getChildren().add(vb2);
			TextField inputText = new TextField();
			vb2.setSpacing(20);
			
			
			vb2.getChildren().addAll(messages, inputText);
			
			chatScene.setOnKeyPressed( a->{
				if(a.getCode() == KeyCode.ENTER)
				{
					c.send(Nametf.getText() + ": " + inputText.getText());
				}
			});
			
			chatStage.setScene(chatScene);
			chatStage.setTitle("Chat Host");
			chatStage.show();
			
		});
		
		
		joinbt.setOnAction(e->{
			
			primaryStage.close();
			
			Pane chatPane = new Pane();
			Scene chatScene = new Scene(chatPane, 400, 600);
			Stage chatStage = new Stage();
			
			c = new Client(data ->{
				Platform.runLater(()->{

					try
					{
						messages.appendText(data.toString() + "\n");
					}
					catch(Exception a)
					{
						
					}
			});
		});

			
			Platform.runLater(() ->{
				c.start();
			});
			
			
			
			VBox vb2 = new VBox();
			chatPane.getChildren().add(vb2);
			TextField inputText = new TextField();
			vb2.setSpacing(20);
			
			
			vb2.getChildren().addAll(messages, inputText);
			
			chatScene.setOnKeyPressed( a->{
				if(a.getCode() == KeyCode.ENTER)
				{
					c.send(Nametf.getText() + ": " + inputText.getText());
				}
			});
			
			chatStage.setScene(chatScene);
			chatStage.setTitle("Chat Client");
			chatStage.show();
			
			
		});
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chat Client");
		primaryStage.show();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}
