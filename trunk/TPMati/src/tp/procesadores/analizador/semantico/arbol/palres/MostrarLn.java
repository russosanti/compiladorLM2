package tp.procesadores.analizador.semantico.arbol.palres;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class MostrarLn extends ClaseNodo {

	private static final long serialVersionUID = 1L;

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		int index=0; 
		if (this.nodos.size()>0 )
		{
			Codigo codigoAux = new Codigo(); 
			while ( index<= (this.nodos.size()-1)) 
			{ 
				codigoAux = this.nodos.get(index).generarCodigo(codigoAux, tempManager, labelManager);
				codigo.appendCodigo(codigoAux.getCodigo());
				if ( codigoAux.getLabel() == null )
				{
					codigo.appendCodigo("CALL\twriteSTR\n");
				}else 
				{
					codigo.appendCodigo("PUSH\t0001h\n");
					codigo.appendCodigo("PUSH\t" + codigoAux.getLabel() +"\n");
					codigo.appendCodigo("CALL\twriteNUM\n");
				}
				codigoAux = new Codigo();
				index++;
			}
			codigo.appendCodigo("CALL\twriteCRLF\n");
		}
		return codigo;
	}
}
