package tp.procesadores.analizador.semantico.arbol.expresiones;

import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class DivNatural extends ClaseNodo{

	private static final long serialVersionUID = 1L;
	
	public DivNatural(ClaseNodo nodo1, ClaseNodo nodo2){
		this.add(nodo1);
		this.add(nodo2);
	}
	
	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		GeneradorCodigoUtils gc = new GeneradorCodigoUtils();
//		Temporal temp = tempManager.getNewTemporal(codigo.getContexto());
		codigo.setLabel("aritmetico");

//		StringBuilder strbldr = new StringBuilder();
//        strbldr.append(String.format(labelManager.getTabulacion()+"%s\tdw\t?",temp.getTmp())).append("\n");
//        strbldr.append(String.format(labelManager.getTabulacion()+"PUSH\t%s",temp.getTmp())).append("\n");
//        codigo.setCodigo(codigo.getCodigo() + "\n" +  strbldr.toString() );
		
        Codigo codigoAux = new Codigo(); 
        codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager); 
        codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
        String dividendo = codigoAux.getLabel();
        codigoAux = new Codigo();
        codigoAux = this.nodos.get(1).generarCodigo(codigoAux, tempManager, labelManager); 
        codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
        String divisor = codigoAux.getLabel(); 
        
		codigo.setCodigo(codigo.getCodigo() + 
						 gc.generarDivisionNatural("aritmetico",dividendo,divisor,labelManager.getTabulacion())); 
		return codigo;
	}
}
