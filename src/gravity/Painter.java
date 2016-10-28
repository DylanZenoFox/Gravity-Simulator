package gravity;


import javafx.scene.canvas.GraphicsContext;


@SuppressWarnings("restriction")
public class Painter {
	private GraphicsContext gc;
	private GravEnviroment grav;
	int height;
	int width;
	int tempXCoor,tempYCoor;
	
	public Painter(GraphicsContext gc,GravEnviroment grav, int height, int width){
		this.gc = gc;
		this.grav = grav;
		this.height = height;
		this.width = width;
	}
	
	public void setTempXYCoor(int x, int y){
		tempXCoor = x;
		tempYCoor = y;
	}
	
	public int getTempXCoor(){
		return tempXCoor;
	}
	
	public int getTempYCoor(){
		return tempYCoor;
	}
	
	public void moveAllMasses(){
		grav.moveAllMasses();
	}
	
	public void repaint(){
		gc.clearRect(0, 0, width, height);
		for(PointMass mass : grav.getMassList()){
			gc.setFill(mass.getColor());
			gc.fillOval((int) mass.getXDraw(), (int) mass.getYDraw(), mass.getSize(), mass.getSize());
		}
	}
	
	public void removeAllTagged(){
		for(int i = 0; i < grav.getMassList().size(); i++){
			if(grav.getMassList().get(i).getToBeRemoved()){
				grav.getMassList().remove(i);
				i--;
			}
		}
	}
	
	public void removeAllMoving(){
		for(int i = 0; i < grav.getMassList().size(); i++){
			if(grav.getMassList().get(i).getMoving()){
				grav.getMassList().remove(i);
				i--;
			}
		}
	}

}
