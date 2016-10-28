package gravity;

import javafx.scene.paint.*;
@SuppressWarnings("restriction")
public class PointMass {
	private double x,y,dx, dy, ddx, ddy;
	private int size;
	private int sizeBuffer;
	private double mass;
	private GravEnviroment grav;
	private boolean otherMassContact;
	private Color color;
	private boolean moving;
	private boolean interaction;
	private boolean destroyer;
	private boolean toBeRemoved;
	
	public PointMass(int xCoor,int yCoor,double initialDx, double initialDy, double mass,
			int size, GravEnviroment grav, Color color, boolean interaction, boolean moving, boolean destroyer){
		dy = initialDy;
		dx = initialDx;
		this.interaction = interaction;
		this.moving = moving;
		this.destroyer = destroyer;
		toBeRemoved = false;
		if(!moving){
			dx = 0;
			dy = 0;
		}
		this.mass = mass;
		this.size = size;
		sizeBuffer = size/2;
		this.grav = grav;
		this.color = color;
		x = xCoor + sizeBuffer;
		y = yCoor + sizeBuffer;
	}
	
	public void move(){
		if(!otherMassContact){
			changeAccXDir();
			changeAccYDir();
		}
		accelX();
		accelY();
		moveY();
		moveX();
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getSize(){
		return size;
	}
	
	public double getXDraw(){
		return x - sizeBuffer;
	}
	
	public double getYDraw(){
		return y - sizeBuffer;
	}
	
	public double getXGrav(){
		return x;
	}
	
	public double getYGrav(){
		return y;
	}
	
	public double getMass(){
		return mass;
	}
	
	public void moveX(){
		x += dx;
	}
	
	public void moveY(){
		y += dy;
	}
	
	public void accelX(){
		dx += ddx;
	}
	
	public void accelY(){
		dy += ddy;
	}
	
	public void toggleMovingOn(double dx, double dy){
		moving = true;
		this.dy = dy;
		this.dx = dx;
	}
	
	public void toggleMovingOff(){
		moving = false;
		dy = 0;
		dx = 0;
	}
	
	
	
	public void toggleInteraction(){
		interaction = !interaction;
	}
	
	public boolean getMoving(){
		return moving;
	}
	
	public boolean getInteracting(){
		return interaction;
	}
	
	public boolean getDestroyer(){
		return destroyer;
	}
	
	public boolean getToBeRemoved(){
		return toBeRemoved;
	}
	
	public void tagToBeRemoved(){
		toBeRemoved = true;
	}
	
	private double getAngle(PointMass p){
		double atan = Math.atan2(yDisplacement(p),xDisplacement(p));
		
		if(atan < 0){
			atan += 2*Math.PI;
		}
		return atan;
	}
	
	private Force calculateNetForce(){
		Force temp = new Force(0,0);
		for(PointMass p : grav.getMassList()){
			if(!p.equals(this) && calculateDistance(p) > (sizeBuffer + p.getSize()/2) && p.getInteracting() && this.getInteracting()){
				double mag = (GravEnviroment.G*this.getMass()*p.getMass())/(Math.pow(calculateDistance(p), 2.0));
				double angle = getAngle(p);
				temp.add(new Force(mag,angle));
			} else if(!p.equals(this) && p.getDestroyer() && calculateDistance(p) <= sizeBuffer + p.getSize()/2){
				tagToBeRemoved();
			}
		}
		return temp;
	}
	
	private void changeAccXDir(){
		ddx = ((calculateNetForce().getMag()*Math.cos(calculateNetForce().getAngle()))/this.getMass());
	}
	
	private void changeAccYDir(){
		ddy = ((calculateNetForce().getMag()*Math.sin(calculateNetForce().getAngle()))/this.getMass());
	}
	private int calculateDistance(PointMass p){
		return (int) Math.sqrt((double)(Math.pow((double)(xDisplacement(p)), 2.0) + Math.pow((double)(yDisplacement(p)), 2.0)));
	}
	
	private double xDisplacement(PointMass p){
		return p.getXGrav() - this.getXGrav();
	}
	
	private double yDisplacement(PointMass p){
		return p.getYGrav() - this.getYGrav();
	}
	
}
