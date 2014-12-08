package tp.procesadores.analizador.semantico.arbol.principal;

import java.util.List;

import tp.procesadores.analizador.lexico.tokens.visitor.FuncionNodeVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.GeneradorCodigoUtils;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Funcion extends ClaseNodo {

	private static final long serialVersionUID = 1L;
	private String nombreFuncion;
	
	
	public String getNombreFuncion() {
		return nombreFuncion;
	}
	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	} 
	
	@Override
	public Funcion acceptFuncVisitor(FuncionNodeVisitor visitor) {
		return this;
	}	

	@Override
	public Codigo generarCodigo (Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{ 
		int cantParamPorRef = 0;
		int indiceNodos = 0; 
		GeneradorCodigoUtils gc = new GeneradorCodigoUtils();

		//Tratar Metodos anidados 
		TablaDeSimbolos tablaAux = this.nodos.get(indiceNodos).acceptTSVisitor(new TablaSimbolosVisitor());
		Codigo codigoAux = new Codigo(); 
		while (( tablaAux == null ) && ( indiceNodos < (this.nodos.size()-1)) )
		{ 
			codigoAux = this.nodos.get(indiceNodos).generarCodigo(codigoAux, tempManager, labelManager);
			codigo.appendCodigo(codigoAux.getCodigo()); 
			indiceNodos++;
			tablaAux = this.nodos.get(indiceNodos).acceptTSVisitor(new TablaSimbolosVisitor());
		}
		
		if ( tablaAux != null )
		{ 
			codigo.setContexto(getNombreFuncion());
			
			//Encabezado y parametros 
			codigo.appendCodigo(gc.generarInicioProcedimiento(this.getNombreFuncion()));
			Metodo metodoAux = tablaAux.getMetodo(getNombreFuncion());
			if(!metodoAux.parametrosEsNull()){ 
				int indexParametros = 0; 
				List<Parametro> parametrosAux = metodoAux.getParametros();
				boolean continuar = true; 
				//Si existen parametros por referencia
				while((indexParametros<= (parametrosAux.size()-1)) && continuar) 
				{ 
					if(!parametrosAux.get(indexParametros).esPorValor()){ 
						continuar = false; 
						codigo.appendCodigo(gc.generarEncabezadoParametrosPorReferencia());
					}
					indexParametros++; 
				}
				indexParametros=0;
				
				//Solo se admiten hasta 3 parametros por procedimiento
				String[] registros = { "BX", "CX", "DX" };
				while(indexParametros <= (parametrosAux.size()-1) && (indexParametros<=2)) 
				{ 
					Parametro paramAux = parametrosAux.get(indexParametros);
					if ( !paramAux.esPorValor())
					{
						codigo.appendCodigo(gc.generarParametroPorRef(registros[indexParametros], 4+ (2*cantParamPorRef)));
						cantParamPorRef++;
					}
					indexParametros++;
				}
				if(indexParametros>3){
					codigo.setCodigo("");
					System.out.println("=== ERROR: CANTIDAD DE PARAMETROS EN EXCEDIDA EN PROCEDIMIENTO ===");
				}
			}
			//Declaraciones funcion
			codigoAux = new Codigo();
			tablaAux.generarCodigo(codigoAux, tempManager, labelManager);
			codigo.appendCodigo(codigoAux.getCodigo());
			codigo.appendCodigo(tablaAux.generarPUSH());
			
			//TRATAR Codigo Bloque funcion
			codigoAux = new Codigo(); 
			codigoAux = this.nodos.get(indiceNodos+1).generarCodigo(codigoAux, tempManager, labelManager);
			codigo.appendCodigo(codigoAux.getCodigo());		
			codigo.appendCodigo("MOV\tAX,\t"+ codigoAux.getLabel() + "\n");	
			
			//Limpieza memoria: POP de las variables declaradas. 
//			codigo.appendCodigo(tempManager.codigoLimpiezaTemporales(nombreFuncion));
			
			
			codigo.appendCodigo(tablaAux.generarPOP());
			//Fin funcion
			codigo.appendCodigo(gc.generarFinProcedimiento(this.getNombreFuncion(),Integer.toString(2*cantParamPorRef)));
		}	
		return codigo;	
	}

}
