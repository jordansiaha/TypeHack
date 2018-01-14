import com.sun.glass.events.WindowEvent;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TypeHackGUI extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	Stage currentStage;
	Button singlePlayer;
	Button multiPlayer;
	Button back;
	Button back2;
	Button saveName;
	TextField nameField;
	Scene mainMenuScene;
	Scene singlePlayerScene;
	Scene multiPlayerScene;
	GridPane mainMenu;
	BorderPane singlePlayerMenu;
	GridPane multiPlayerMenu;
	Label title;
	Button b1 = new Button("Random");
	Button b2 = new Button("A Christmas Carol");
	Button b3 = new Button("A Resumed Identity");
	Button b4 = new Button("Holmes' Final Problem");
	Button b5 = new Button("The Idiots");
	DropShadow borderGlow = new DropShadow();

	String name;

	final String FONT_NAME = "Georgia";
	static final int X = 800;
	static final int Y = 600;

	@Override
	public void start(Stage primaryStage) {
		currentStage = primaryStage;
		primaryStage.setTitle("TypeHack");


		singlePlayer = new Button("Single Player");
		multiPlayer = new Button("MultiPlayer");
		back = new Button("Return to Main Menu");
		back2 = new Button("Return to Main Menu");
		title = new Label("TypeHack");
		nameField = new TextField();
		saveName = new Button("Enter");
		singlePlayer.setOnAction(e -> ButtonClicked(e));
		multiPlayer.setOnAction(e -> ButtonClicked(e));
		back.setOnAction(e -> ButtonClicked(e));
		back2.setOnAction(e -> ButtonClicked(e));
		b1.setOnAction(e -> ButtonClicked(e));
		b2.setOnAction(e -> ButtonClicked(e));
		b3.setOnAction(e -> ButtonClicked(e));
		b4.setOnAction(e -> ButtonClicked(e));
		b5.setOnAction(e -> ButtonClicked(e));
		saveName.setOnAction(e -> ButtonClicked(e));

		/*
		 * Title 'TypeHack'
		 */
		title.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 30));
		title.setTextFill(Color.WHEAT);

		/*
		 * Main Menu
		 */
		mainMenu = new GridPane();
		// mainMenu.setPrefSize(950, 650);

		mainMenu.setHgap(10);
		mainMenu.setVgap(8);
		mainMenu.setAlignment(Pos.CENTER);

		mainMenu.setPadding(new Insets(10, 10, 10, 10));

		mainMenuScene = new Scene(mainMenu, 400, 300);
		mainMenuScene.getStylesheets().add("styles.css");
		mainMenu.add(title, 0, 1);
		mainMenu.setStyle("-fx-background-color: #061314;");
		singlePlayer.getStyleClass().add("glass-wheat");
		multiPlayer.getStyleClass().add("glass-wheat");
		mainMenu.add(singlePlayer, 0, 0);
		mainMenu.add(multiPlayer, 1, 0);
		mainMenu.add(nameField, 0, 1);
		mainMenu.add(saveName, 1, 1);
		saveName.getStyleClass().add("glass-wheat");
		nameField.setPromptText("Enter you name");
		nameField.selectAll();
		GridPane.setHalignment(singlePlayer, HPos.LEFT);
		GridPane.setHalignment(multiPlayer, HPos.RIGHT);
		GridPane.setHalignment(title, HPos.CENTER);

		GridPane.setConstraints(title, 5, 2);

		/*
		 * Single Player Menu
		 */

		singlePlayerMenu = new BorderPane();
		// singlePlayerMenu.setPrefSize(950, 650);
		singlePlayerScene = new Scene(singlePlayerMenu, X, Y);
		singlePlayerScene.getStylesheets().add("styles.css");
		singlePlayerMenu.setPadding(new Insets(20));
		singlePlayerMenu.setStyle("-fx-background-color: #061314;");
		Button start = new Button("               Start              ");
		start.getStyleClass().add("glass-wheat");
		back.getStyleClass().add("glass-wheat");
		HBox bottomButtons = new HBox(470);
		bottomButtons.getChildren().addAll(back, start);
		bottomButtons.setPadding(new Insets(10));

		singlePlayerMenu.setBottom(bottomButtons);

		VBox buttons = new VBox(5);
		buttons.getStylesheets().add("styles.css");
		Label vLabel = new Label("Select a Text");

		borderGlow.setColor(Color.RED);
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);
		borderGlow.setHeight(70);

		b1.getStyleClass().add("dark-blue");
		b2.getStyleClass().add("dark-blue");
		b3.getStyleClass().add("dark-blue");
		b4.getStyleClass().add("dark-blue");
		b5.getStyleClass().add("dark-blue");
		vLabel.setFont(Font.font(FONT_NAME, FontWeight.NORMAL, 50));
		vLabel.setTextFill(Color.WHEAT);
		buttons.getChildren().addAll(vLabel, b1, b2, b3, b4, b5);
		buttons.setAlignment(Pos.CENTER);

		singlePlayerMenu.setCenter(buttons);

		/*
		 * MultiPlayer Menu
		 */
		multiPlayerMenu = new GridPane();
		// multiPlayerMenu.setPrefSize(950, 650);
		multiPlayerScene = new Scene(multiPlayerMenu, X, Y);
		multiPlayerScene.getStylesheets().add("styles.css");
		multiPlayerMenu.add(back2, 0, 0, 1, 1);
		multiPlayerMenu.setStyle("-fx-background-color: #061314;");

		primaryStage.setScene(mainMenuScene);
		primaryStage.show();
	}

	public void ButtonClicked(ActionEvent e) {
		if (e.getSource() == back || e.getSource() == back2) {
			currentStage.setScene(mainMenuScene);
		} else if (e.getSource() == singlePlayer) {
			currentStage.setScene(singlePlayerScene);
		} else if (e.getSource() == b1) {
			Game game = new Game("Random", name);
			reset(game);
		} else if (e.getSource() == b2) {
			Game game = new Game("A_Christmas_Carol", name);
			reset(game);
		} else if (e.getSource() == b3) {
			Game game = new Game("A_Resumed_Identity", name);
			reset(game);
		} else if (e.getSource() == b4) {
			Game game = new Game("Holmes_Final_Problem", name);
			reset(game);
		} else if (e.getSource() == b5) {
			Game game = new Game("The_Idiots", name);
			reset(game);
		} else if (e.getSource() == saveName) {
			nameField.setDisable(true);
			name = nameField.getText();
		} else {
			currentStage.setScene(multiPlayerScene);
		}
	}

	private void reset(Game game) {
		currentStage.hide();
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (game.isDone()) {
				currentStage.show();
				currentStage.setScene(mainMenuScene);
				nameField.setText("");
				nameField.setDisable(false);
				break;
			}
		}
	}

}