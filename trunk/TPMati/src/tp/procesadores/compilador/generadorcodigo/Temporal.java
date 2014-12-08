package tp.procesadores.compilador.generadorcodigo;


public class Temporal {

	private String contexto; 	//nombre procedimiento 
	private String tmp; 		//nombre temporal 


	public Temporal(String context, String name){
//	    Random randomGenerator = new Random();
		this.setContexto(context);
		this.setTmp(name); 
	}
	
	public String getContexto() {
		return contexto;
	}
	
	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
	
	public String getTmp() {
		return tmp;
	}
	
	public void setTmp(String tmp) {
		this.tmp = tmp;
	}
}
