package tp.procesadores.compilador.generadorcodigo;

public class Label {

	private String labelName;

	public Label(String name){
		this.setLabelName(name);
	}
	
	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	} 
	
}
