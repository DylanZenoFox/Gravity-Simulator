package gravity;

public class Force {
	private double angle;
	private double mag;
	
	public Force(double mag, double angle){
		this.mag = mag;
		this.angle = angle;
	}
	
	public double getMag(){
		return mag;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public void add(Force f1){
		double xComp1 = f1.getMag()*Math.cos(f1.getAngle());
		double xComp2 = this.getMag()*Math.cos(this.getAngle());
		double yComp1 = f1.getMag()*Math.sin(f1.getAngle());
		double yComp2 = this.getMag()*Math.sin(this.getAngle());
		
		double newMag = Math.sqrt(Math.pow((xComp1 + xComp2),2.0) + Math.pow(yComp1 + yComp2, 2.0));
		double newAngle = Math.atan2((yComp1 + yComp2),(xComp1 + xComp2));
		
		if(newAngle < 0){
			newAngle += 2*Math.PI;
		}
		
		this.mag = newMag;
		this.angle = newAngle;
	}
	
}
