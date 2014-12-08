package tp.procesadores.analizador.semantico.arbol.funcionesrequeridas;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class FuncionEsImpar extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		//	MOV AX, temp1
		//	MOV BX, 2
		//	DIV BX
		//	
		//	CMP DX,0
		//	JE es_par
		//	JNE es_impar
		
		Codigo codigoAux = new Codigo();
		codigoAux =	this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
		
		codigo.appendCodigo(codigoAux.getCodigo());
		
		StringBuilder str = new StringBuilder();
		str.append("MOV\t"+"AX,"+codigoAux.getLabel()+"\n");
		str.append("MOV\t"+"BX,"+"2"+"\n");
		str.append("IDIV\t"+"BX"+"\n");
		str.append("CMP\t"+"DX,"+"0"+"\n");
		str.append("JE\t");
		
		codigo.appendCodigo(str.toString());
		
		return codigo;
	}
}
