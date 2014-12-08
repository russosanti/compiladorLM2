package tp.procesadores.analizador.semantico.arbol.bloque;

import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.Label;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Si extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	
	
	//ExpresionBooleana, Bloque, Sino 
	
	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{
		GeneradorCodigoUtils gc = new GeneradorCodigoUtils();
		if(this.nodos.size()>2)
		{
			Label sino = labelManager.getNewLabel("sino");							
			Codigo codigoAux = new Codigo();
			codigoAux.setLabel(sino.getLabelName());
			codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager); 
			codigoAux.setCodigo(codigoAux.getCodigo()+sino.getLabelName()+"\n");
			codigo.setCodigo(codigo.getCodigo()+codigoAux.getCodigo());
			labelManager.setTabulacionOn();
				codigoAux = new Codigo();
				codigoAux = this.nodos.get(1).generarCodigo(codigoAux, tempManager, labelManager);
				codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
				Label finsi = labelManager.getNewLabel("finsi"); 
				codigo.setCodigo(codigo.getCodigo()+gc.generarJumpIncondicional(finsi.getLabelName(),labelManager.getTabulacion()));
			labelManager.setTabulacionOff();
			codigo.setCodigo(codigo.getCodigo()+"\n"+sino.getLabelName()+":\n");
			labelManager.setTabulacionOn();
				codigoAux = new Codigo();
				codigoAux = this.nodos.get(2).generarCodigo(codigoAux, tempManager, labelManager);
				codigo.setCodigo(codigo.getCodigo() + codigoAux.getCodigo());
			labelManager.setTabulacionOff();
			codigo.setCodigo(codigo.getCodigo()+"\n"+finsi.getLabelName()+":\n");
		}else
		{
			Label finsi = labelManager.getNewLabel("finsi");
			Codigo codigoAux = new Codigo(); 
			codigoAux.setLabel(finsi.getLabelName());
			codigoAux = this.nodos.get(0).generarCodigo(codigoAux, tempManager, labelManager); 
			codigoAux.setCodigo(codigoAux.getCodigo()+finsi.getLabelName()+"\n");
			codigo.setCodigo(codigo.getCodigo()+codigoAux.getCodigo());
			labelManager.setTabulacionOn();
				codigoAux = new Codigo();
				codigo.setCodigo(codigo.getCodigo() + this.nodos.get(1).generarCodigo(codigoAux, tempManager, labelManager).getCodigo());
			labelManager.setTabulacionOff();
			codigo.setCodigo(codigo.getCodigo()+"\n"+finsi.getLabelName()+":\n");
		}
		return codigo; 
	}

}
