package tp.procesadores.analizador.semantico.arbol.bloque;

import java.util.Arrays;
import java.util.List;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Asignacion extends ClaseNodo {

	private static final long serialVersionUID = 1L;

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		String variable;
		Codigo codigoAux = new Codigo(); 
		codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
		codigo.appendCodigo(codigoAux.getCodigo());
		variable = codigoAux.getLabel();
		
		
		codigoAux = new Codigo(); 
		codigoAux = this.nodos.get(1).generarCodigo(codigoAux, tempManager, labelManager);
		codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
		codigo.setCodigo(codigo.getCodigo() + 
				labelManager.getTabulacion() ); 
		List<String> registros = Arrays.asList ("AX", "BX", "CX", "DX","[BX]", "[CX]", "[DX]"); 
		if ( !registros.contains(variable) && !registros.contains(codigoAux.getLabel()))
		{
			codigo.appendCodigo("MOV\tDX, "+codigoAux.getLabel()+"\n" + 
								"MOV\t" + variable + ", DX" + "\n");
		}else{
			List<String> registros2 = Arrays.asList ("[BX]", "[CX]", "[DX]");
			List<String> registros3 = Arrays.asList ("AX", "BX", "CX", "DX");
			if(registros2.contains(variable) && !registros3.contains(codigoAux.getLabel()))
			{
				codigo.appendCodigo(GeneradorCodigoUtils.generarMov("AX", codigoAux.getLabel()));
				codigo.appendCodigo("\n");
				codigo.appendCodigo(GeneradorCodigoUtils.generarMov(variable, "AX"));
			}else
			{
				codigo.appendCodigo(
					GeneradorCodigoUtils.generarMov(variable, codigoAux.getLabel()));
			}
		}
		codigo.appendCodigo("\n");
		return codigo;
	}
}
