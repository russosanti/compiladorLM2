package tp.procesadores.analizador.semantico.arbol.general;

import java.util.List;

import tp.procesadores.analizador.lexico.tokens.visitor.NodeVisitorInterface;
import tp.procesadores.analizador.lexico.tokens.visitor.TablaSimbolosVisitor;
import tp.procesadores.analizador.lexico.tokens.visitor.VisitableNode;
import tp.procesadores.analizador.semantico.arbol.expresiones.ClaseNodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Metodo;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.Parametro;
import tp.procesadores.analizador.semantico.arbol.tabla.simbolos.TablaDeSimbolos;
import tp.procesadores.compilador.generadorcodigo.Codigo;
import tp.procesadores.compilador.generadorcodigo.LabelManager;
import tp.procesadores.compilador.generadorcodigo.TempManager;

public class Identificador extends ClaseNodo implements VisitableNode {

	private static final long serialVersionUID = 1L;
	private String lexema;
	private String contexto; 
	
	public Identificador(String lexema){
		this.setLexema(lexema);
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	@Override
	public String accept(NodeVisitorInterface visitor) {
		return visitor.visit(this);	
	}
	
	@Override
	public Codigo generarCodigo(Codigo codigo, TempManager tempManager, LabelManager labelManager)
	{		
		if (this.nodos.size()>1)
		{			
//			caso2 : tengo un vector con indice natural
//			codigo=""  identificador [ natural ]
//			&
//			caso3: tengo un vector con un indice que es una expresion
//			codigo = codigo expresion    label= lexema[ temporal ]
//			 
			Codigo codigoAux = new Codigo();
			codigoAux =	this.nodos.get(1).generarCodigo(codigoAux, tempManager, labelManager);
			StringBuilder str = new StringBuilder();
			if (codigoAux.getCodigo().equals(""))
			{
				str.append("MOV\t"+"BX,"+"["+codigoAux.getLabel()+"*2]"+"\n");
				str.append("SAL\t"+"BX,"+"1"+"\n");
				codigo.setLabel("[ BX + "+this.getLexema()+"]");
				codigo.appendCodigo(str.toString());
				codigo.appendCodigo(codigoAux.getCodigo());
				
			}
			else
			{
				codigo.appendCodigo(codigoAux.getCodigo());
				str.append("MOV\t"+"BX,"+"["+codigoAux.getLabel()+"]"+"\n");
				str.append("SAL\t"+"BX,"+"1"+"\n");
				codigo.appendCodigo(str.toString());
				codigo.setLabel("["+this.getLexema()+"+"+"BX"+"]");		
			}					
		}
		else
		{
			//caso 1: tengo solo 1 variable
			//codigo = ""  laberl=lexema
			//caso4: tengo un parametro de la funcion como variable
			codigo.setCodigo("");
			TablaDeSimbolos tablaAux = this.nodos.get(0).acceptTSVisitor(new TablaSimbolosVisitor());
			if ( tablaAux != null )
			{
				if (tablaAux.existeId(lexema))
				{
					codigo.setLabel(this.getLexema());
				}else
				{ 
					String[] registros = { "BX", "CX", "DX" };
					Metodo metodoAux = tablaAux.getMetodo(contexto);
					if ( metodoAux != null )
					{
						List<Parametro> parametros = metodoAux.getParametros();
						int index=0; 
						while (index <= (parametros.size()-1))
						{ 
							if(parametros.get(index).getLexema().equals(lexema))
							{
								if(parametros.get(index).esPorValor())	
									codigo.setLabel(registros[index]);
								else 
									codigo.setLabel("["+registros[index]+"]");
							}
							index++;
						}
					}else
					{
						codigo.setCodigo("============= ERROR EN IDENTIFICADOR - VARIABLE / PARAMETRO NO ENCONTRADO ===============");
					}
				}
			}else
			{
				codigo.setCodigo("============= ERROR EN IDENTIFICADOR - VARIABLE NO ENCONTRADA ===============");
			}
		}
		return codigo;
	}

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
}
