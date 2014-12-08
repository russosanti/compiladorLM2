package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class IgualdadNaturales extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	public IgualdadNaturales ( ClaseNodo nodo1, ClaseNodo nodo2 ){
		this.add(nodo1);
		this.add(nodo2);
	}
	
	@Override 
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		GeneradorCodigoUtils gc = new GeneradorCodigoUtils();
        Codigo codigoAux = new Codigo(); 
        codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager); 
        codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
        String entero1 = codigoAux.getLabel();
        codigoAux = new Codigo();
        codigoAux = this.nodos.get(1).generarCodigo(codigoAux, tempManager, labelManager); 
        codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
        String entero2 = codigoAux.getLabel(); 
        if (codigo.getLabel().equals("or"))
        	codigo.setCodigo(codigo.getCodigo() + gc.generarExprBool(entero1, entero2, labelManager.getTabulacion()) +"JE\t");
        else
        	codigo.setCodigo(codigo.getCodigo() + gc.generarExprBool(entero1, entero2, labelManager.getTabulacion()) +"JNE\t");
        return codigo; 
	}

}
