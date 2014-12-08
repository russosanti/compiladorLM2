package tp.procesadores.compilador.generadorcodigo;

import java.util.ArrayList;
import java.util.List;

public class LabelManager {

	List<Label> labelList = new ArrayList<Label>();
	private int countLabels; 
	private String tabulacion; 
	
	public LabelManager(){
		this.setCountLabels(0);
		this.tabulacion = "";
	}
	
	public Label getNewLabel(String name)
	{
		countLabels++;
		return new Label(name+this.getCountLabels()); 
	}

	public int getCountLabels() {
		return countLabels;
	}

	public void setCountLabels(int countLabels) {
		this.countLabels = countLabels;
	}

	public void setTabulacionOff() {
		this.tabulacion = "";
	}

	public void setTabulacionOn() {
		this.tabulacion = "";
	}
	
	public String getTabulacion(){
		return this.tabulacion;
	}
}
