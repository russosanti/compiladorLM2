package tp.procesadores.analizador.semantico.arbol.bloque;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.Label;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Mientras extends ClaseNodo {

	private static final long serialVersionUID = 1L;

	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		Label mientras = labelManager.getNewLabel("mientras");
		codigo.setCodigo(codigo.getCodigo()+mientras.getLabelName()+":\n");
		labelManager.setTabulacionOn();
			Label finMientras = labelManager.getNewLabel("finmientras");
			Codigo codigoAux = new Codigo();
			codigoAux.setLabel(finMientras.getLabelName());
			codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager);
			codigoAux.setCodigo(codigoAux.getCodigo()+finMientras.getLabelName());
			codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo()+"\n");
			codigoAux = new Codigo(); 
			codigoAux = this.nodos.get(1).generarCodigo(codigoAux, tempManager, labelManager);
			codigo.appendCodigo(codigoAux.getCodigo());
			GeneradorCodigoUtils gc = new GeneradorCodigoUtils(); 
			codigo.setCodigo(codigo.getCodigo() + 
					gc.generarJumpIncondicional(mientras.getLabelName(), labelManager.getTabulacion())+"\n");
		labelManager.setTabulacionOff();
		codigo.setCodigo(codigo.getCodigo()+finMientras.getLabelName()+":\n");
		return codigo;
	}
	
}

