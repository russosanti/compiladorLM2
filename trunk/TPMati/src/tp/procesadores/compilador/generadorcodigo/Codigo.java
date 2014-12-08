package tp.procesadores.compilador.generadorcodigo;

public class Codigo {
	private String label; 
	private String codigo;
	private String contexto; 
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
	
	public void appendCodigo(String codigo){
		this.codigo = this.codigo + codigo;
	}

	public void appendCodigoAlInicio(String codigo2) {
		this.codigo = codigo2 + this.codigo; 
	}
	
}
