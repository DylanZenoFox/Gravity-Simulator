package gravity;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.paint.*;

import javafx.scene.input.*;

import javafx.animation.*;
 

@SuppressWarnings("restriction")

public class GravityGUI extends Application {

	private int sceneHeight = 800, sceneWidth = 770;
	private int buttonHeight = 100, buttonWidth = 267;
	private int massSize = 30, massSizeBuffer = massSize/2, blackHoleSize = 50;
	private double massValue = 40.0, blackHoleMass = 10000.0;
	private final static int CUSTOM = 1;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void start(Stage primaryStage){
		
		Canvas canvas = new Canvas(sceneWidth +20,sceneHeight - buttonHeight + 10);
		GravEnviroment grav = new GravEnviroment();
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		final Painter painter = new Painter(gc,grav,sceneHeight,sceneWidth +20);
		
		AnimationTimer timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				update(painter);
			}
		};
		
		GridPane grid = new GridPane();
		
		
		Button b1 = new Button("Start");
		b1.setPrefHeight(buttonHeight);
		b1.setPrefWidth(buttonWidth);
		b1.setOnAction(e -> {
			timer.start();
		});
		
		Button b2 = new Button("Stop");
		b2.setPrefHeight(buttonHeight);
		b2.setPrefWidth(buttonWidth);
		b2.setOnAction(e -> {
			timer.stop();
		});
		
		Button b3 = new Button("Remove All Moving");
		b3.setPrefHeight(buttonHeight);
		b3.setPrefWidth(buttonWidth);
		b3.setOnAction(e -> {
			painter.removeAllMoving();
			painter.repaint();
		});
		
		
		grid.add(b1, 0, 0);
		grid.add(b2, 1, 0);
		grid.add(b3, 2, 0);
		
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-color: black");
		bp.setTop(canvas);
		bp.setBottom(grid);
		BorderPane.setAlignment(canvas, Pos.CENTER);
		BorderPane.setAlignment(grid, Pos.CENTER);
		
		
		Scene scene = new Scene(bp,sceneWidth,sceneHeight);
		
		addCustomElements(grav,painter);
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				painter.setTempXYCoor((int)e.getX(), (int)e.getY());
				grav.addMass((int)e.getX() - massSizeBuffer, (int) e.getY() - massSizeBuffer,0,0, massValue, massSize, false,false,false);
				painter.repaint();
			}
		});
		scene.setOnMouseReleased(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e){
				int initialX = (-painter.getTempXCoor() + (int) e.getX())/20;
				int initialY = (-painter.getTempYCoor() + (int) e.getY())/20;
				grav.getLastMassAdded().toggleInteraction();
				grav.getLastMassAdded().toggleMovingOn(initialX, initialY);
			}
		});
		
		primaryStage.setTitle("Gravity Simulator");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			primaryStage.close();
		});
		primaryStage.setResizable(false);
	}
	
	public void update(Painter painter){
		painter.moveAllMasses();
		painter.removeAllTagged();
		painter.repaint();
	}
	
	private void addCustomElements(GravEnviroment g, Painter p){
		switch(CUSTOM){
		case 1: addBlackHoleCenter(g,p);
			break;
		case 2: add2BlackHoleCenter(g,p);
			break;
		default: break;
		}
		
		
	}
	
	private void addBlackHoleCenter(GravEnviroment g, Painter p){
		Color col = Color.rgb(100, 30, 30);
		g.addMass(sceneWidth/2 - blackHoleSize/2, (sceneHeight - buttonHeight)/2 - blackHoleSize/2,0,0,blackHoleMass, blackHoleSize,col, true,false,true);
		p.repaint();
	}
	
	private void add2BlackHoleCenter(GravEnviroment g, Painter p){
		Color col = Color.rgb(100, 30, 30);
		g.addMass(sceneWidth/4 - blackHoleSize/2, (sceneHeight - buttonHeight)/2 - blackHoleSize/2,0,0,blackHoleMass, blackHoleSize,col, true,false,true);
		g.addMass(3*sceneWidth/4 - blackHoleSize/2, (sceneHeight - buttonHeight)/2 - blackHoleSize/2,0,0,blackHoleMass, blackHoleSize,col, true,false,true);
		p.repaint();
	}

}
