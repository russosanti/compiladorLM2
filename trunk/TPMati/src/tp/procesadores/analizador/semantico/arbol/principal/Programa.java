package tp.procesadores.analizador.semantico.arbol.principal;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.FuncionesPredefinidas;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Programa extends ClaseNodo {

	private static final long serialVersionUID = 1L;

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		//Inicio de todo Programa en Assembler
		GeneradorCodigoUtils gc = new GeneradorCodigoUtils();
		codigo.setCodigo(gc.generarCodigoInicial());
		
		//Globales 
		Codigo codigoAux = new Codigo(); 
		codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
		codigo.setCodigo(codigo.getCodigo() + 
							codigoAux.getCodigo());  
		codigo.appendCodigo("aritmetico\tdw ?\n" + 
							"PUSH\taritmetico\n\n");
							
		
		//Jump procedimieto principal
		codigo.setCodigo(codigo.getCodigo() + 
				gc.generarJumpIncondicional("PRINCIPAL", labelManager.getTabulacion()) + "\n\n");
		
		
		//Codigo Lista de Funciones y Procedimientos excepto el Principal en codigoAux 
		int index = 1; 
		codigoAux = new Codigo();
		codigoAux.setCodigo("");
		while(index<(this.nodos.size()-1))
		{
			codigoAux.appendCodigo( 
					this.nodos.get(index).generarCodigo(new Codigo(), tempManager, labelManager).getCodigo());
			index++; 
		}
		
		//Tratamiento procedimiento Principla 
		codigo.setCodigo(codigo.getCodigo() + "\nPRINCIPAL:\n" );
		codigo.appendCodigo(this.nodos.get(this.nodos.size()-1).generarCodigo(new Codigo(), tempManager, labelManager).getCodigo());
		codigo.appendCodigo("\n\n\n");
		
		
		//append a codigo Lista de Funciones y Procedimientos declarados 
		codigo.appendCodigo(codigoAux.getCodigo());
		
		//Funciones Predefinids 
		FuncionesPredefinidas funcionesPredefinidas = new FuncionesPredefinidas();
		codigo.setCodigo(codigo.getCodigo() + 
				funcionesPredefinidas.generarProcedimientosWrite() +
				funcionesPredefinidas.generarProcedimientoRead()+
				funcionesPredefinidas.aEntero()+
				funcionesPredefinidas.aNatural());
		
		//Fin programa 
		codigo.setCodigo(codigo.getCodigo() + 
				gc.generarFinPrograma("PRINCIPAL"));
		
		return codigo;
	}
}
