package analizador.semantico.tree.exp;

import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;
import exceptions.AlgorithmicError;
import exceptions.CodeException;
import generadorCodigo.TemporalManager;


public class NodoExpBool extends NodoExp{
	
	private TipoOperacion operacion;
	
	public NodoExpBool(NodoExp izq, TipoOperacion op, NodoExp der){
		super(izq,der);
		this.operacion = op;
		this.tipoNodo = TipoNodo.NODOEXPBOOL;
	}
	
	public enum TipoOperacion{
		
		IGUALDAD("="),
		NOIGUALDAD("<>"),
		MAYOR(">"),
		MENOR("<"),
		MAYORIGUAL(">="),
		MENORIGUAL("<=");
		
		private String desc;
		
		private TipoOperacion(String s){
			this.desc = s;
		}
		
		public String descripcion(){
			return this.desc;
		}
		
		public static TipoOperacion tipoValueOf(String s) throws AlgorithmicError{
			switch (s){
				case "=":
					return IGUALDAD;
				case "<>":
					return NOIGUALDAD;
				case ">":
					return MAYOR;
				case "<":
					return MENOR;
				case ">=":
					return MAYORIGUAL;
				case "<=":
					return MENORIGUAL;
			}
			throw new AlgorithmicError("Error parseando operacion booleana");
		}
	}

	public TipoOperacion getTipoOperacion() {
		return this.operacion;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.getIzq().printTree();
			this.getDer().printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " +this.getTipoNodo()+" OP: "+this.operacion;
	}
	
	@Override
	public void generateCode() throws CodeException {

		TemporalManager tm = TemporalManager.getInstance();
		//Trato la Izquierda
		try{
			@SuppressWarnings("unused")
			Hoja h = (Hoja)this.getIzq();
		}catch(ClassCastException tce){
			try{
				@SuppressWarnings("unused")
				NodoAcceso na = (NodoAcceso)this.getIzq();
			}catch(ClassCastException tce2){
				try{
					@SuppressWarnings("unused")
					Hoja hd = (Hoja)this.getDer();
				}catch(ClassCastException tce3){
					this.getIzq().setNeedsTemporal();
				}
			}
		}
		
		//Trata la Derecha
		String varUsada;
		try{
			Hoja h = (Hoja)this.getDer();
			varUsada = h.getValor();
			this.getIzq().generateCode();
		}catch(ClassCastException tce){
			try{
				NodoAcceso na = (NodoAcceso)this.getDer();
				if(na.getTipoNodo()==TipoNodo.ACCESO){
					if(na.getTipoNodo()==TipoNodo.ACCESOARRAY){
						this.getIzq().setNeedsTemporal();
					}
					varUsada = na.getValor();
					this.getIzq().generateCode();
				}else{
					this.getDer().setNeedsTemporal();
					this.getIzq().setNeedsTemporal();
					this.getIzq().generateCode();
					this.getDer().generateCode();
					varUsada = this.getDer().getTemporal();
				}
			}catch(ClassCastException tce2){
				this.getDer().setNeedsTemporal();
				this.getIzq().setNeedsTemporal();
				this.getIzq().generateCode();
				this.getDer().generateCode();
				varUsada = this.getDer().getTemporal();
			}
		}
		
		ITree cg = ExecutionTree.getInstance();
		
		cg.insertCode("MOV CX, bp");
		cg.insertCode("MOV bp, sp");
		//Calculo este Nodo
		switch(this.operacion){
		case IGUALDAD:
			this.generarCodigoIGUALDAD(varUsada);
			break;
		case NOIGUALDAD:
			this.generarCodigoNOIGUALDAD(varUsada);
			break;
		case MAYOR:
			this.generarCodigoMAYOR(varUsada);
			break;
		case MENOR:
			this.generarCodigoMENOR(varUsada);
			break;
		case MAYORIGUAL:
			this.generarCodigoMAYORIGUAL(varUsada);
			break;
		case MENORIGUAL:
			this.generarCodigoMENORIGUAL(varUsada);
			break;
		}
		
		cg.insertCode("MOV bp, CX");
		
		if(this.getIzq().needsTemporal()){
			tm.useTemp();
		}
		if(this.getDer().needsTemporal()){
			tm.useTemp();
		}
		
		if(this.needsTemporal()){
			String temp = tm.getTemporal();
			this.setTemporalUsed(temp);
			cg.insertCode("MOV CX, bp");
			cg.insertCode("MOV bp, sp");
			cg.insertCode("MOV " + temp +",AX");
			cg.insertCode("MOV bp, CX");
		}
		//Todo valor debe quedar en AX
	}

	private void generarCodigoMENORIGUAL(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.getIzq().needsTemporal()){
			String temp = this.getIzq().getTemporal();
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("CMP AX,"+s);
		}else{
			cg.insertCode("CMP AX,"+s);
		}
		cg.insertCode("JE Equal"+System.identityHashCode(this)+";");
		cg.insertCode("JL Less"+System.identityHashCode(this)+";");
		cg.insertCode("JG Greater"+System.identityHashCode(this)+";");
		
		cg.insertCode("Less"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,1");//Seteo en true porque era menor
		cg.insertCode("JMP Fin"+System.identityHashCode(this)+":");
		
		cg.insertCode("Equal"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,1");//Seteo en true porque era igual
		cg.insertCode("JMP Fin"+System.identityHashCode(this)+":");
		
		cg.insertCode("Greater"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,0");//Seteo en false porque era mayor
		cg.insertCode("Fin"+System.identityHashCode(this)+":");
		
	}

	private void generarCodigoMAYORIGUAL(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.getIzq().needsTemporal()){
			String temp = this.getIzq().getTemporal();
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("CMP AX,"+s);
		}else{
			cg.insertCode("CMP AX,"+s);
		}
		cg.insertCode("JE Equal"+System.identityHashCode(this)+";");
		cg.insertCode("JL Less"+System.identityHashCode(this)+";");
		cg.insertCode("JG Greater"+System.identityHashCode(this)+";");
		
		cg.insertCode("Less"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,0");//Seteo en false porque era menor
		cg.insertCode("JMP Fin"+System.identityHashCode(this)+":");
		
		cg.insertCode("Equal"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,1");//Seteo en true porque era igual
		cg.insertCode("JMP Fin"+System.identityHashCode(this)+";");
		
		cg.insertCode("Greater"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,1");//Seteo en true porque era mayor
		cg.insertCode("Fin"+System.identityHashCode(this)+":");
		
	}

	private void generarCodigoMENOR(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.getIzq().needsTemporal()){
			String temp = this.getIzq().getTemporal();
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("CMP AX,"+s);
		}else{
			cg.insertCode("CMP AX,"+s);
		}		
		cg.insertCode("JL Less"+System.identityHashCode(this)+";");
		cg.insertCode("MOV AX,0");//Seteo en false porque era mayor
		cg.insertCode("JMP Fin"+System.identityHashCode(this)+";");
		cg.insertCode("Less"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,1");//Seteo en true porque era menor	
		cg.insertCode("Fin"+System.identityHashCode(this)+":");
	}

	private void generarCodigoMAYOR(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.getIzq().needsTemporal()){
			String temp = this.getIzq().getTemporal();
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("CMP AX,"+s);
		}else{
			cg.insertCode("CMP AX,"+s);
		}		
		cg.insertCode("JG Greater"+System.identityHashCode(this)+";");
		cg.insertCode("MOV AX,0");//Seteo en false porque era menor
		cg.insertCode("JMP Fin"+System.identityHashCode(this)+";");
		cg.insertCode("Greater"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,1");//Seteo en true porque era mayor
		cg.insertCode("Fin"+System.identityHashCode(this)+":");
	}

	private void generarCodigoNOIGUALDAD(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.getIzq().needsTemporal()){
			String temp = this.getIzq().getTemporal();
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("CMP AX,"+s);
		}else{
			cg.insertCode("CMP AX,"+s);
		}		
		cg.insertCode("JNE Ineq"+System.identityHashCode(this)+";");
		cg.insertCode("MOV AX,0");//Seteo en false porque era igual
		cg.insertCode("JMP Fin"+System.identityHashCode(this)+";");
		cg.insertCode("Ineq"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,1");//Seteo en true porque era distinto
		cg.insertCode("Fin"+System.identityHashCode(this)+":");
	}

	private void generarCodigoIGUALDAD(String s) {
		ITree cg = ExecutionTree.getInstance();
		if(this.getIzq().needsTemporal()){
			String temp = this.getIzq().getTemporal();
			cg.insertCode("MOV AX,"+temp);
			cg.insertCode("CMP AX,"+s);
		}else{
			cg.insertCode("CMP AX,"+s);
		}		
		cg.insertCode("JE Eq"+System.identityHashCode(this)+";");
		cg.insertCode("MOV AX,0");//Seteo en false porque era distinto
		cg.insertCode("JMP Fin"+System.identityHashCode(this)+";");
		cg.insertCode("Eq"+System.identityHashCode(this)+":");
		cg.insertCode("MOV AX,1");//Seteo en true porque era igual
		cg.insertCode("Fin"+System.identityHashCode(this)+":");
	}
}
