package kernel;
import drawing.CadDrawing;
import drawing.RCanvas;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AugiecadMain extends Application{
		
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) {
	
	//Load settings/new drawing
	Settings set = Settings.getInstance();
	
	//Create Bottom
	TextField textBox = new TextField ();
	textBox.setEditable(false);
	textBox.setFocusTraversable(false);
	
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
	
	//Create menu
	MainMenuBar menu = new MainMenuBar();

	//Create event Drawing and handlers
	CadDrawing.CURRENT_DRAWING = new CadDrawing(set,set.DRAWING_ACCURACY);
	HandleEvents e = new HandleEvents(drawArea, textBox, historyBox, menu, s);
	drawArea.setOnKeyPressed(k -> e.handle(k));
	drawArea.setOnKeyPressed(k -> e.handle(k));
	historyBox.setOnKeyPressed(k -> e.handle(k));
	textBox.setOnKeyPressed(k -> e.handle(k));
	drawArea.setOnMouseClicked(m -> e.handle(m));
	drawArea.setOnMouseMoved(m -> e.handle(m));
	s.setOnCloseRequest(w -> e.handle(w, set));
	s.widthProperty().addListener(ns -> set.setSaveSize(s));
	s.heightProperty().addListener(ns -> set.setSaveSize(s));
	textBox.focusedProperty().addListener(c -> noFocus(drawArea));
	historyBox.focusedProperty().addListener(c -> noFocus(drawArea));
	
	//Create scene and set stage properties
	Scene f = new Scene(b,set.PROGRAM_WIDTH,set.PROGRAM_HEIGHT);
	((BorderPane) f.getRoot()).setTop(menu);
	s.setTitle("AugieCAD");
	s.setScene(f);
	s.show();
	drawArea.requestFocus();
	}

	private void noFocus(RCanvas drawArea) {
		drawArea.requestFocus();
		
	}	
	
	
}
