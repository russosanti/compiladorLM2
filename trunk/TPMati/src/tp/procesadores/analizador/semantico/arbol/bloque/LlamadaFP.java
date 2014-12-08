package tp.procesadores.analizador.semantico.arbol.bloque;

import java.util.List;

import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class LlamadaFP extends ClaseNodo {

	private static final long serialVersionUID = 1L;

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		//El 1 er nodo es la TS, el 2do es nombre metodo, los siguientes parametros. 
		TablaDeSimbolos tablaAux = this.nodos.get(0).acceptTSVisitor(new TablaSimbolosVisitor());
		NodeVisitor visitor = new NodeVisitor();
		String nombreMetodo = this.nodos.get(1).accept(visitor);
		Metodo metodoAux = tablaAux.getMetodo(nombreMetodo);
		
		String[] registros = { "BX", "CX", "DX" };
		int indexRegistros=0;
		int indexNodos=2; 
		List<Parametro> parametros = metodoAux.getParametros();
		
		if(parametros.size() ==  (nodos.size()-2))
		{
			while(indexNodos <= (this.nodos.size()-1))  
			{
				if(parametros.get(indexNodos-2).esPorValor())
				{
					codigo.appendCodigo(String.format("MOV\t%s,\t%s", registros[indexRegistros], this.nodos.get(indexNodos).accept(visitor)) + "\n");
				}else
				{
					codigo.appendCodigo(String.format("MOV\t%s,\tOFFSET %s", "AX", this.nodos.get(indexNodos).accept(visitor)) + "\n");
					codigo.appendCodigo("PUSH\tAX\n");
				}
				indexNodos++;
				indexRegistros++;
			}
		}	
		codigo.appendCodigo(String.format("CALL\t%s", metodoAux.getNombre()) + "\n");
		if( metodoAux.esFuncion() ) 
			codigo.setLabel("AX");
		return codigo;
	}
}
