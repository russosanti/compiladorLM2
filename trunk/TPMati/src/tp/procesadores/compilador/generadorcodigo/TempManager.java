package tp.procesadores.compilador.generadorcodigo;

import java.util.ArrayList;
import java.util.List;


public class TempManager {

	List<Temporal> listaTemporales = new ArrayList<Temporal>();
	private int contadorTemporales; 
	
	public TempManager(){
		this.setContadorTemporales(0);
	}
	
	public Temporal getNewTemporal(String contexto){
		contadorTemporales++;
		return new Temporal(contexto, "tmp"+contadorTemporales);
	}
	
	public void addTemporal(Temporal tmp){
		listaTemporales.add(tmp);
	}
	
	public String codigoLimpiezaTemporales(String contexto){
		String retorno = "";
		int index = 0;
		int count=0;
		if (!listaTemporales.isEmpty()){
			while( index < (listaTemporales.size()-1))
			{
				if(listaTemporales.get(index).getContexto().equals(contexto))
				{
					count++;
				}
			}
			retorno = "[BP + " + 2*count + "]";
		}
		return retorno;
	}

	public int getContadorTemporales() {
		return contadorTemporales;
	}

	public void setContadorTemporales(int contadorTemporales) {
		this.contadorTemporales = contadorTemporales;
	}
}
