import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AugiecadMain extends Application{
		
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) {
	
	//Load settings/preferences
	Settings set = Settings.loadFromFile();

	//Create Bottom
	TextField textBox = new TextField ();
	textBox.setEditable(false);
	ListView<String> historyBox = new ListView<String>() ;
	historyBox.setPrefHeight(set.HISTORY_BOX_HEIGHT);
	historyBox.setMinHeight(50);
	VBox v = new VBox();
	v.getChildren().addAll(textBox, historyBox);
	
	//Create Canvas Area
	BorderPane b = new BorderPane();
	RCanvas drawArea = new RCanvas();
	VBox canvasHolder = new VBox(drawArea);
	drawArea.widthProperty().bind(canvasHolder.widthProperty());
	drawArea.heightProperty().bind(canvasHolder.heightProperty());
	b.setCenter(canvasHolder);
	b.setBottom(v);
	
	//Create event handlers	
	HandleEvents e = new HandleEvents(drawArea,textBox,historyBox);
	drawArea.setOnKeyPressed(new EventHandler<KeyEvent>() {public void handle(KeyEvent ke) {e.handle(ke);}} );
	drawArea.setOnKeyPressed(k -> e.handle(k));
	historyBox.setOnKeyPressed(k -> e.handle(k));
	textBox.setOnKeyPressed(k -> e.handle(k));
	drawArea.setOnMouseClicked(m -> e.handle(m));
	s.setOnCloseRequest(w -> e.handle(w, set));
	s.widthProperty().addListener(ns -> e.setNewSize(set,s));
	s.heightProperty().addListener(ns -> e.setNewSize(set,s));
	
	//Create scene and set stage properties
	Scene f = new Scene(b,set.PROGRAM_WIDTH,set.PROGRAM_HEIGHT);
	((BorderPane) f.getRoot()).setTop(new MainMenuBar());
	s.setTitle("AugieCAD");
	s.setScene(f);
	s.show();
	
	}	
}
