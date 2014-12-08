package analizador.sintactico.estructuras;

/** Wrapper para el Estado a apilar en la Pila LR
 * @author Santy */
public class Estado implements LRApilable{
	/** Valor del Estado apilado */
	private int estado;
	
	public Estado(int s){
		this.estado = s;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public String toString(){
		return String.valueOf(this.estado);
	}
}
