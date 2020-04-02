import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;

public class ChatApp extends Application
{
	private boolean isServer;
	private TextArea messages = new TextArea();
	private NetworkConnection connection;

			
			
	
	@Override
	public void init() throws Exception
	{
		
		

		

	}
			
			
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox pane = new VBox();
		pane.setSpacing(20);
		Scene scene = new Scene(pane, 500, 500);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		
		Button hostbt = new Button("Host");
		Button connectbt = new Button("Join");
		Text name = new Text("name");
		TextField nametf = new TextField("");
		
		pane.getChildren().addAll(hostbt, connectbt, name, nametf);
		
		hostbt.setOnAction(e->
		{
			connection = createServer();
			try
			{
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

				isServer = false;
				primaryStage.setTitle(isServer ? "Server: " : "Client: ");
				connection.startConnection();
				primaryStage.show();
				
				connection.send(nametf.getText() + " has just connected...\n");

			}
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}		
			stage.close();
		});
		
		
		
		messages.setPrefHeight(550);
		TextField input = new TextField();
		input.setOnKeyPressed(e->{
			
			if(e.getCode() == KeyCode.ENTER)
			{
				String message = nametf.getText() + ": ";
				message += input.getText();
				input.clear();
				
				messages.appendText(message +"\n");
		
				try 
				{
					connection.send(message);
				}
				catch (Exception e1) 
				{
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("ERROR SENDING MESSAGE");
				}
			}
		});
		
		VBox root = new VBox(20, messages, input);
		root.setPrefSize(600, 600);
		
		primaryStage.setScene(new Scene(root));
	}
	
	@Override
	public void stop() throws Exception
	{
		connection.closeConnection();
	}
	
	private Server createServer() {
		return new Server(55555, data -> {
			Platform.runLater(() ->{
				
				messages.appendText(data.toString() + "\n");
			});
			
		});
	}
	private Client createClient() {
		return new Client("127.0.0.1", 55555, data -> {
			Platform.runLater(() ->{
				messages.appendText(data.toString() + "\n");
			});
			
		});
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
