package analizador.semantico.tree.bloque;

import exceptions.CodeAlgorithmicError;
import exceptions.CodeException;
import exceptions.TableException;
import generadorCodigo.ParameterRetainer;
import tabladesimbolos.SingleTabla;
import tabladesimbolos.iTablaDeSimbolos;
import tabladesimbolos.declaraciones.DeclParam;
import tabladesimbolos.declaraciones.Declaraciones;
import analizador.semantico.tree.exp.NodoAcceso;
import analizador.semantico.tree.exp.NodoExp;
import analizador.sintactico.ExecutionTree;
import analizador.sintactico.ITree;

public class NodoAsigAcceso extends NodoAcceso implements Sentencias{
	
	private NodoExp exp;
	
	public NodoAsigAcceso(String id, NodoExp exp){
		super(id);
		this.exp = exp;
		this.setTipoNodo(TipoNodo.ASIGACCESSO);
	}
	
	@Override
	public TipoNodo getTipoNodo() {
		return TipoNodo.ASIGACCESSO;
	}
	
	public void printTree(){
		try{
			ITree prin = ExecutionTree.getInstance();
			prin.insertar(this.toString());
			prin.ident();
			this.exp.printTree();
			prin.deident();
		}catch(Exception e){}
	}
	
	public String toString(){
		return "Nombre: " + this.id + " Tipo Nodo: " +this.getTipoNodo();
	}
	
	@Override
	public void generateCode() throws CodeException {
		this.exp.generateCode(); //La exp queda guardad en AX
		ITree cg = ExecutionTree.getInstance();
		cg.insertCode("MOV "+this.getValor()+", AX");
	}
	
	public String getValor() throws CodeException{
		iTablaDeSimbolos tabla = SingleTabla.getInstance();
		try {
			Declaraciones decl = tabla.get(this.id);
			switch(decl.getTipoDeclaracion()){
			case VAR:
				if(tabla.declaredIDinContext(this.id)){
					return decl.getID()+tabla.getContexto();
				}else{
					return decl.getID();
				}
			case CONST:
				throw new CodeException("Error Generando codigo","No se puede asignar un valor a una constante");
			case PARAM:
				DeclParam dp = (DeclParam)decl;
				ParameterRetainer pr = ParameterRetainer.getInstance();
				if(dp.isByRef()){
					ITree cg = ExecutionTree.getInstance();
					cg.insertCode("MOV BX, [bp+" + pr.getParamPos(dp.getID()) +"]");
					return "word ptr [BX]";
				}else{
					return "[bp+" + pr.getParamPos(dp.getID()) +"]";
				}
			default:
				throw new CodeAlgorithmicError("Tipo de declaracion no valida para Nodo Asig Acceso");
			}
		} catch (TableException e) {
			throw new CodeAlgorithmicError(e.getTitle(),e.getMessage());
		} catch (ClassCastException ice){
			throw new CodeAlgorithmicError("Error en el Nodo Asig Acceso",ice.getMessage());
		}
	}
}
