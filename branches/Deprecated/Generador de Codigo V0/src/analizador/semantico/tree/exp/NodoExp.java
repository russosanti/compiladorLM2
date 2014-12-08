package analizador.semantico.tree.exp;

import exceptions.CodeException;
import generadorCodigo.TemporalManager;
import tabladesimbolos.Tipo;
import analizador.semantico.tree.INodo;
import analizador.semantico.tree.bloque.Mostrable;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoExp implements INodo, Mostrable{
	
	private TipoOperacion operacion;
	private NodoExp izq;
	private NodoExp der;
	protected TipoNodo tipoNodo;
	private Tipo tipo;
	private boolean needsTemporal = false;
	private String temporalUsed;
	
	protected NodoExp(){
		this.tipoNodo = TipoNodo.EXP;
	}
	
	protected NodoExp(NodoExp izq, NodoExp der){
		this.setIzq(izq);
		this.der = der;
		this.tipoNodo = TipoNodo.EXP;
	}
	
	protected NodoExp(NodoExp izq, NodoExp der, Tipo tipo){
		this.setIzq(izq);
		this.der = der;
		this.tipo = tipo;
		this.tipoNodo = TipoNodo.EXP;
	}
	
	public NodoExp(NodoExp izq, TipoOperacion op, NodoExp der){
		this.setIzq(izq);
		this.operacion = op;
		this.der = der;
		this.tipoNodo = TipoNodo.EXP;
	}
	
	public NodoExp(NodoExp izq, TipoOperacion op, NodoExp der, Tipo tipo){
		this.setIzq(izq);
		this.operacion = op;
		this.der = der;
		this.tipoNodo = TipoNodo.EXP;
		this.tipo = tipo;
	}
	
	public enum TipoOperacion{
		
		AND("AND"),
		OR("OR"),
		NOT("NOT"),
		SUM("SUM"),
		REST("REST"),
		DIV("DIV"),
		PROD("PROD");
		
		private String desc;
		
		private TipoOperacion(String s){
			this.desc = s;
		}
		
		public String descripcion(){
			return this.desc;
		}
	}

	@Override
	public TipoNodo getTipoNodo() {
		return this.tipoNodo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public NodoExp getIzq() {
		return izq;
	}
	
	public NodoExp getDer() {
		return der;
	}
	
	public TipoOperacion getOperacion(){
		return this.operacion;
	}

	protected void setIzq(NodoExp izq) {
		this.izq = izq;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.izq.printTree();
			this.der.printTree();
			prin.deident();
		}catch(Exception e){
			
		}
	}
	
	public String getTemporal(){
		return this.temporalUsed;
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" OP: "+this.operacion;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void generateCode() throws CodeException{
		TemporalManager tm = TemporalManager.getInstance();
		//Trato la Izquierda
		try{
			@SuppressWarnings("unused")
			Hoja h = (Hoja)this.izq;
		}catch(ClassCastException tce){
			try{
				@SuppressWarnings("unused")
				NodoAcceso na = (NodoAcceso)this.izq;
			}catch(ClassCastException tce2){
				try{
					@SuppressWarnings("unused")
					Hoja hd = (Hoja)this.der;
				}catch(ClassCastException tce3){
					this.izq.setNeedsTemporal();
				}
			}
		}
		
		//Trata la Derecha
		String varUsada;
		try{
			Hoja h = (Hoja)this.der;
			varUsada = h.getValor();
			this.izq.generateCode();
		}catch(ClassCastException tce){
			try{
				NodoAcceso na = (NodoAcceso)this.der;
				if(na.getTipoNodo()==TipoNodo.ACCESO){
					if(na.getTipoNodo()==TipoNodo.ACCESOARRAY){
						this.izq.setNeedsTemporal();
					}
					varUsada = na.getValor();
					this.izq.generateCode();
				}else{
					this.der.setNeedsTemporal();
					this.izq.setNeedsTemporal();
					this.izq.generateCode();
					this.der.generateCode();
					varUsada = this.der.getTemporal();
				}
			}catch(ClassCastException tce2){
				if(this.der.getOperacion() == TipoOperacion.NOT){
					this.der.setNeedsTemporal();
				}else{
					this.der.setNeedsTemporal();
					this.izq.setNeedsTemporal();
				}
				this.izq.generateCode();
				this.der.generateCode();
				varUsada = this.der.getTemporal();
				//genero el codigo de izq y derecha
			}
		}
		
		//Calculo este Nodo
		switch(this.operacion){
		case SUM:
			this.generarCodigoSUM(varUsada);
			break;
		case REST:
			this.generarCodigoREST(varUsada);
			break;
		case PROD:
			this.generarCodigoPROD(varUsada);
			break;
		case DIV:
			this.generarCodigoDIV(varUsada);
			break;
		case AND:
			this.generarCodigoAND(varUsada);
			break;
		case OR:
			this.generarCodigoOR(varUsada);
			break;
		}
		
		if(this.izq.needsTemporal){
			tm.useTemp();
		}
		if(this.der.needsTemporal){
			tm.useTemp();
		}
		
		if(this.needsTemporal){
			ITree cg = ExecutionTree.getInstance();
			String temp = tm.getTemporal();
			this.temporalUsed = temp;
			cg.insertCode("MOV " + temp +",AX");
		}
		//Todo valor debe quedar en AX
	}
	
	private void generarCodigoSUM(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.izq.needsTemporal){
			String temp = this.izq.temporalUsed;
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("ADD AX,"+s);
		}else{
			cg.insertCode("ADD AX,"+s);
		}
	}
	
	private void generarCodigoREST(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.izq.needsTemporal){
			String temp = this.izq.temporalUsed;
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("SUB AX,"+s);
		}else{
			cg.insertCode("SUB AX,"+s);
		}		
	}

	private void generarCodigoPROD(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.izq.needsTemporal){
			String temp = this.izq.temporalUsed;
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("MOV BX,"+s);
			cg.insertCode("MUL BX"); //El resultado queda en AX
		}else{
			cg.insertCode("MOV BX,"+s);
			cg.insertCode("MUL BX"); //El resultado queda en AX
		}			
	}

	private void generarCodigoDIV(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.izq.needsTemporal){
			String temp = this.izq.temporalUsed;
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("MOV BX,"+s);
			cg.insertCode("DIV BX"); //El resultado queda en AX, el resto en BX
		}else{
			cg.insertCode("MOV BX,"+s);
			cg.insertCode("DIV BX"); //El resultado queda en AX, el resto en BX
		}
	}

	private void generarCodigoAND(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.izq.needsTemporal){
			String temp = this.izq.temporalUsed;
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("AND AX,"+s);
		}else{
			cg.insertCode("AND AX,"+s);
		}		
	}

	private void generarCodigoOR(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.izq.needsTemporal){
			String temp = this.izq.temporalUsed;
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("OR AX,"+s);
		}else{
			cg.insertCode("OR AX,"+s);
		}
	}

	/**Genera el String que se va a imprimir luego en pantalla 
	 * @throws CodeException */
	public void showable() throws CodeException{
		ITree cg = ExecutionTree.getInstance();
		this.generateCode();
		cg.insertCode("call PRINT_NUM");
	}

	public boolean needsTemporal() {
		return needsTemporal;
	}

	public void setNeedsTemporal() {
		this.needsTemporal = true;
	}
	
	public void unsetNeedsTemporal() {
		this.needsTemporal = true;
	}
	
	public void setTemporalUsed(String s){
		this.temporalUsed = s;
	}
}
