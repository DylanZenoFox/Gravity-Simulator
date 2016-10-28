package gravity;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.*;

@SuppressWarnings("restriction")
public class GravEnviroment{
	private int numMasses;
	private ArrayList<PointMass> massList;
	public final static double G = 6;
	private PointMass lastMassAdded;
	
	public GravEnviroment(){
		numMasses = 0;
		massList = new ArrayList<PointMass>();
	}
	
	public int getNumMasses(){
		return numMasses;
	}

	public ArrayList<PointMass> getMassList(){
		return massList;
	}
	
	public void addMass(int x, int y, int dx, int dy, double mass, int size, boolean interaction, boolean moving,boolean destroyer){
		numMasses++;
		Random rand = new Random();
		Color c = Color.rgb(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
		PointMass m = new PointMass(x,y,dx,dy,mass,size,this,c,interaction,moving, destroyer);
		lastMassAdded = m;
		massList.add(m);
	}
	
	public void addMass(int x, int y, int dx, int dy, double mass, int size,Color color, boolean interaction, boolean moving, boolean destroyer){
		numMasses++;
		PointMass m = new PointMass(x,y,dx,dy,mass,size,this,color,interaction,moving,destroyer);
		lastMassAdded = m;
		massList.add(m);
	}
	
	public void moveAllMasses(){
		for(int i = 0; i < massList.size(); i++){
			if(massList.get(i).getMoving()){
				massList.get(i).move();
			}
		}
	}
	
	public PointMass getLastMassAdded(){
		return lastMassAdded;
	}
	
}
