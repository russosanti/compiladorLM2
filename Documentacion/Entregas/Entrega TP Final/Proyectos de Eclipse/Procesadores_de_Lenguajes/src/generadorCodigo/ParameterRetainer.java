package generadorCodigo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import analizador.semantico.tree.func.HojaParam;

public class ParameterRetainer {
	
	private static ParameterRetainer instance;
	private ArrayList<HojaParam> lista;
	private HashMap<String,ArrayList<HojaParam>> allParams = new HashMap<String,ArrayList<HojaParam>>();
	private HashMap<String,ArrayList<String>> declaredVars = new HashMap<String,ArrayList<String>>();

	private String contexto;
	private String funcID;
	
	/** Constructor del Singleton */
	public static ParameterRetainer getInstance(){
		if(instance == null){
			instance = new ParameterRetainer();
		}
		return instance;
	}
	
	/** Crea una nueva instancia del Singleton */
	public static ParameterRetainer getInstance(String contexto){
		getInstance();
		instance.setLista(null);
		instance.setContexto(contexto);
		return instance;
	}
	
	/** Busca los parametros de una determinada funcion
	 * @return La lista de Parametros o null */
	public ArrayList<HojaParam> getParams(){
		return this.lista;
	}
	
	/** Te retorna la posicion del Parametro dentro del Stack
	 * @param id - Parametro a buscar
	 * @return -1 si no lo encuentra, sino el bp+x */
	public int getParamPos(String id){
		int max = 2 + (2*this.lista.size());
		int pos = -1;
		int i = 0;
		Iterator<HojaParam> iterator = this.lista.iterator();
		while(iterator.hasNext() && pos<0){
			if(iterator.next().getId().equals(id)){
				pos = i;
			}
			i++;
		}
		if(pos<0){
			return -1;
		}
		return max - (2*pos);
	}
	
	/** Te retorna la posicion del Parametro dentro del Stack
	 * @param id - Parametro a buscar
	 * @return -1 si no lo encuentra, sino el bp+x */
	public boolean isParamByRef(String func,int pos){
		if(this.allParams.containsKey(func)){
			ArrayList<HojaParam> params = this.allParams.get(func);
			HojaParam h = params.get(pos);
			if(h.isRef()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/** Te retorna la posicion del Parametro dentro del Stack
	 * @param id - Parametro a buscar
	 * @return -1 si no lo encuentra, sino el bp+x */
	public boolean isParamByRef(int pos){
		return this.isParamByRef(this.funcID, pos);
	}

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
	
	public void setLista(ArrayList<HojaParam> lista) {
		this.lista = lista;
	}
	
	public int cantidadParametros(){
		return this.lista.size();
	}

	public String getFuncID() {
		return funcID;
	}

	public void setFuncID(String funcID) {
		this.funcID = funcID;
	}
	
	public void addParams(String funcID,ArrayList<HojaParam> l){
		this.allParams.put(funcID, l);
	}
	
	public void addVars(String contexto, String id){
		if(!this.declaredVars.containsKey(contexto)){
			this.declaredVars.put(contexto, new ArrayList<String>());
		}
		this.declaredVars.get(contexto).add(id);
	}
	
	public ArrayList<String> getDeclaredVars(String contexto){
		return this.declaredVars.get(contexto);
	}
}
