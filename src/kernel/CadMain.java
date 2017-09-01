package kernel;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadMain extends Application{
	
	// all other classes will use CadMain.foo; to access all GUI components
	public static RCanvas drawArea = new RCanvas();
	public static MainMenuBar menu;
	public static Stage stage;
	public static GraphicsContext gc = drawArea.getGraphicsContext2D();	 
	public static TextField textBox = new TextField ();
	public static ListView<String> historyBox = new ListView<String>() ;
	public static Scene scene;
	public static Settings set = Settings.getInstance();
	
	public static void main(String[] args) {
		launch(args);
	}

	
	@Override
	public void start(Stage s) {
	stage = s;
	
	//Create menu and drawing
	menu = MainMenuBar.getInstance();
	CadDrawing.CURRENT_DRAWING = new CadDrawing(set.DRAWING_ACCURACY);
	
	//Create Bottom
	textBox.setEditable(false);
	textBox.setFocusTraversable(false);
	
	historyBox.setPrefHeight(set.HISTORY_BOX_HEIGHT);
	historyBox.setMinHeight(50);
	VBox v = new VBox();
	v.getChildren().addAll(textBox, historyBox);
	
	//Create Canvas Area
	BorderPane b = new BorderPane();
	VBox canvasHolder = new VBox(drawArea);
	drawArea.widthProperty().bind(canvasHolder.widthProperty());
	drawArea.heightProperty().bind(canvasHolder.heightProperty());
	b.setCenter(canvasHolder);
	b.setBottom(v);
	
	//Create event handlers
	HandleEvents e = HandleEvents.getInstance();
	drawArea.setOnKeyPressed(k -> e.handle(k));
	drawArea.setOnKeyPressed(k -> e.handle(k));
	historyBox.setOnKeyPressed(k -> e.handle(k));
	textBox.setOnKeyPressed(k -> e.handle(k));
	drawArea.setOnMouseClicked(m -> e.handle(m));
	drawArea.setOnMouseMoved(m -> e.handle(m));
	s.setOnCloseRequest(w -> e.handle(w, set));
	textBox.focusedProperty().addListener(c -> noFocus());
	historyBox.focusedProperty().addListener(c -> noFocus());
	
	//Create scene and set stage properties
	scene = new Scene(b,set.PROGRAM_WIDTH,set.PROGRAM_HEIGHT);
	scene.widthProperty().addListener(ns -> set.setSaveSize(scene));
	scene.heightProperty().addListener(ns -> set.setSaveSize(scene));
	((BorderPane) scene.getRoot()).setTop(menu);
	s.setTitle("AugieCAD");
	s.setScene(scene);
	s.show();
	drawArea.requestFocus();
	}

	private void noFocus() {
		drawArea.requestFocus();
		
	}	
	
	
}
